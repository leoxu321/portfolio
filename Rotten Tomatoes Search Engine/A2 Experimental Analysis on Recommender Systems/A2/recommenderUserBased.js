const {Matrix} = require("ml-matrix");
const fs = require("fs");
const path = require("path");
const { count } = require("console");

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

async function calcPCC(dataMatrix, rootIndex, allMeanUsers){
    let correlationCoefficient = [];
    let count = 0;
    for (let row = 0; row < dataMatrix.rows; row++){
        let numer = 0;
        let denomRoot = 0;
        let denomOther = 0;
        if(row != rootIndex) {
            for(let col = 0; col < dataMatrix.columns; col++){

                let rootVal = dataMatrix.get(rootIndex, col);
                let otherVal = dataMatrix.get(row, col);
                if(rootVal != 0 && otherVal != 0){
                    let ratingAvgA = allMeanUsers[rootIndex];
                    let ratingAvgB = allMeanUsers[row];

                    numer += (rootVal - ratingAvgA) * (otherVal - ratingAvgB);
                    denomRoot += Math.pow(rootVal- ratingAvgA, 2);
                    denomOther += Math.pow((otherVal - ratingAvgB), 2);
                }
            }
            let denom = Math.sqrt(denomRoot) * Math.sqrt(denomOther);
            
            correlationCoefficient.push({"pcc": denom === 0 ? 0 : numer / denom, "userIndex": row});
        }
    }
    return correlationCoefficient;
}

async function calcPred(rootRatingAvg, allMeanUsers, allPCCs, startingMatrix , col, neighbourhoodSize, threshhold, threshholdBased, includeNeg) {
    let denom = 0;
    let numer = 0;
    let countN = 0;

    for(let i = 0; i < allPCCs.length; i++){

        if(threshholdBased){
            if (allPCCs[i]["pcc"] < threshhold ){
                break;
            }

            if(allPCCs[i]["pcc"] > threshhold && startingMatrix.get(allPCCs[i]["userIndex"],col) > 0){//threshold based, and rating is valid
                denom += allPCCs[i]["pcc"];
                numer += allPCCs[i]["pcc"] * (startingMatrix.get(allPCCs[i]["userIndex"], col) - allMeanUsers[allPCCs[i]["userIndex"]]);
                countN++;
            }
        }else{

            if(countN == neighbourhoodSize) break;//only take the top-k neighbours
            
            if (includeNeg){
                if(startingMatrix.get(allPCCs[i]["userIndex"],col) > 0){//accept also neg pcc values for neighbourhood, and if the rating is above 0/valid
                    denom += allPCCs[i]["pcc"];
                    numer += allPCCs[i]["pcc"] * (startingMatrix.get(allPCCs[i]["userIndex"], col) - allMeanUsers[allPCCs[i]["userIndex"]]);
                    countN++;
                }
            }else{
                if(allPCCs[i]["pcc"] > 0 && startingMatrix.get(allPCCs[i]["userIndex"],col) > 0){//only accept positive pcc values for neighbourhood, and if the rating is above 0/valid
                    denom += allPCCs[i]["pcc"];
                    numer += allPCCs[i]["pcc"] * (startingMatrix.get(allPCCs[i]["userIndex"], col) - allMeanUsers[allPCCs[i]["userIndex"]]);
                    countN++;
                }
            }
            
        }
    }

    return rootRatingAvg + numer / denom;
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
        //let userAvg = allMeanUsers[row];

        for(let col = 0; col < startingMatrix.columns; col++){
            let actualVal = startingMatrix.get(row, col);
            
            if(actualVal != 0) {//if valid rating, then leave

                startingMatrix.set(row, col, 0);//Leave One Out

                allMeanUsers[row] = (allMeanUsers[row] * allCounts[row] - actualVal) / (allCounts[row] - 1);//recalculates RatingAvg for just that User after Rating removed in O(1) time
                //allMeanUsers[row] = await calcRatingAvg(startingMatrix.getRow(row));//recalculates RatingAvg for just that User after Rating removed in O(n) time
                
                let allPCCs = await calcPCC(startingMatrix, row, allMeanUsers);//calcs the PCC for each User/Row
                allPCCs.sort((a, b) => b["pcc"] - a["pcc"]);//Sorts the PCC from Highest to Lowest

                let predictedVal = await calcPred(allMeanUsers[row], allMeanUsers, allPCCs, startingMatrix , col, neighbourhoodSize, threshhold, threshholdBased, includeNeg);
                if (isNaN(predictedVal) || !isFinite(predictedVal)) {//if Nan or Infinity, best guess of User Rating Avg is the prediction
                    predictedVal = allMeanUsers[row];
                }
                
                //reset to Original User Values
                startingMatrix.set(row, col, actualVal);
                allMeanUsers[row] = actualRAvg;
                
                //over and under bounds setting 0.5-5
                if(predictedVal < 0.5){ underPred++; predictedVal = 0.5;}
                else if(predictedVal > 5){overPred++; predictedVal = 5;}

                // predictedVal = Math.min(Math.max(predictedVal, 1), 5);//prediction bounding 1-5, Minh's FirstBorn Baby
                
                numer += Math.abs(predictedVal - actualVal);
                numPred++;
            }
            
        }
    }

    console.log("Total under predictions (<1): ", underPred);
    console.log("Total over predictions (>5): ", overPred);
    console.log("Total predictions: ", numPred);
    return numer / numPred;
}

async function run(result, neighbourhoodSize, threshhold, threshholdBased, includeNeg) {
    for(let i = 0; i < result.length; i++) {//matrix iteration

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
