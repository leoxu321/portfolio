// this line allows us to use the expressjs module
var express = require("express");
var path = require("path");
var bodyParser = require('body-parser');
const bcrypt = require('bcrypt');


var app = express();
var router = express.Router();
// Add this line so we can serve files from our local
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.text());
const fs = require('fs');
const mongoose = require("mongoose");
mongoose.connect('mongodb://localhost:27017/MovieDatabase', {useNewUrlParser: true});
let db = mongoose.connection;
mongoose.connection.on("error", err => {
  console.log("err", err)
})
mongoose.connection.on("connected", (err, res) => {
  console.log("mongoose is connected")
})

//Schema and models for using the database
const UserSchema = mongoose.model(
  'userlists',
  new mongoose.Schema({
    firstname: String,
    lastname: String,
    name:String,
    username: String,
    password: String,
    signedin: Boolean,
    contributing: Boolean,
    profilepic: String,
    Followers: [Object],
    Place: String,
    follPlace: String,
    RecFilms:Array,
    Reviews:Array,
    PersonFollowing:Array,
    Contributions:Array
   })
);

const NotifPersonSchema = mongoose.model(
  'notifperson',
  new mongoose.Schema({
    name:String,
    films:Array,
   })
);

const PersonSchema = mongoose.model(
  'personlists',
  new mongoose.Schema({
    name:String,
    films:Array,
    followers:Array,
    frequency:Array
   })
);

const FreqSchema = mongoose.model(
  'freqlists',
  new mongoose.Schema({
    name:String,
    frequency:Array
   })
);

const RatingSchema = mongoose.model(
  'ratings',
  new mongoose.Schema({
    Title:String,
    Ratings:Array,
    Totalrating:String,
    Review:Array,
    Poster:String
   })
);

const MovieSchema = mongoose.model(
    'movies',
    new mongoose.Schema({
        Title: String,
        Year: String,
        Rated: String,
        Released: String,
        Runtime: String,
        Genre: String,
        Director: String,
        Writer: String,
        Actors: String,
        Plot: String,
        Language: String,
        Country: String,
        Awards: String,
        Poster: String,
        Ratings: Array,
        Metascore: String,
        imdbRating: String,
        imdbVotes: String,
        imdbID: String,
        Type: String,
        DVD: String,
        BoxOffice: String,
        Production: String,
        Website: String,
        Response: String
    })
);

//Objects for client side
function safeUserInfo(firstname,lastname,username,profilepic){
  this.firstname = firstname;
  this.lastname = lastname;
  this.username = username;
  this.profilepic = profilepic;
}

function followerInfo(username,profilepic,date){
  this.username = username;
  this.profilepic = profilepic;
  this.date = date;
}

function personObj(title,array){
  this.title = title;
  this.array = array;
}

function reviewObj(username,review){
  this.username = username;
  this.review = review;
}

function userRevObj(username,review,title,date){
  this.username = username;
  this.review = review;
  this.title = title;
  this.date = date;
  this.type = "Review";
}

function freqObj(name,freq){
  this.name=name;
  this.freq=freq;
}

function personDate(name,date,person){
  this.name=name;
  this.date=date;
  this.type="Person";
  this.person=person;
}

//HERE IS ALL OF THE PAGE SERVING
// Add the abillity to serve our static files from the public directory
app.use(express.static("public"));

// Here we serve up our index page
app.get("/", function(req, res) {
  res.sendFile(path.join(__dirname + "/FirstWebpage.html"));
  console.log("FirstWebpage");
});

app.get("/SearchUsers", function(req, res) {
    res.sendFile(path.join(__dirname + "/SearchUsers.html"));
    console.log("SearchUsers");
  });

app.get("/AllUsers.html", function(req, res) {
    res.sendFile(path.join(__dirname + "/AllUsers.html"));
    console.log("AllUsers");
  });

app.get("/FirstWebpage.html", function(req, res) {
  res.sendFile(path.join(__dirname + "/FirstWebpage.html"));
  console.log("FirstWebpage");
});

app.get("/LogInPage.html", function(req,res){
  res.sendFile(path.join(__dirname + "/LogInPage.html"));
  console.log("LogInPage.html");
});

app.get("/AllMovies.html", function(req,res){
  res.sendFile(path.join(__dirname + "/AllMovies.html"));
  console.log("AllMovies.html");
});

app.get("/AboutUs.html", function(req,res){
  res.sendFile(path.join(__dirname + "/AboutUs.html"));
  console.log("AboutUs.html");
});

app.get("/Signup.html", function(req,res){
  res.sendFile(path.join(__dirname + "/Signup.html"));
  console.log("Signup.html");
});

app.get("/AccountSettings.html", function(req,res){
  res.sendFile(path.join(__dirname + "/AccountSettings.html"));
  console.log("AccountSettings.html");
});

app.get("/searchFollowing", function(req,res){
  res.sendFile(path.join(__dirname + "/searchFollowing.html"));
  console.log("/searchFollowing");
});

app.get("/peopleFollowed", function(req, res) {
  res.sendFile(path.join(__dirname + "/peopleFollowed.html"));
  console.log("peopleFollowed");
});

app.get("/ViewingPeople.html", function(req,res){
  res.sendFile(path.join(__dirname + "/ViewingPeople.html"));
  console.log("ViewingPeople.html");
});

app.get("/MovieInfo.html", function(req,res){
  res.sendFile(path.join(__dirname + "/MovieInfo.html"));
  console.log("MovieInfo.html");
});

app.get("/listUsersReview", function(req,res){
  res.sendFile(path.join(__dirname + "/listUsersReview.html"));
  console.log("listUsersReview.html");
});

app.get("/notifications", function(req,res){
  res.sendFile(path.join(__dirname + "/notifications.html"));
  console.log("/notifications.html");
});

//REQUIRING SCRIPTS STARTS HERE-------------------------------------------------------
app.get('/movie.js', function(req,res){
    res.sendFile(__dirname + '/movie.js');
});

app.get('/userbase.js', function(req, res){
    res.sendFile(__dirname + '/userbase.js');
});

app.get('/personFollowed.js', function(req, res){
    res.sendFile(__dirname + '/personFollowed.js');
});
app.get('/movieinfo.js', function(req, res){
    res.sendFile(__dirname + '/movieinfo.js');
});


//MOVIE APPS START HERE---------------------------------------------------------------
app.get("/CastView.html", function(req,res){
    res.sendFile(path.join(__dirname + "/CastView.html"));
    console.log("CastView.html");
  });

app.get("/MovieCreate", function(req,res){
        res.sendFile(path.join(__dirname + "/MovieCreate.html"));
        console.log("/MovieCreate.html");
});

app.get("/SimilarMovies.html", function(req,res){
  res.sendFile(path.join(__dirname + "/SimilarMovies.html"));
  console.log("SimilarMovies.html");
});

app.get("/Review.html", function(req,res){
  res.sendFile(path.join(__dirname + "/Review.html"));
  console.log("Review.html");
});

app.get("/RecMovies.html", function(req,res){
  res.sendFile(path.join(__dirname + "/RecMovies.html"));
  console.log("RecMovies.html");
});

function Sim(Genre, Director){
  this.Genre = Genre;
  this.Director = Director;
}
app.post('/SMovies',async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  try{
      const movie = await MovieSchema.find({Title:data.Title});
      //console.log(movie.Genre);
        let tmp = movie[0].Genre;
        let tmp2 = tmp.split(",");
        let tmp3 = movie[0].Director;
        let tmp4 = tmp3.split(",");
        let arr1 = [];
        let arr2 = [];
        for(let i = 0; i < tmp2.length; i++){
          tmp2[i] = tmp2[i].trim();
          const gen = await MovieSchema.find({Genre:{$regex : ".*"+tmp2[i]+".*"}}).select("Title");

          for(let k = 0; k < gen.length; k++){
            //console.log(gen[i])
            if(gen[k].Title !== data.Title){
              arr1.push(gen[k])
            }
          }
        }
        //console.log(tmp2)
        for(let j = 0; j < tmp4.length; j++){
          tmp4[j] = tmp4[j].trim();
          const dir = await MovieSchema.find({Director:{$regex : ".*"+tmp4[j]+".*"}}).select("Title");
          for(let h = 0; h < dir.length; h++){
            if(dir[h].Title !== data.Title){
              arr2.push(dir[h])
            }
          }
        }
        //console.log(arr1)
        let data2 = new Sim(arr1,arr2);
        let data3 = JSON.stringify(data2);

        res.send(data3);
  }
  catch(err){
      return next(err);
  }
})

app.post('/Poster',async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var data = req.body
  console.log(data);
  try{
      const poster = await MovieSchema.find({Title:data}).select("Poster");
      console.log(poster);
      res.send(poster[0].Poster)
  }
  catch(err){
      return next(err);
  }
})

app.post('/RMovies',async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','application/json');
  req.connection.setTimeout(50)
  var data = req.body
  try{
      const ratings = await RatingSchema.find({Title:data.Title});
      let info=JSON.stringify(ratings);
      res.send(info)
  }
  catch(err){
      return next(err);
  }
})

app.post('/sendRatingReq',async (req, res, next) => {
  res.set('Content-Type','application/json');
  var data = req.body
  try{
      const check = await RatingSchema.exists({Title:data.Title});
      if(check === true){
        const rating = await RatingSchema.find({Title:data.Title}).select("Totalrating");
        res.send(rating[0].Totalrating);
      }
  }
  catch(err){
      return next(err);
  }
})

app.get("/movies",async (req,res,next)=>{
    try{
        let movie = req.query.title;
        let genre = req.query.genre;
        let year = req.query.year;
        let minrating = req.query.minrating;
        const newMovie = await MovieSchema.exists({Title:movie})
        const newGen = await MovieSchema.exists({Genre:{$regex : ".*"+genre+".*"}})
        const newYear = await MovieSchema.exists({Year:year})
        if(newMovie === true){
            const movie2= await MovieSchema.find({Title:movie})
            res.send(movie2)
        }
        if(newGen === true){
            const gen2= await MovieSchema.find({Genre:{$regex : ".*"+genre+".*"}});
            res.send(gen2);
        }
        if(newYear === true){
            const year2= await MovieSchema.find({Year:year});
            console.log(year2)
            res.send(year2);
        }
        if(minrating !== undefined){
          if(minrating>=0 && minrating<=10){
            let y = [];
            const rating = await RatingSchema.find();
            for(let i = 0; i < rating.length; i++){
              let z = parseInt(rating[i].Totalrating,10)
              if(z >= minrating){
                y.push(rating[i].Title);
              }
            }
            res.send(y);
          }
          if(minrating<0 && minrating>10){
              res.send("");
          }
        }


        else{
            res.sendFile(path.join(__dirname + "/AllMovies.html"));
        }
    }
    catch(err){
        {
            return next(err);
        }
    }
})

app.get("/movies/:movie",async (req,res,next)=>{
    try{
        let movie = req.params.movie;
        const newMovie = await MovieSchema.exists({Title:movie})
        if(newMovie === true){
            const movie2= await MovieSchema.find({Title:movie})
            res.send(movie2)
            console.log("XD")
        }
        else{
            res.sendFile(path.join(__dirname + "/AllMovies.html"));
        }
    }
    catch(err){
        {
            return next(err);
        }
    }
})

//IF A USER DECIDES TO CONTRIBUTE A NEW FILM
app.post("/movies",async (req,res,next)=>{
    console.log("Posted");
    res.set('Content-Type','application/json');
    var data = req.body
    var people=[];
    var date= new Date();
    var make = true;
    try{
      const movie = await MovieSchema.exists({Title:data.Title});
      if(movie === false){
        p1=data.Actors.split(",");
        p2=data.Director.split(",");
        p3=data.Writer.split(",");
        for(let i=0; i<p1.length;i++){
          people.push(p1[i].trim());
        }
        for(let j=0; j<p2.length;j++){
          people.push(p2[j].trim());
        }
        for(let k=0; k<p3.length;k++){
          people.push(p3[k].trim());
        }
        for(let a=0; a<people.length; a++){
          var index =people[a].indexOf("(");
          if (index != "-1"){
            people[a]=people[a].substring(0,index-1);
          }
        }
        if(make === true){
          await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
          await new Promise(r => setTimeout(r, 20));
          for(var x=0; x<people.length;x++){
            personobj= new personDate(data.Title,date,people[x]);
            const check = await PersonSchema.exists({name:people[x]});
            if(check === false){
              var person = new PersonSchema({"name":people[x]});
              person.save();
              var notifperson = new NotifPersonSchema({"name":people[x]});
              notifperson.save();
              await new Promise(r => setTimeout(r, 20));
              await PersonSchema.updateOne({"name":people[x]},{$push:{films:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await NotifPersonSchema.updateOne({"name":people[x]},{$push:{films:personobj}});
            }else{
              const checkfilm = await PersonSchema.exists({name:people[x],films:{$in:[data.Title]}});
              await new Promise(r => setTimeout(r, 20));
              if (checkfilm === false){
                  await PersonSchema.updateOne({"name":people[x]},{$push:{films:data.Title}});
                  await NotifPersonSchema.updateOne({"name":people[x]},{$push:{films:personobj}});
              }
            }
          }
          for(let i=0; i<people.length;i++){
            name = await PersonSchema.find({name:people[i]}).select("name");
            for(let j=0; j<people.length; j++){
              if(people[j]!=name[0].name){
                await PersonSchema.updateOne({name:name[0].name},{$push:{frequency:people[j]}});
              }
            }
          }
          if(data.Actors === ""){
            res.send("Need to enter Actors");
          }
          else if(data.Director === ""){
            res.send("Need to enter Director");
          }
          else if(data.Writer === ""){
            res.send("Need to enter Writers");
          }
          else{
            await MovieSchema.deleteOne({Title:data.Title});
              var data2 = new MovieSchema({Title:data.Title,Year:data.Year,Rated:data.Rated,
                  Runtime:data.Runtime,Genre:data.Genre,Director:data.Director,
                  Writer:data.Writer,Actors:data.Actors,Plot:data.Plot,Language:data.Language,
                  Country:data.Country,Awards:data.Awards,Poster:data.Poster,Ratings:data.Ratings,
                  Metascore:data.Metascore,imdbRating:data.imdbRating,imdbVotes:data.imdbVotes,
                  imdbID:data.imdbID,Type:data.Type,DVD:data.DVD,BoxOffice:data.BoxOffice,
                  Production:data.Production,Website:data.Website,Response:data.Response});
              data2.save();
              res.send("Movie saved in database")
          }
          }
          else{
              console.log("This Movie Already Exists");
          }
        }
    }
    catch(err){
        {
            return next(err);
        }
    }
})

app.post("/editFilm",async (req,res,next)=>{
    res.set('Content-Type','application/json');
    var data = req.body
    var string="Successfully Updated"
    var date = new Date();
    personobj= new personDate(data.Title,date,data.Change);
    try{
        const movie = await MovieSchema.exists({Title:data.Title});
        var check = true;
        if(movie === true){
            let field = data.Type.toUpperCase();
            console.log(field);
            let newPeople=data.Change;
            if(field === "TITLE"){
                await MovieSchema.updateOne({Title:data.Title},{$set: {Title:data.Change}});
                await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
                await new Promise(r => setTimeout(r, 20));
            }else if(field ==="YEAR"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Year:data.Change}});
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
            }
            else if(field ==="RATED"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Rated:data.Change}});
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
            }
            else if(field ==="RUNTIME"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Runtime:data.Change}});
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
            }
            else if(field ==="GENRE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Genre:data.Change}});
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
            }
            else if(field ==="DIRECTOR"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              const olddata = await MovieSchema.find({Title:data.Title}).select("Director");
              const totaldata = await MovieSchema.find({Title:data.Title}).select("Actors Director Writer");
              var freq2 = totaldata[0].Actors.split(",")
              var freq1 = totaldata[0].Writer.split(",")
              var info = olddata[0].Director.split(",");
              for (let z=0; z<info.length; z++){
                  var index =info[z].indexOf("(");
                  if (index != "-1"){
                    info[z]=info[z].substring(0,index-1);
                  }
               }
               for (let c=0; c<freq1.length; c++){
                   var index =freq1[c].indexOf("(");
                   if (index != "-1"){
                     freq1[c]=freq2[c].substring(0,index-1);
                   }
                }
              for(let i=0; i<info.length;i++){
                if (newPeople===info[i].trim()){
                  check=false;
                }
              }
              if (check === true){
                console.log("true so adding");
                newPeople+=", "+olddata[0].Director;
                const checkfilm = await PersonSchema.exists({name:data.Change});
                await new Promise(r => setTimeout(r, 20));
                if (checkfilm === true){
                    t = await NotifPersonSchema.exists({"name":data.Change});
                    if (t===false){
                      console.log("false so made on");
                      var notifperson = new NotifPersonSchema({"name":data.Change});
                      notifperson.save();
                      await new Promise(r => setTimeout(r, 20));
                      await MovieSchema.updateOne({Title:data.Title},{$set: {Director:newPeople}});
                      await PersonSchema.updateOne({"name":data.Change},{$push:{films:data.Title}});
                      await NotifPersonSchema.updateOne({"name":data.Change},{$push:{films:personobj}});
                    }else{
                    console.log("true so updating");
                    await MovieSchema.updateOne({Title:data.Title},{$set: {Director:newPeople}});
                    await PersonSchema.updateOne({"name":data.Change},{$push:{films:data.Title}});
                    await NotifPersonSchema.updateOne({"name":data.Change},{$push:{films:personobj}});
                  }
                  for(let i=0; i<info.length;i++){
                    temp = info[i].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp}});
                    await new Promise(r => setTimeout(r, 20));
                  }
                  for(let k=0;k<freq1.length;k++){
                    temp1 = freq1[k].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp1}});
                  }
                  for(let j=0;j<freq2.length;j++){
                    temp2 = freq2[j].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp2}});
                  }
                }else{
                  string = "Person does not exist";
                }
              }else{
                string = "Is already an writer for this Film";
              }
            }
            else if(field ==="WRITER"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              const olddata = await MovieSchema.find({Title:data.Title}).select("Writer");
              const totaldata = await MovieSchema.find({Title:data.Title}).select("Actors Director Writer");
              var freq2 = totaldata[0].Actors.split(",")
              var freq1 = totaldata[0].Director.split(",")
              var info = olddata[0].Writer.split(",");
              for (let z=0; z<info.length; z++){
                  var index =info[z].indexOf("(");
                  if (index != "-1"){
                    info[z]=info[z].substring(0,index-1);
                  }
               }
               for (let c=0; c<freq1.length; c++){
                   var index =freq1[c].indexOf("(");
                   if (index != "-1"){
                     freq1[c]=freq2[c].substring(0,index-1);
                   }
                }
              for(let i=0; i<info.length;i++){
                if (newPeople===info[i].trim()){
                  check=false;
                }
              }
              if (check === true){
                console.log("adding");
                newPeople+=", "+olddata[0].Writer;
                const checkfilm = await PersonSchema.exists({name:data.Change});
                await new Promise(r => setTimeout(r, 20));
                if (checkfilm === true){
                    t = await NotifPersonSchema.exists({"name":data.Change});
                    if (t===false){
                      console.log("false so made one");
                      var notifperson = new NotifPersonSchema({"name":data.Change});
                      notifperson.save();
                      await new Promise(r => setTimeout(r, 20));
                      await NotifPersonSchema.updateOne({"name":data.Change},{$push:{films:personobj}});
                      await MovieSchema.updateOne({Title:data.Title},{$set: {Writer:newPeople}});
                      await PersonSchema.updateOne({"name":data.Change},{$push:{films:data.Title}});
                    }else{
                    console.log("True so updated");
                    await MovieSchema.updateOne({Title:data.Title},{$set: {Writer:newPeople}});
                    await PersonSchema.updateOne({"name":data.Change},{$push:{films:data.Title}});
                    await NotifPersonSchema.updateOne({"name":data.Change},{$push:{films:personobj}});
                  }
                  for(let i=0; i<info.length;i++){
                    temp = info[i].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp}});
                    await new Promise(r => setTimeout(r, 20));
                  }
                  for(let k=0;k<freq1.length;k++){
                    temp1 = freq1[k].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp1}});
                  }
                  for(let j=0;j<freq2.length;j++){
                    temp2 = freq2[j].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp2}});
                  }
                }else{
                  string = "Person does not exist";
                }
              }else{
                string = "Is already an writer for this Film";
              }
            }
            else if(field ==="ACTORS"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              const olddata = await MovieSchema.find({Title:data.Title}).select("Actors");
              const totaldata = await MovieSchema.find({Title:data.Title}).select("Actors Director Writer");
              var freq2 = totaldata[0].Writer.split(",")
              var freq1 = totaldata[0].Director.split(",")
              var info = olddata[0].Actors.split(",");
              for (let z=0; z<freq2.length; z++){
                  var index =freq2[z].indexOf("(");
                  if (index != "-1"){
                    freq2[z]=freq2[z].substring(0,index-1);
                  }
               }
               for (let c=0; c<freq1.length; c++){
                   var index =freq1[c].indexOf("(");
                   if (index != "-1"){
                     freq1[c]=freq2[c].substring(0,index-1);
                   }
                }
              for(let i=0; i<info.length;i++){
                if (newPeople===info[i].trim()){
                  check=false;
                }
              }
              if (check === true){
                console.log("adding");
                newPeople+=", "+olddata[0].Actors;
                const checkfilm = await PersonSchema.exists({name:data.Change});
                await new Promise(r => setTimeout(r, 20));
                if (checkfilm === true){
                    t = await NotifPersonSchema.exists({"name":data.Change});
                    if (t===false){
                      console.log("was false so making notif");
                      var notifperson = new NotifPersonSchema({"name":data.Change});
                      notifperson.save();
                      await new Promise(r => setTimeout(r, 20));
                      await NotifPersonSchema.updateOne({"name":data.Change},{$push:{films:personobj}});
                      await MovieSchema.updateOne({Title:data.Title},{$set: {Actors:newPeople}});
                      await PersonSchema.updateOne({"name":data.Change},{$push:{films:data.Title}});
                    }else{
                    await MovieSchema.updateOne({Title:data.Title},{$set: {Actors:newPeople}});
                    await PersonSchema.updateOne({"name":data.Change},{$push:{films:data.Title}});
                    await NotifPersonSchema.updateOne({"name":data.Change},{$push:{films:personobj}});
                    console.log("was true so adding notif");
                  }
                  for(let i=0; i<info.length;i++){
                    temp = info[i].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp}});
                    await new Promise(r => setTimeout(r, 20));
                  }
                  for(let k=0;k<freq1.length;k++){
                    temp1 = freq1[k].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp1}});
                  }
                  for(let j=0;j<freq2.length;j++){
                    temp2 = freq2[j].trim();
                    await PersonSchema.updateOne({"name":data.Change},{$push:{frequency:temp2}});
                  }
                }else{
                  string = "Person does not exist";
                }
              }else{
                string = "Is already an Actor for this Film";
              }
            }
            else if(field ==="PLOT"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Plot:data.Change}});
            }
            else if(field ==="LANGUAGE"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Language:data.Change}});
            }
            else if(field ==="COUNTRY"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Country:data.Change}});
            }
            else if(field ==="AWARDS"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Awards:data.Change}});
            }
            else if(field ==="POSTER"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Poster:data.Change}});
            }
            else if(field ==="RATINGS"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$pull:{Ratings:{Source:data.Change.Source}}});
              await MovieSchema.updateOne({Title:data.Title},{$push: {Ratings:data.Change}});
            }
            else if(field ==="METASCORE"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Metascore:data.Change}});
            }
            else if(field ==="IMDBRATING"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {imdbRating:data.Change}});
            }
            else if(field ==="IMDBVOTES"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {imdbVotes:data.Change}});
            }
            else if(field ==="IMDBID"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {imdbID:data.Change}});
            }
            else if(field ==="TYPE"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Type:data.Change}});
            }
            else if(field ==="DVD"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {dvd:data.Change}});
            }
            else if(field ==="BOXOFFICE"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {BoxOffice:data.Change}});
            }
            else if(field ==="PRODUCTION"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Production:data.Change}});
            }
            else if(field ==="WEBSITE"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Website:data.Change}});
            }
            else if(field ==="RESPONSE"){
              await UserSchema.updateOne({username:data.user},{$push:{Contributions:data.Title}});
              await new Promise(r => setTimeout(r, 20));
              await MovieSchema.updateOne({Title:data.Title},{$set: {Response:data.Change}});
            }else{
              string = "Field is not an option";
            }
        }else{
          string = "Movie does not exist!";
        }
    }
    catch(err){
        {
            return next(err);
        }
    }
    res.send(string);
})

app.post('/Genre', async (req, res, next) => {
    console.log("Posted");
    res.set('Content-Type','application/json');
    var data = req.body
    try{
        const genre = await MovieSchema.find({Genre:{$regex : ".*"+data.Title+".*"}});
        let g = []
        for(let i = 0; i < genre.length; i++){
            g.push(genre[i].Title+"<br>");
        }
        let a = JSON.stringify(g);
        let parsed = JSON.parse(a);
        res.send(parsed)
    }
    catch(err){
        return next(err);
    }
  })

app.post('/numRating', async(req,res,next)=>{
        var newNumRating = req.body
        var add = true;
        var total=0;
        res.set('Content-Type','application/json')
        try{
          const check = await RatingSchema.exists({Title:newNumRating.Title});
          if (check === false){
            let img = await MovieSchema.find({Title:newNumRating.Title}).select("Poster");
            let movie = new RatingSchema({"Title":newNumRating.Title,"Ratings":newNumRating,"Totalrating":newNumRating.Rating,"Review":[],"Poster":img[0].Poster});
            movie.save();
          }else{
            const movie = await RatingSchema.find({Title:newNumRating.Title}).select("Ratings");
            for(let i=0; i < movie[0].Ratings.length;i++){
              if(movie[0].Ratings[i].User === newNumRating.User){
                add = false;
              }
            }
            if(add === true){
              await RatingSchema.updateOne({Title:newNumRating.Title},{$push:{Ratings:newNumRating}});
            }else{
              await RatingSchema.updateOne({Title:newNumRating.Title},{$pull:{Ratings:{User:newNumRating.User}}});
              await RatingSchema.updateOne({Title:newNumRating.Title},{$push:{Ratings:newNumRating}});
            }
            const movie2 = await RatingSchema.find({Title:newNumRating.Title});
            for(let j=0; j<movie2[0].Ratings.length;j++){
              total += parseInt(movie2[0].Ratings[j].Rating,10)
            }
            total= (total/movie2[0].Ratings.length);
            await RatingSchema.updateOne({Title:newNumRating.Title},{$set: {Totalrating: total}});
          }
          res.send();

        }catch (err) { return next(err); }
  });

app.post('/TextRating', async(req,res,next)=>{
          var newNumRating = req.body
          var add = true;
          var total=0;
          res.set('Content-Type','application/json')
          try{
            let date_ob = new Date();
            console.log(date_ob);
            if (newNumRating.User !== null){
              const check = await RatingSchema.exists({Title:newNumRating.Title});
              let review = new reviewObj(newNumRating.User,newNumRating.Rating);
              let userside = new userRevObj(newNumRating.User,newNumRating.Rating,newNumRating.Title,date_ob);
              if (check === false){
                let img = await MovieSchema.find({Title:newNumRating.Title}).select("Poster");
                let movie = new RatingSchema({"Title":newNumRating.Title,"Ratings":[],"Totalrating":"0","Review":[review],"Poster":img[0].Poster});
                await UserSchema.updateOne({username:newNumRating.User},{$push:{Reviews:userside}});
                movie.save();
              }else{
                const movie = await RatingSchema.find({Title:newNumRating.Title}).select("Review");
                for(let i=0; i < movie[0].Review.length;i++){
                  if(movie[0].Review[i].username === newNumRating.User){
                    add = false;
                  }
                }
                if(add === true){
                  await RatingSchema.updateOne({Title:newNumRating.Title},{$push:{Review:review}});
                  await UserSchema.updateOne({username:newNumRating.User},{$push:{Reviews:userside}});
                }else{
                  await RatingSchema.updateOne({Title:newNumRating.Title},{$pull:{Review:{username:review.username}}});
                  await RatingSchema.updateOne({Title:newNumRating.Title},{$push:{Review:review}});
                  var title = newNumRating.Title;
                  await UserSchema.updateOne({username:newNumRating.User},{$pull:{Reviews:{title}}});
                  await UserSchema.updateOne({username:newNumRating.User},{$push:{Reviews:userside}});
                }
              }
            }
            res.send();
          }catch (err) { return next(err); }
  });

app.post('/Year', async (req, res, next) => {
    console.log("Posted");
    res.set('Content-Type','application/json');
    var data = req.body
    try{
        const year = await MovieSchema.find({Year:data.Title});
        let g = []
        for(let i = 0; i < year.length; i++){
            g.push(year[i].Title+"<br>");
        }
        let a = JSON.stringify(g);
        let parsed = JSON.parse(a);
        res.send(parsed)
    }
    catch(err){
        return next(err);
    }
  })

app.post('/MinRating', async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  try{
    let y = [];
    const rating = await RatingSchema.find();
    for(let i = 0; i < rating.length; i++){
      if(rating[i].Totalrating >= data.Title){
        y.push(rating[i].Title+"<br>");
      }
    }
    let data2 = JSON.stringify(y);
    res.send(data2);
  }
  catch(err){
      return next(err);
  }
})

app.post('/followers',async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  var pass = true;
  var date= new Date();
  var personObj = new personDate(data.person,date,data.person);
  try{
    console.log(data)
    console.log(data);
    const follower = await PersonSchema.find({name:data.person}).select("followers");
    console.log(follower[0].followers);
      for(let i = 0; i < follower[0].followers.length; i++){
        if(follower[0].followers[i] === data.user){
          pass = false;
          break;
        }
      }
      if(pass === true){
        await PersonSchema.updateOne({name:data.person},{$push:{followers:data.user}});
        await UserSchema.updateOne({username:data.user},{$push:{PersonFollowing:personObj}});
      }
      else{
        await PersonSchema.updateOne({name:data.person},{$pull:{followers:data.user}});
        var name=data.person;
        await UserSchema.updateOne({username:data.user},{$pull:{PersonFollowing:{name:name}}});
      }
      res.send();
  }
  catch(err){
      return next(err);
  }
})
function sendTitle(Title){
  this.Title = Title;
}
app.post('/RecFilms',async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  var pass = true;
  try{
    console.log(data)
    const follower = await PersonSchema.find();
    for(let i = 0; i < follower.length; i++){
      for(let j = 0; j < follower[i].followers.length;j++){
        if(follower[i].followers[j] === data.Title){
          let films = follower[i].films;
          for(let k = 0; k < films.length;k++){
            const u = await UserSchema.find({username:data.Title}).select("RecFilms");

            if(u[0].RecFilms.includes(films[k]) === false) {
              await new Promise(r => setTimeout(r, 40));
              await UserSchema.updateOne({username:data.Title},{$push:{RecFilms:films[k]}});
            }
          }
        }
      }
    }
    const u = await UserSchema.find({username:data.Title}).select("RecFilms");
    let y = u[0].RecFilms;

    let data2 = new sendTitle(y)
    let data3 = JSON.stringify(data2);
    //console.log(data3);
    res.send(data3);
  }
  catch(err){
      return next(err);
  }
})

app.post('/minReccMovies',async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  try{
    let g = []
    console.log(data)
    const u = await UserSchema.find({username:data.User}).select("RecFilms");
    for(let i = 0; i < u[0].RecFilms.length; i++){
      const x2 = await MovieSchema.find({Title:u[0].RecFilms[i]})
      g.push(x2);
    }
    //console.log(g[1][0].imdbRating)
    let p = [];
    for(let j = 0; j < g.length; j++){
      if(parseFloat(g[j][0].imdbRating) >= parseFloat(data.Rating)){
        p.push(g[j][0].Title)
      }
    }
    let data2 = new sendTitle(p);
    let data3 = JSON.stringify(data2);
    //console.log(data2);
    res.send(data3);
  }
  catch(err){
      return next(err);
  }
})

app.post('/findFilm',async (req, res, next) => {
      console.log("Posted");
      res.set('Content-Type','text/plain');
      var movie = req.body
      let pass = false;
      try{
        const movies2 = await MovieSchema.find().select("Title");
        for(let i = 0; i < movies2.length; i++){
          if(movies2[i].Title.toUpperCase() === movie.toUpperCase()){
            //const movies = await MovieSchema.find({Title:movies2[i].Title});
            res.send(movies2[i].Title);
            pass = true;
          }
        }
        if(pass === false){
          console.log("fuxk")
          res.send("no movie");
        }
      }
      catch(err){
          return next(err);
      }
    })

app.post('/theMovie',async (req, res, next) => {
        console.log("Posted");
        res.set('Content-Type','application/json');
        var data = req.body
        try{
          const movies2 = await MovieSchema.find().select("Title");
          for(let i = 0; i < movies2.length; i++){
            if(movies2[i].Title.toUpperCase() === data.Title.toUpperCase()){
              const movies = await MovieSchema.find({Title:movies2[i].Title});
              res.send(movies[0]);
            }
          }
        }
        catch(err){
            return next(err);
        }
    })

app.get('/AllMovies', async (req, res, next) => {
        console.log("Posted");
        res.set('Content-Type','application/json');
        try{
            const movies = await MovieSchema.find().limit(12);
            let m = []
            for(let i = 0; i < movies.length; i++){
                if(movies[i].Title === "#DUPE#"){
                    continue;
                }
                else{
                    m.push(movies[i]);
                }
            }
            let a = JSON.stringify(m);
            res.send(a);
        }
        catch(err){
            return next(err);
        }
    })
app.post('/findCast', async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','text/plain');
  var data = req.body;
  console.log(data);
  try{
    const actors2 = await MovieSchema.find().select("Actors");
    for(let i = 0; i < actors2.length; i++){
      if(actors2[i].Actors.toUpperCase().includes(data.toUpperCase()) === true){
        let x = actors2[i].Actors.split(", ");
        for(let j = 0; j < x.length; j++){
          if(x[j].toUpperCase() === data.toUpperCase()){
            res.send(x[j]);
            break;
          }
        }
        break;
      }
    }
    const dir2 = await MovieSchema.find().select("Director");
    for(let i = 0; i < dir2.length; i++){
      if(dir2[i].Director.toUpperCase().includes(data.toUpperCase()) === true){
        let y = dir2[i].Director.replace('(', ', ');
        let x = y.split(", ");
        for(let j = 0; j < x.length; j++){
          if(x[j].toUpperCase() === data.toUpperCase()){
            res.send(x[j]);
            break;
          }
        }
        break;
      }
    }
    const writer2 = await MovieSchema.find().select("Writer");
    for(let i = 0; i < writer2.length; i++){
      if(writer2[i].Writer.toUpperCase().includes(data.toUpperCase()) === true){
        //console.log(writer2[i].Writer);
        let x = writer2[i].Writer.split(", ");
        for (let c=0; c<x.length; c++){
          var index =x[c].indexOf("(");
          if (index != "-1"){
            x[c]=x[c].substring(0,index-1);
          }
        }
        //console.log(x);
        for(let j = 0; j < x.length; j++){
          if(x[j].toUpperCase() === data.toUpperCase()){
            res.send(x[j]);
            break;
          }
        }
        break;
      }
    }
  }
  catch(err){
      return next(err);
  }
})

app.post('/castProf', async (req, res, next) => {
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body;
  //console.log(data)
  try{
      const actor = await MovieSchema.find({Actors:{$regex : ".*"+data.Title+".*"}}).select("Title");
      const writer = await MovieSchema.find({Writer:{$regex : ".*"+data.Title+".*"}}).select("Title");
      const director = await MovieSchema.find({Director:{$regex : ".*"+data.Title+".*"}}).select("Title");
      var Obj = {
          acted: [],
          directed: [],
          wrote: []
      };
      Obj.acted.push(actor);
      Obj.directed.push(writer);
      Obj.wrote.push(director);
      Obj = JSON.stringify(Obj);
      res.send(Obj);
  }
  catch(err){
      return next(err);
  }
})

//USER APPS START HERE------------------------------------------------------------------------------------

app.get("/Following", function(req,res){
  res.sendFile(path.join(__dirname + "/Following.html"));
  console.log("Following.html");
});

app.get("/userlook", function(req,res){
  res.sendFile(path.join(__dirname + "/SearchUsers"));
  console.log("SearchUsers.html");
});


app.get("/users", async(req,res,next)=>{
  try{
    users=[];
    let name = req.query.name;
    if(typeof(name)==='undefined'){
      const person = await UserSchema.find().select("username");
      console.log(person);
      for(let i=0;i<person.length;i++){
        users.push(person[i].username);
      }
      res.send(users);
    }else{
      const query = { 'username': { $regex: new RegExp(`^${name}$`), $options: 'i' } };
      UserSchema.find(query).sort({ name: 'asc' }).select("username")
          .then(name => {
              res.json(name);
          })
          .catch(error => {
              // error..
              res.send("that user does not exist");
          });
    }
  }catch (err) { return next(err); }
});


app.get("/users/:user", async(req,res,next)=>{
  try{
    let userlook = (req.params.user);
    const user = await UserSchema.find({_id:userlook}).select("username contributing Reviews");
    res.send(user)
  }catch (err) { return next(err); }
});

app.get("/people", async(req,res,next)=>{
  try{
    names=[];
    let name = req.query.name;
    if(typeof(name)==='undefined'){
      const person = await PersonSchema.find().select("name");
      for(let i=0;i<person.length;i++){
        names.push(person[i].name);
      }
      res.send(names);
    }else{
      const query = { 'name': { $regex: new RegExp(`^${name}$`), $options: 'i' } };
      PersonSchema.find(query).sort({ name: 'asc' }).select("name")
          .then(name => {
              res.json(name);
          })
          .catch(error => {
              // error..
              res.send("that user does not exist");
          });
    }
  }catch (err) { return next(err); }
});




app.get("/people/:person", async(req,res,next)=>{
  try{
    let name = req.params.person;
    const person = await PersonSchema.find({_id:name}).select("name");
    res.send(person);

  }catch (err) { return next(err); }
});



//runs to check if user can log in or not

app.post('/authentication', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var clientauth = req.body
  try {
     const serverauth = await UserSchema.find({username:clientauth.username}).select("_id username password");

     if ( typeof(serverauth[0]) === 'undefined'){
       res.send("Invalid Username or Password");
     }else{
       if(await bcrypt.compare(clientauth.password,serverauth[0].password)){
           console.log("got here");
           await UserSchema.updateOne({username:clientauth.username},{$set: {signedin: true}});
           res.send("FirstWebpage.html");
       }else{
         res.send("Invalid Username or Password");
       }
     }
  } catch (err) { return next(err); }
});

//Checks if the user is signed in
app.post('/signedin?', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var clientName = req.body
  try {
    const serverUser = await UserSchema
      .find({username:clientName}).select("signedin")
      if(serverUser[0].signedin === true){
          res.send(true);
      }else if(serverUser[0].signedin === false){
        res.send(false);
      }
      else{
        console.log("Request Failed");
      }
  } catch (err) { return next(err); }
});

app.post('/showaccountsettings?', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var clientName = req.body
  try {
    const serverUser = await UserSchema
      .find({username:clientName}).select("signedin")
      if(serverUser[0].signedin === true){
          res.send("/AccountSettings.html");
      }else if(serverUser[0].signedin === false){
          res.send("/LogInPage.html");
      }
      else{
        res.send("/LogInPage.html");
      }
  } catch (err) { return next(err); }
});

app.post('/signout',async (req,res,next)=>{
  //this will read the body
  var clientName = req.body
  res.set('Content-Type','text/plain')
  try {
    const userInfo = await UserSchema
      .find({username:clientName})
      if(userInfo[0].signedin === true){
         console.log("are here");
         await UserSchema.updateOne({username:clientName},{$set: {signedin: false}});
         res.send();
      }else if(serverUser[0].signedin === false){
        res.send(false);
      }
      else{
        console.log("Request Failed");
      }
    } catch (err) { return next(err); }
})

app.post('/toggleContribute?', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var clientName = req.body
  try {
    const serverUser = await UserSchema
      .find({username:clientName}).select("contributing")
      if(serverUser[0].contributing === true){
          res.send(true);
      }else if(serverUser[0].contributing === false){
        res.send(false);
      }
      else{
        console.log("Request Failed");
      }
  } catch (err) { return next(err); }
});

//Makes a new user
app.post('/newuser', async(req,res,next)=>{
  //this will read the body
  let pass = 0;
  var newUserRequest = req.body
  //this will set a response of the json type
  res.set('Content-Type','application/json')
  //this sends info back to the user
  //res.send(info)
  try{
    //Checks if a item exists
    const newUser = await UserSchema.exists({username:newUserRequest.username})
    if (newUser === false){
      const hashedPassword = await bcrypt.hash(newUserRequest.password,10);
      var user = new UserSchema({"firstname":newUserRequest.firstname,"lastname":newUserRequest.lastname,"name":newUserRequest.firstname.toUpperCase()+" "+newUserRequest.lastname.toUpperCase(),"username":newUserRequest.username,"password":hashedPassword,"signedin":false,"contributing":false,"profilepic":"https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png","Place":"0","follPlace":"0","RecFilms":[],"Reviews":[],"PersonFollowing":[],"Contributions":[]});
      user.save();
      res.send("LogInPage.html");
    }else{
      console.log("This user exists!");
    }
  }catch (err) { return next(err); }
})

//Sends the users personal account information
app.post('/showInfo?', async(req,res,next) =>{
  //this will read the body
  var clientName = req.body
  //this will set a response of the text type
  res.set('Content-Type','application/json')
  try {
    const userInfo = await UserSchema
      .find({username:clientName}).select("-password")
      if(userInfo[0].signedin === true){
         data = new safeUserInfo(userInfo[0].firstname,userInfo[0].lastname,userInfo[0].username,userInfo[0].profilepic);
         res.send(data);
      }else if(serverUser[0].signedin === false){
        res.send(false);
      }
      else{
        console.log("Request Failed");
      }
  } catch (err) { return next(err); }
})

//Updates all the users profile
app.post('/updateProfile',async (req,res,next)=>{
  //this will read the body
  var newinfo = req.body
  //this will set a response of the text type
  res.set('Content-Type','text/plain')
  try {
    const userInfo = await UserSchema
    .find({username:newinfo.username})
    if(userInfo[0].signedin === true){
      if (newinfo.firstname != null){
        await UserSchema.updateOne({username:newinfo.username},{$set: {firstname:newinfo.firstname}});
      }
      if (newinfo.lastname != null){
        await UserSchema.updateOne({username:newinfo.username},{$set: {lastname: newinfo.lastname}});
      }
      if (newinfo.pfp != null){
        await UserSchema.updateOne({username:newinfo.username},{$set: {profilepic: newinfo.pfp}});
      }
       res.send();
    }else if(serverUser[0].signedin === false){
      res.send(false);
    }
    else{
      console.log("Request Failed");
    }
  }catch (err) { return next(err); }
})


app.post('/deleteAccount',async (req,res,next)=>{
  var user = req.body
  res.set('Content-Type','text/plain')
  try {
    await UserSchema.remove({username:user})
    res.send("/FirstWebpage.html");
  }catch (err) { return next(err); }
})

app.post("/queryusers", async (req, res, next)=> {
  try{
    let users = []
    res.set("Content-type","application/json");
    var searcheduser = req.body
    if(searcheduser.username === null){
      var num = 0;
      const usersize = await UserSchema
      .find().select("username profilepic");
      const userlist = await UserSchema
      .find().skip(num).limit(6).select("username profilepic");
      if (searcheduser.action === "1"){
        num+=0;
      }else if (searcheduser.action ==="2"){
        num+=6;
      }else{
        if (num>5){
            num-=6;
        }
      }
      if(num>usersize.length){
        num-=6;
      }
      for(let i=0; i < userlist.length; i++){
        users.push(userlist[i]);
      }
      users.push(searcheduser.action);
    }else{
      const amount = await UserSchema.find({username:searcheduser.username}).select("Place");
      var num = parseInt(amount[0].Place,10)
      const usersize = await UserSchema
      .find().select("username profilepic");
      const userlist = await UserSchema
      .find().skip(num).limit(6).select("username profilepic");
      console.log(userlist);
      if (searcheduser.action === "1"){
        num+=0;
      }else if (searcheduser.action ==="2"){
        num+=6;
      }else{
        if (num>5){
            num-=6;
        }
      }
      if(num>usersize.length){
        num-=6;
      }
      for(let i=0; i < userlist.length; i++ ){
        users.push(userlist[i]);
      }
      console.log(num);
      users.push(searcheduser.action);
      var string = num.toString()
      await UserSchema.updateOne({username:searcheduser.username},{$set: {Place:string}});
    }
    let a = JSON.stringify(users);
    let parsed = JSON.parse(a);
    res.send(parsed)
  }catch (err) { return next(err); }
  });

app.post("/listUsers", async (req, res, next)=> {
    try{
      var searcheduser = req.body
      res.set("Content-type","text/plain");
      const newUser = await UserSchema.exists({username:searcheduser})
      if (newUser == true){
          res.send("/ViewingPeople.html")
      }
    }catch (err) { return next(err); }
  });


app.post("/userlookup?", async (req, res, next)=>{
  try{
      var searcheduser = req.body
      res.set("Content-type","application/json");
      const userlist = await UserSchema.find({username:searcheduser.username});
      res.send(userlist[0]);
    }catch (err) { return next(err); }
});

app.post('/addFollower?',async (req,res, next)=>{
  try{
    var user = req.body
    var pass = true;
    res.set('Content-Type','application/json')
    const folImg = await UserSchema.find({username:user.friend}).select("profilepic");
    const following = await UserSchema.find({username:user.username}).select("Followers");
    for(let i=0;i<following[0].Followers.length;i++){
      if(following[0].Followers[i].username===user.friend){
        pass = false;
        break;
      }
    }
    let date = new Date();
    var data = new followerInfo(user.friend,folImg[0].profilepic,date);
    console.log(data);
    if (pass === true){
      await UserSchema.updateOne({username:user.username},{$push:{Followers:data}})
    }
    else{
        await UserSchema.updateOne({username:user.username},{$pull:{Followers:{username:data.username}}})
      }
  }catch (err) { return next(err); }
  res.set('Content-Type','application/json')
  res.send();
})

app.post('/following?',async(req,res,next)=>{
  try{
      var followobj = req.body
      console.log(followobj.username);
      var pass= true;
      res.set("Content-type","text/plain");
      if(followobj.username === null){
        pass=true;
      }else{
        const following = await UserSchema.find({username:followobj.username}).select("Followers");
        for(let i=0;i<following[0].Followers.length;i++){
          if(following[0].Followers[i].username===followobj.friend){
            pass = false;
            break;
          }
        }
      }
      res.send(pass);
    }catch (err) { return next(err); }
});

app.post("/queryUserNotifs", async (req, res, next)=> {
  res.set("Content-type","application/json")
  try{
    var searcheduser = req.body
    notif=[];
    notifsend=[];
    let following= [];
    let listofpersons=[];
    let length=0;
    res.set("Content-type","application/json");
    var num = parseInt(searcheduser.pages,10)
    const userfollow = await UserSchema
    .find({username:searcheduser.username}).select("Followers");
    const personfollow = await UserSchema
    .find({username:searcheduser.username}).select("PersonFollowing");
    //Add the people array to this next
    length=userfollow[0].Followers.length+personfollow[0].PersonFollowing.length;
    if (searcheduser.action === "1"){
      num+=0;
    }else if (searcheduser.action ==="2"){
      num+=1;
    }else{
      if (num>0){
          num-=1;
      }
    }
    for(let i=0; i < length; i++ ){
       following.push(userfollow[0].Followers[i]);
    }
    for(let k=0; k<personfollow[0].PersonFollowing.length;k++){
      listofpersons.push(personfollow[0].PersonFollowing[k]);
    }
    if(typeof(following[0]) === 'undefined'){

    }else{
      for(let j=0; j<following.length;j++){
          if(typeof(following[j])==='undefined'){

          }else{
            const check = await UserSchema.find({username:following[j].username}).select("Reviews");
            for(let k=0; k<check[0].Reviews.length;k++){
              if(check[0].Reviews[k].date>following[j].date){
                notif.push(check[0].Reviews[k]);
              }
            }
          }
      }
    }
    if(typeof(listofpersons[0])==='undefined'){

    }else{
      for(let h=0; h<listofpersons.length;h++){
        const check = await NotifPersonSchema.find({name:listofpersons[h].name}).select("films");
        if(typeof(check[0]) ==='undefined'){
        }else{
          for(let b=0; b<check[0].films.length;b++){
            if(check[0].films[b].date>listofpersons[h].date){
              notif.push(check[0].films[b]);
            }
          }
        }
      }
    }
    if(num*6/2>notif.length){
      num-=1;
    }
    for(let i=num;i<notif.length;i++){
      notifsend.push(notif[i]);
    }
    notifsend.push(searcheduser.action);
    notifsend.push(num);
    let a = JSON.stringify(notifsend);
    let parsed = JSON.parse(a);
    res.send(parsed)
  }catch (err) { return next(err); }
  });

app.post("/queryUserRev", async (req, res, next)=> {
  try{
    var searcheduser = req.body
    let reviews = []
    res.set("Content-type","application/json");
    var num = parseInt(searcheduser.pages,10)
    const reviewlist = await UserSchema
    .find({username:searcheduser.username}).select("Reviews")
    if (searcheduser.action === "1"){
      num+=0;
    }else if (searcheduser.action ==="2"){
      num+=5;
    }else{
      if (num>4){
          num-=5;
      }
    }
    if(num>reviewlist[0].Reviews.length){
      num-=5;
    }
    for(let i=num; i < reviewlist[0].Reviews.length; i++ ){
      reviews.push(reviewlist[0].Reviews[i]);
    }
    reviews.push(searcheduser.action);
    reviews.push(num);
    console.log(reviews);
    let a = JSON.stringify(reviews);
    res.send(a)
  }catch (err) { return next(err); }
  });

app.post("/queryFollowers", async (req, res, next)=> {
  try{
    var searcheduser = req.body
    let followers = []
    res.set("Content-type","application/json");
    var num = parseInt(searcheduser.pages,10)
    const followlist = await UserSchema
    .find({username:searcheduser.username}).select("Followers")
    if (searcheduser.action === "1"){
      num+=0;
    }else if (searcheduser.action ==="2"){
      num+=6;
    }else{
      if (num>5){
          num-=6;
      }
    }
    if(num>followlist[0].Followers.length){
      num-=6;
    }
    for(let i=num; i < followlist[0].Followers.length; i++ ){
      followers.push(followlist[0].Followers[i]);
    }
    followers.push(searcheduser.action);
    followers.push(num);
    let a = JSON.stringify(followers);
    let parsed = JSON.parse(a);
    res.send(parsed)
  }catch (err) { return next(err); }
  });

app.post("/queryWhoFollows", async (req, res, next)=> {
    try{
      var searcheduser = req.body
      let followers = []
      res.set("Content-type","application/json");
      var num = parseInt(searcheduser.pages,10)
        const followlist = await UserSchema
        .find({username:searcheduser.look}).select("Followers");
        if (searcheduser.action === "1"){
          num+=0;
        }else if (searcheduser.action ==="2"){
          num+=6;
        }else{
          if (num>5){
              num-=6;
          }
        }
        console.log(followlist[0]);
        if(num>followlist[0].Followers.length){
          num-=6;
        }
        for(let i=num; i < followlist[0].Followers.length; i++ ){
          followers.push(followlist[0].Followers[i]);
        }
        followers.push(searcheduser.action);
        followers.push(num);
      let a = JSON.stringify(followers);
      let parsed = JSON.parse(a);
      res.send(parsed)
    }catch (err) { return next(err); }
    });

app.post("/queryPeopleFollow", async (req, res, next)=> {
    try{
      var searcheduser = req.body
      let followers = []
      res.set("Content-type","application/json");
      var num = parseInt(searcheduser.pages,10)
        const followlist = await UserSchema
        .find({username:searcheduser.look}).select("PersonFollowing");
        if (searcheduser.action === "1"){
          num+=0;
        }else if (searcheduser.action ==="2"){
          num+=6;
        }else{
          if (num>5){
              num-=6;
          }
        }
        if(num>followlist[0].PersonFollowing.length){
          num-=6;
        }
        for(let i=num; i < followlist[0].PersonFollowing.length; i++ ){
          followers.push(followlist[0].PersonFollowing[i].name);
        }
        followers.push(searcheduser.action);
        followers.push(num);
      let a = JSON.stringify(followers);
      let parsed = JSON.parse(a);
      res.send(parsed)
    }catch (err) { return next(err); }
    });


app.post('/addPerson', async(req,res,next)=>{
      //this will read the body
      let pass = 0;
      var newPersonRequest = req.body
      res.set('Content-Type','application/json')
      try{
        //Checks if a item exists
        const newPerson = await PersonSchema.exists({name:newPersonRequest})
        if (newPerson === false){
          var index = newPersonRequest.indexOf(" ");
          var firstname = newPersonRequest.slice(0,index);
          var lastname = newPersonRequest.slice(index+1);
          var person = new PersonSchema({"firstname":firstname,"lastname":lastname,"name":newPersonRequest});
          person.save();
          res.send("Successfully Added!")
        }else{
          res.send("This person already exists!");
        }
      }catch (err) { return next(err); }
});

app.post('/toggleContributing', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var contribUser = req.body
  try {
     const user = await UserSchema.find({username:contribUser}).select("contributing");
      if(user[0].contributing === false){
          await UserSchema.updateOne({username:contribUser},{$set: {contributing: true}});
      }else{
         await UserSchema.updateOne({username:contribUser},{$set: {contributing: false}});
      }
      res.send();
  } catch (err) { return next(err); }
});

app.post('/contributing?', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var contribUser = req.body
  try {
    if (typeof(contribUser)!='undefined'){
      const user = await UserSchema.find({username:contribUser}).select("contributing");
       if(user[0].contributing === false){
           res.send("hidden");
       }else{
          res.send("visible");
       }
    }else{
      res.send("hidden");
    }
  } catch (err) { return next(err); }
});


app.post('/togglePersonfollow?',async(req,res,next)=>{
  res.set('Content-Type','text/plain');
  var data=req.body;
  var found = false;
  try{
      const following = await PersonSchema.find({name:data.friend});
      for(let i=0; i < following[0].followers.length;i++){
        if(following[0].followers[i]===data.username){
          found =true;
        }
      }
      res.send(found);
    }catch (err) { return next(err); }
});

app.post('/findFrequent',async(req,res,next)=>{
  res.set('Content-Type','application/json');
  var data=req.body;
  top=[];
  //console.log(data);
  var made = false;
  var count =0;
  try{
      const coworkers = await PersonSchema.find({name:data}).select("frequency");
       const map = coworkers[0].frequency.reduce((acc, e) => acc.set(e, (acc.get(e) || 0) + 1), new Map());
       for (let [key, value] of  map.entries()) {
          obj = new freqObj(key,value);
          count +=1;
          if(made === false){
            top.push(obj)
            if(count >3){
              made = true;
            }
          }else{
            for(let j=0;j<top.length;j++){
              if(obj.freq> top[j].freq){
                top[j] = obj;
                break;
              }
            }
          }
        }
      let a = JSON.stringify(top);
      //let parsed = JSON.parse(a);
      res.send(a)
    }catch (err) { return next(err); }
});

var server = app.listen(3000, function() {
  var host = server.address().address;
  var port = server.address().port;
  console.log("Movie Database listening at http://%s:%s", host, port);
});
