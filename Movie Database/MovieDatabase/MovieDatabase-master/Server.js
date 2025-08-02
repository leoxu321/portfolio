// this line allows us to use the expressjs module
var express = require("express");
var path = require("path");
var bodyParser = require('body-parser');
var app = express();
// Add this line so we can serve files from our local
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.text());
const fs = require('fs')
users = require("./Usersv2.json");
movies = require("./movie-data.json");
movieRatings = require("./movieRatings.json");

// directory



function safeUserInfo(firstname,lastname,username,profilepic){
  this.firstname = firstname;
  this.lastname = lastname;
  this.username = username;
  this.profilepic = profilepic;
}

function createMovieForRating(title,rating,total){
  this.title = title;
  this.rating = rating;
  this.total = total;
}


function writeData(data){
  const jsonString = JSON.stringify(data)
  fs.writeFile('./Usersv2.json', jsonString, err => {
      if (err) {
          console.log('Error writing file', err)
      } else {
          console.log('Successfully wrote file')
      }
  })
}

function writeRatingData(data){
  const jsonString = JSON.stringify(data)
  fs.writeFile('./movieRatings.json', jsonString, err => {
      if (err) {
          console.log('Error writing file', err)
      } else {
          console.log('Successfully wrote file')
      }
  })
}


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

app.get('/userbase.js', function(req, res){
    res.sendFile(__dirname + '/userbase.js');
});

app.post('/findFilm',function(req,res){
  console.log("Posted");
  res.set('Content-Type','text/plain');
  var movie = req.body
  found = 0;
  for(let i =0; i < movies.length;i++){
    let m = movies[i].Title.toUpperCase();
    if(m===movie){
      res.send("/MovieInfo.html");
      found = 1;
    }
  }
  if(found === 0){
    res.send("This is not a valid movie");
  }
})

app.post('/Genre', function(req,res){
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  console.log(data.Title)
  let g = []
  for(let i = 0; i < movies.length; i++){
    if(movies[i].Genre.includes(data.Title)){
      g.push(movies[i].Title)
    }
  }
  let a = JSON.stringify(g);
  let parsed = JSON.parse(a);
  res.send(parsed)
})

app.post('/Year', function(req,res){
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  console.log(data.Title)
  let y = []
  for(let i = 0; i < movies.length; i++){
    if(data.Title === movies[i].Year){
      y.push(movies[i].Title)
      console.log(movies[i])
    }
  }
  let a = JSON.stringify(y);
  let parsed = JSON.parse(a);
  res.send(parsed)
})

app.post('/Rating', function(req,res){
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  console.log(data.Title)
  let y = []
  for(let i = 0; i < movies.length; i++){
    if(parseInt(data.Title) <= parseInt(movies[i].imdbRating)){
      if(movies[i].Title !== "#DUPE#"){
        y.push(movies[i].Title)
      }
      //console.log(movies[i])
    }
  }
  let a = JSON.stringify(y);
  let parsed = JSON.parse(a);
  res.send(parsed)

})

app.post('/theMovie',function(req,res){
  console.log("Posted");
  res.set('Content-Type','application/json');
  var data = req.body
  for(let i =0; i < movies.length;i++){
    if(data.Title.toUpperCase() === movies[i].Title.toUpperCase()){
      res.send(movies[i]);
    }
  }
})

app.post('/AllMovies', function(req,res){
  console.log("Posted");
  res.set('Content-Type','application/json');
  let m = []
  for(let i = 0; i< movies.length; i++){
      m.push(movies[i].Poster)
  }
  let a = JSON.stringify(m);
  let parsed = JSON.parse(a);
  res.send(parsed)
})

//runs to check if user can log in or not
app.post('/authentication',function(req,res){
  console.log("Posted");
  res.set('Content-Type','text/plain');
  var authinfo = req.body
  var failed = false;
  for(var key in users){
    if (authinfo.username === users[key].username){
      if(authinfo.password === users[key].password){
        temp = users[key];
        console.log(temp.profilepic);
        temp.signedin = true;
        //delete users[key];
        console.log(temp.username);
        users[temp.username] = temp;
        writeData(users);
        res.send("FirstWebpage.html");
        failed =false;
        return;
      }else{
        failed= true;
      }
    }else{
      failed = true;
    }
  }
  if(failed === true){
      res.send("Invalid username or password");
  }
})

//Runs to add a new user to the server
app.post('/newuser',function(req,res){
  //this will read the body
  let pass = 0;
  var newUser = req.body
  //this will set a response of the json type
  res.set('Content-Type','application/json')
  //this sends info back to the user
  //res.send(info)
  for(var key in users){
    if (newUser.username === users["username"]){
      res.send("That username is taken")
      pass=1;
    }
  }
  if (pass != 1){
    users[newUser.username]=newUser;
    writeData(users);
    res.send("LogInPage.html");
  }
})

app.post('/signedin?',function(req,res){
  //this will read the body
  var checkUser = req.body
  //this will set a response of the text type
  res.set('Content-Type','text/plain')
  for(var key in users){
    //console.log(checkUser);
    //console.log(users[key]);
    if (checkUser === users[key].username){
       if(users[key].signedin === true){
         res.send("true");
       }
    }
  }
})

app.post('/signout',function(req,res){
  //this will read the body
  var user = req.body
  //this will set a response of the text type
  res.set('Content-Type','text/plain')
  for(var key in users){
    //console.log(checkUser);
    console.log(users[key]);
    if (user === users[key].username){
        temp = users[key];
        temp.signedin = false;
        //delete users[key];
        console.log(temp.username);
        users[temp.username] = temp;
         writeData(users);
         res.send();
    }
  }
})

app.post('/showInfo?',function(req,res){
  //this will read the body
  var user = req.body
  //this will set a response of the text type
  res.set('Content-Type','application/json')
  for(var key in users){
    //console.log(checkUser);
    //console.log(users[key]);
    if (user === users[key].username){
      if(users[key].signedin === true){
        data = new safeUserInfo(users[key].firstname,users[key].lastname,users[key].username,users[key].profilepic);
        res.send(data);
      }
    }
  }
})

app.post('/updateProfile',function(req,res){
  //this will read the body
  var userinfo = req.body
  //this will set a response of the text type
  res.set('Content-Type','text/plain')
  for(var key in users){
    //console.log(checkUser);
    //console.log(users[key]);
    if (userinfo.username === users[key].username){
      if(users[key].signedin === true){
        let temp = users[key];
        users[key].firstname =userinfo.firstname;
        res.send();
        writeData(users);
      }
    }
  }
})

app.post('/updateProfile',function(req,res){
  //this will read the body
  var userinfo = req.body
  //this will set a response of the text type
  res.set('Content-Type','text/plain')
  for(var key in users){
    if (userinfo.username === users[key].username){
      if(users[key].signedin === true){
        let temp = users[key];
        users[key].lastname =userinfo.lastname;
        res.send();
        writeData(users);
      }
    }
  }
})

app.post('/deleteAccount',function(req,res){
  var user = req.body
  res.set('Content-Type','text/plain')
  for(var key in users){
    if (user === users[key].username){
      if(users[key].signedin === true){
        delete users[key];
        res.send("/AccountSettings.html");
        writeData(users);
      }
    }
  }
})

app.post('/changepfp',function(req,res){
  var user = req.body
  res.set('Content-Type','text/plain')
  for(var key in users){
    if (user.username === users[key].username){
      if(users[key].signedin === true){
        temp = users[key];
        temp.profilepic = user.pfp;
        console.log(temp.pfp);
        users[temp.username] = temp;
         writeData(users);
         res.send();
      }
    }
  }
})

//NEEEEEEEEEEED TO ADDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
app.post('/addFollower?',function(req,res){
  var user = req.body
  res.set('Content-Type','application/json')
  for(var key in users){
    if (user.username === users[key].username){
      if(users[key].signedin === true){
        temp = users[key];
        temp[user.friend] = user.friend;
        users[temp.username] = temp;
        writeData(users);
        res.send();
        }
      }
    }
})

app.post("/listUsers", function(req, res) {
    var searcheduser = req.body
    res.set("Content-type","text/plain");
    for(var key in users){
      if(searcheduser === users[key].username){
        res.send("/ViewingPeople.html")
      }
    }
  });

app.post("/queryusers", function(req, res) {
    var searcheduser = req.body
    res.set("Content-type","application/json");
    let user = []
    for(var key in users){
      user.push(users[key].username);
    }
    let a = JSON.stringify(user);
    let parsed = JSON.parse(a);
    res.send(parsed)
  });

  app.post("/userlookup?", function(req, res) {
      var searcheduser = req.body
      console.log(searcheduser)
      res.set("Content-type","application/json");
      for(var key in users){
        if(users[key].username === searcheduser.username){
          let a = users[key];
          console.log(a);
          res.send(a)
        }
      }
    });

app.post("/rate?", function(req, res) {
    var ratingObject = req.body
    var found = false;
    console.log(ratingObject);
    res.set("Content-type","application/json");
    for(var key in movieRatings){
      console.log(key)
      if(movieRatings[key].title === ratingObject.title)
      {
        temp = movieRatings[key];
        console.log(temp);
        temp[ratingObject.user]=ratingObject.rating;
        temp["total"] = +movieRatings[key].total +1;
        temp["rating"] = +movieRatings[key].rating + +ratingObject.rating;
        console.log(temp);
        movieRatings[key] = temp;
        found = true;
      }
    }
      if(found ===false){
          let temp = new createMovieForRating(ratingObject.title,ratingObject.rating,1);
          movieRatings[ratingObject.title] = temp;
      }
    writeRatingData(movieRatings);
    res.send();
  });

app.post("/sendRatingReq?", function(req, res) {
  var rating = req.body
  console.log(rating);
  res.set("Content-Type","application/json");
  for(var key in movieRatings){
    if(movieRatings[key].title === rating.Title)
    {
      let temp = (movieRatings[key].rating / movieRatings[key].total);
      temp = JSON.stringify(temp);
      res.send(temp);
    }
  }
});

var server = app.listen(8080, function() {
  var host = server.address().address;
  var port = server.address().port;

  console.log("Movie Database listening at http://%s:%s", host, port);
});
