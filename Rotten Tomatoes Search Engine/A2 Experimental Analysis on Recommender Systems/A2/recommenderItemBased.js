const {Matrix} = require("ml-matrix");
const fs = require("fs");
const path = require("path");

let startingMatricies = [];

const testDataDP = 'dataFiles';

async function loadTestData(callback){
    fs.readdir(testDataDP, (err, testFiles) => {
        if (err) {
            console.error('Error reading Test Data directory:', err);
            return;
        }
    
        testFiles.forEach((file) => {
            let filePath = path.join(testDataDP,file);
            let testData = fs.readFileSync(filePath, 'utf8').split('\n');
            let matrixSize = testData[0];
            
            // console.log(matrixSize);
            let rows = matrixSize.split(" ")[0];
            let cols = matrixSize.split(" ")[1];
            let tempData = Matrix.zeros(Number(rows), Number(cols));
            
            for(let row = 0; row < rows; row++){
                let currLine = testData[row + 3].split(" ");//skip first 3 lines to get the 
                for(let col = 0; col < cols; col++){
                    tempData.set(row, col, Number(currLine[col]));// row - 3 since we skip the first 3 lines in the txt file
                }
            }
            startingMatricies.push(tempData);
        });
        callback(null, startingMatricies);
    });
    
}

async function calcAllMean(dataMatrix){
    let allMeanUsers = [];
    let allCounts = [];
    for (let row = 0; row < dataMatrix.rows; row++){
        let count = 0;
        let sum = 0;
        for(let col = 0; col < dataMatrix.columns; col++){
            if(dataMatrix.get(row,col) != 0) {
                sum += dataMatrix.get(row,col);
                count++;
            }
        }
        allMeanUsers.push(count === 0 ? 0 : sum / count);   
        allCounts.push(count);
    }
    return { "means" : allMeanUsers, "counts" : allCounts };
}

async function calcCosSim(dataMatrix, rootIndex, allMeanUsers){
    let correlationCoefficient = [];
    count = 0;
    for (let col = 0; col < dataMatrix.columns; col++){
        numer = 0;
        denomRoot = 0;
        denomOther = 0;
        if(col != rootIndex) {
            for(let row = 0; row < dataMatrix.rows; row++){

                let rootVal = dataMatrix.get(row, rootIndex);
                let otherVal = dataMatrix.get(row, col);

                if(rootVal != 0 && otherVal != 0){

                    numer += (rootVal - allMeanUsers[row]) * (otherVal - allMeanUsers[row]);
                    denomRoot += Math.pow(rootVal - allMeanUsers[row], 2);
                    denomOther += Math.pow(otherVal - allMeanUsers[row], 2);
                }
            }
            denom = Math.sqrt(denomRoot) * Math.sqrt(denomOther);
            
            correlationCoefficient.push({"cos": numer/denom, "itemIndex": col});
        }
    }
    return correlationCoefficient;
}


async function calcPred(allCOS, startingMatrix , row, neighbourhoodSize, threshhold, threshholdBased, includeNeg) {
    let denom = 0;
    let numer = 0;
    let countN = 0;
    for(let i = 0; i < allCOS.length; i++){//change to only accept positive cos sim
        

        if(threshholdBased){
            if (allCOS[i]["cos"] < threshhold ){
                break;
            }

            if(allCOS[i]["cos"] > threshhold && startingMatrix.get(row, allCOS[i]["itemIndex"]) > 0){//threshold based, and rating is valid
                denom += allCOS[i]["cos"];
                numer += allCOS[i]["cos"] * (startingMatrix.get(row, allCOS[i]["itemIndex"]));
                countN++;
            }
        }else{

            if(countN == neighbourhoodSize) break;//only take the top-k neighbours
            
            if (includeNeg){
                if(startingMatrix.get(row, allCOS[i]["itemIndex"]) > 0){//accept also neg CosSim values for neighbourhood, and if the rating is above 0/valid
                    denom += allCOS[i]["cos"];
                    numer += allCOS[i]["cos"] * (startingMatrix.get(row, allCOS[i]["itemIndex"]));
                    countN++;
                }
            }else{
                if(allCOS[i]["cos"] > 0 && startingMatrix.get(row, allCOS[i]["itemIndex"]) > 0){//only accept positive cos sim values for neighbourhood
                    denom += allCOS[i]["cos"];
                    numer += allCOS[i]["cos"] * (startingMatrix.get(row, allCOS[i]["itemIndex"]));
                    countN++;
                }
            }
            
        }
    }

    return numer / denom;
}

async function calcMAE(startingMatrix, neighbourhoodSize, threshhold, threshholdBased, includeNeg) {
    let numer = 0
    let numPred = 0;//denom

    let underPred = 0;
    let overPred = 0;
    
    let allMeanAndCounts = await calcAllMean(startingMatrix);
    let allCounts = allMeanAndCounts["counts"];//number of values
    let allMeanUsers = allMeanAndCounts["means"];//calcs all user means

    for(let row = 0; row < startingMatrix.rows; row++) {
        let actualRAvg = allMeanUsers[row];//save old Rating Avg for reset

        for(let col = 0; col < startingMatrix.columns; col++){
            let actualVal = startingMatrix.get(row, col);
            
            if(actualVal != 0) {//if valid rating, then leave

                startingMatrix.set(row, col, 0);//Leave One Out

                allMeanUsers[row] = (allMeanUsers[row] * allCounts[row] - actualVal) / (allCounts[row] - 1);//recalculates RatingAvg for just that User after Rating removed in O(1) time

                let allCOS = await calcCosSim(startingMatrix, col, allMeanUsers);//calcs the COS sim for each Item/Column
                allCOS.sort((a, b) => b["cos"] - a["cos"]);//Sorts the cos from Highest to Lowest

                let predictedVal = await calcPred(allCOS, startingMatrix , row, neighbourhoodSize, threshhold, threshholdBased, includeNeg);
                
                if (isNaN(predictedVal) || !isFinite(predictedVal)) {//if Nan or Infinity, best guess of User Rating Avg is the prediction
                    predictedVal = allMeanUsers[row];
                }
                
                //reset to Original User Values
                startingMatrix.set(row, col, actualVal);
                allMeanUsers[row] = actualRAvg;

                //over and under bounds setting 1-5
                if(predictedVal < 0.5){ underPred++; predictedVal = 0.5;}
                else if(predictedVal > 5){overPred++; predictedVal = 5;}
                
                numer += Math.abs(predictedVal - actualVal);
                numPred++;
            }
            
        }
    }

    return numer / numPred;
}

async function run(result, neighbourhoodSize, threshhold, threshholdBased, includeNeg) {
    for (let i = 0; i < result.length; i++) {//matrix iteration

        let startTime = performance.now();

        let currMAE = await calcMAE(result[i].clone(), neighbourhoodSize, threshhold, threshholdBased, includeNeg);

        let endTime = performance.now();

        console.log("MAE: " + currMAE + ", Threshhold = " + threshhold + ", neighbourhoodSize = " + 
            neighbourhoodSize + ", Include Negative = " + includeNeg + ", Time = " + (endTime - startTime));
        
    }
}

loadTestData(async (err,result) => {//result is the array of matricies
    if(err){
        console.error("console log error: ", err);
    }else{
        // await run(result, -1, -1, true, true);
        // await run(result, -1, 0, true, true);
        // await run(result, -1, 0.5, true, true);
        // await run(result, -1, 1, true, true);

        // await run(result, 0, -1, false, false);
        // await run(result, 5, 0, false, false);
        // await run(result, 15, 0.5, false, false);
        // await run(result, 25, 1, false, false);
        // await run(result, 50, 0.5, false, false);
        // await run(result, 100, 1, false, false);

        let allNeighbourhoodSizes = [];
        let allThreshholds = [];

        for (let i = 0; i <= 1; i++) {
            allNeighbourhoodSizes.push(i*10);
        }
        for (let i = 0; i <= 1; i++) {
            allThreshholds.push((i * 0.1) - 1);
        }

        allNeighbourhoodSizes.forEach(async(nSize) => {
            run(result, nSize, 777, false, false);
            run(result, nSize, 777, false, true);
        });
        allThreshholds.forEach(async(tSize) => {
            run(result, -1, tSize, true, true);
        });
    }
});