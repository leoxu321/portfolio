app.post('/initpeoples', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var data = req.body
  var unparsedpeople=[];
  var parsedpeople=[];
  var objectsarray=[];
  try {
      const listofpeople = await MovieSchema.find().select("Title Writer Director Actors");
      for (let x=0;x<130;x++){
        temp = new personObj(listofpeople[x].Title,listofpeople[x].Writer.split(", "));
        objectsarray.push(temp);
        temp = new personObj(listofpeople[x].Title,listofpeople[x].Director.split(", "));
        objectsarray.push(temp);
        temp = new personObj(listofpeople[x].Title,listofpeople[x].Actors.split(", "));
        objectsarray.push(temp);
      }
    for (let j =0; j<objectsarray.length; j++){
      for (let k=0; k<objectsarray[j].array.length; k++){
          var index =objectsarray[j].array[k].indexOf("(");
          if (index != "-1"){
            objectsarray[j].array[k]=objectsarray[j].array[k].substring(0,index-1);
          }
       }
    }
    console.log("Finished Parsing")
    for (let b=0; b<objectsarray.length;b++){
      for(let c=0; c<objectsarray[b].array.length; c++){
        await new Promise(r => setTimeout(r, 40));
        const check = await PersonSchema.exists({name:objectsarray[b].array[c]});
        if (check === false){
          var person = new PersonSchema({"name":objectsarray[b].array[c]});
          person.save();
          await new Promise(r => setTimeout(r, 40));
          await PersonSchema.updateOne({"name":objectsarray[b].array[c]},{$push:{films:objectsarray[b].title}})
        }else{
          const checkfilm = await PersonSchema.exists({name:objectsarray[b].array[c],films:{$in:[objectsarray[b].title]}});
          await new Promise(r => setTimeout(r, 40));
          if (checkfilm === false){
              await PersonSchema.updateOne({"name":objectsarray[b].array[c]},{$push:{films:objectsarray[b].title}})
          }
        }
      }
    }
    console.log("Calculating Frequency")
    const run = await MovieSchema.find().select("Title");
    for(let k=0; k<130;k++){
      const freq = await PersonSchema.find({films:run[k].Title});
      for(let i=0; i<freq.length;i++){
        name = await PersonSchema.find({name:freq[i].name}).select("name");
        for(let j=0; j<freq.length; j++){
          if(freq[j].name!=name[0].name){
            await PersonSchema.updateOne({name:name[0].name},{$push:{frequency:freq[j].name}});
          }
        }
      }
    }
    console.log("Database Created");
    res.send("/ViewingPeople.html")
  } catch (err) { return next(err); }
});
