// this line allows us to use the expressjs module
var express = require("express");
var path = require("path");
var bodyParser = require('body-parser');
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
    follPlace: String
   })
);

const PersonSchema = mongoose.model(
  'personlists',
  new mongoose.Schema({
    firstname: String,
    lastname: String,
    name:String,
   })
);

//Objects for client side
function safeUserInfo(firstname,lastname,username,profilepic){
  this.firstname = firstname;
  this.lastname = lastname;
  this.username = username;
  this.profilepic = profilepic;
}

function followerInfo(username,profilepic){
  this.username = username;
  this.profilepic = profilepic;
}

//HERE IS ALL OF THE PAGE SERVING
// Add the abillity to serve our static files from the public directory
app.use(express.static("public"));

// Here we serve up our index page
app.get("/", function(req, res) {
  res.sendFile(path.join(__dirname + "/FirstWebpage.html"));
  console.log("FirstWebpage");
});

app.get("/SearchUsers.html", function(req, res) {
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

app.get("/ViewingPeople.html", function(req,res){
  res.sendFile(path.join(__dirname + "/ViewingPeople.html"));
  console.log("ViewingPeople.html");
});

app.get("/MovieInfo.html", function(req,res){
  res.sendFile(path.join(__dirname + "/MovieInfo.html"));
  console.log("MovieInfo.html");
});

app.get("/Following", function(req,res){
  res.sendFile(path.join(__dirname + "/Following.html"));
  console.log("Following.html");
});

app.get("/users", async (req,res,next)=>{
  try{
    let name = req.query.name;
    const newUser = await UserSchema.exists({firstname:name})
    if (newUser === true){
       const user= await UserSchema.find({firstname:name}).select("-password");
       res.send(user);
    }else{
    res.sendFile(path.join(__dirname + "/SearchUsers.html"));
   }
  }catch (err) { return next(err); }
});

app.get("/users/:user", async(req,res,next)=>{
  try{
    let userlook = (req.params.user);
    const user = await UserSchema.find({username:userlook}).select("username contributing reviews");
    res.send(user)
  }catch (err) { return next(err); }
});

app.get('/userbase.js', function(req, res){
    res.sendFile(__dirname + '/userbase.js');
});

//runs to check if user can log in or not

app.post('/authentication', async (req, res, next) => {
  res.set('Content-Type','text/plain');
  var clientauth = req.body
  try {
    const serverauth = await UserSchema
      .find({username:clientauth.username}).select("_id username password")
      if(serverauth[0].password === clientauth.password){
          console.log("got here");
          await UserSchema.updateOne({username:clientauth.username},{$set: {signedin: true}});
          res.send("FirstWebpage.html");
      }else{
        console.log("Request Failed");
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
      var user = new UserSchema({"firstname":newUserRequest.firstname,"lastname":newUserRequest.lastname,"name":newUserRequest.firstname.toUpperCase()+" "+newUserRequest.lastname.toUpperCase(),"username":newUserRequest.username,"password":newUserRequest.password,"signedin":false,"contributing":false,"profilepic":"https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png","Place":"0","follPlace":"0"});
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
    res.send("/AccountSettings.html");
  }catch (err) { return next(err); }
})

app.post("/queryusers", async (req, res, next)=> {
  try{
    let users = []
    res.set("Content-type","application/json");
    var searcheduser = req.body
    var tempuser;
    if(searcheduser.username === null){
      var num = 0;
      const userlist = await UserSchema
      .find().skip(num).limit(6).select("username profilepic")
      if (searcheduser.action === "1"){
        num+=0;
      }else if (searcheduser.action ==="2"){
        num+=6;
      }else{
        if (num>5){
            num-=6;
        }
      }
      if(num>userlist.length){
        num-=6;
      }
      for(let i=num; i < userlist.length; i++){
        users.push(userlist[i]);
      }
      users.push(searcheduser.action);
    }else{
      const amount = await UserSchema.find({username:searcheduser.username}).select("Place");
      var num = parseInt(amount[0].Place,10)
      const userlist = await UserSchema
      .find().skip(num).limit(6).select("username profilepic")
      if (searcheduser.action === "1"){
        num+=0;
      }else if (searcheduser.action ==="2"){
        num+=6;
      }else{
        if (num>5){
            num-=6;
        }
      }
      if(num>userlist.length){
        num-=6;
      }
      for(let i=num; i < userlist.length; i++ ){
        users.push(userlist[i]);
      }
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
    var data = new followerInfo(user.friend,folImg[0].profilepic);
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
      var pass= true;
      res.set("Content-type","text/plain");
      const following = await UserSchema.find({username:followobj.username}).select("Followers");
      for(let i=0;i<following[0].Followers.length;i++){
        if(following[0].Followers[i].username===followobj.friend){
          pass = false;
          break;
        }
      }
      res.send(pass);
    }catch (err) { return next(err); }
});

app.post("/queryFollowers", async (req, res, next)=> {
  try{
    var searcheduser = req.body
    let followers = []
    res.set("Content-type","application/json");
    const amount = await UserSchema.find({username:searcheduser.username}).select("follPlace");
    var num = parseInt(amount[0].follPlace,10)
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
    var string = num.toString()
    await UserSchema.updateOne({username:searcheduser.username},{$set: {follPlace:string}});
    let a = JSON.stringify(followers);
    let parsed = JSON.parse(a);
    res.send(parsed)
  }catch (err) { return next(err); }
  });

var server = app.listen(8080, function() {
  var host = server.address().address;
  var port = server.address().port;
  console.log("Movie Database listening at http://%s:%s", host, port);
});
