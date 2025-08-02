var express = require("express");
var path = require("path");
var bodyParser = require('body-parser');
var app = express();
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

const MovieSchema = mongoose.model(
    'testmovies',
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

app.get("/CastView.html", function(req,res){
    res.sendFile(path.join(__dirname + "/CastView.html"));
    console.log("CastView.html");
  });

app.get("/MovieCreate", function(req,res){
      res.sendFile(path.join(__dirname + "/MovieCreate.html"));
      console.log("/MovieCreate.html");
  });

app.get('/userbase.js', function(req, res){
    res.sendFile(__dirname + '/userbase.js');
});

app.get('/movie.js', function(req,res){
    res.sendFile(__dirname + '/movie.js');
});

app.get('/', async (_, res, next) => {
    try {
      const users = await UserSchema
        .find({username:"ADMIN"})
      console.log(users[0]);
    } catch (err) { return next(err); }
  });

app.get("/movies",async (req,res,next)=>{
    try{
        let movie = req.query.Title;
        const newMovie = await MovieSchema.exists({Title:movie})
        const newGen = await MovieSchema.exists({Genre:movie})
        const newYear = await MovieSchema.exists({Year:movie})

        if(newMovie === true){
            const movie2= await UserSchema.find({Title:movie})
            res.send(movie2)
        }
        if(newGen == true){
            const gen2= await UserSchema.find({Genre:{$regex : ".*"+movie+".*"}});
            res.send(gen2);
        }
        if(newYear == true){
            const year2= await UserSchema.find({Year:movie});
            res.send(year2);
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
        let movie = req.query.Title;
        const newMovie = await MovieSchema.exists({Title:movie})

        if(newMovie === true){
            const movie2= await UserSchema.find({Title:movie})
            res.send(movie2)
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

app.post("/movies",async (req,res,next)=>{
    console.log("Posted");
    res.set('Content-Type','application/json');
    var data = req.body
    console.log(data);
    try{
        const movie = await MovieSchema.exists({Title:data.Title});
        if(movie === false){
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
        else{
            console.log("This Movie Already Exists");
        }
    }
    catch(err){
        {
            return next(err);
        }
    }
})

app.post("/editFilm",async (req,res,next)=>{
    console.log("Posted");
    res.set('Content-Type','application/json');
    var data = req.body
    console.log(data);
    try{
        const movie = await MovieSchema.exists({Title:data.Title});
        if(movie === true){
            let field = data.Type.toUpperCase();
            console.log(field);
            if(field === "TITLE"){
                await MovieSchema.updateOne({Title:data.Title},{$set: {Title:data.Change}});
            }else if(field ==="YEAR"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Year:data.Change}});
            }
            else if(field ==="RATED"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Rated:data.Change}});
            }
            else if(field ==="RUNTIME"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Runtime:data.Change}});
            }
            else if(field ==="GENRE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Genre:data.Change}});
            }
            else if(field ==="DIRECTOR"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Director:data.Change}});
            }
            else if(field ==="WRITER"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Writer:data.Change}});
            }
            else if(field ==="ACTORS"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Actors:data.Change}});
            }
            else if(field ==="PLOT"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Plot:data.Change}});
            }
            else if(field ==="LANGUAGE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Language:data.Change}});
            }
            else if(field ==="COUNTRY"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Country:data.Change}});
            }
            else if(field ==="AWARDS"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Awards:data.Change}});
            }
            else if(field ==="POSTER"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Poster:data.Change}});
            }
            else if(field ==="RATINGS"){
              await MovieSchema.updateOne({Title:data.Title},{$pull:{Ratings:{Source:data.Change.Source}}});
              await MovieSchema.updateOne({Title:data.Title},{$push: {Ratings:data.Change}});
            }
            else if(field ==="METASCORE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Metascore:data.Change}});
            }
            else if(field ==="IMDBRATING"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {imdbRating:data.Change}});
            }
            else if(field ==="IMDBVOTES"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {imdbVotes:data.Change}});
            }
            else if(field ==="IMDBID"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {imdbID:data.Change}});
            }
            else if(field ==="TYPE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Type:data.Change}});
            }
            else if(field ==="DVD"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {dvd:data.Change}});
            }
            else if(field ==="BOXOFFICE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {BoxOffice:data.Change}});
            }
            else if(field ==="PRODUCTION"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Production:data.Change}});
            }
            else if(field ==="WEBSITE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Website:data.Change}});
            }
            else if(field ==="RESPONSE"){
              await MovieSchema.updateOne({Title:data.Title},{$set: {Response:data.Change}});
            }
        }
    }
    catch(err){
        {
            return next(err);
        }
    }
})

  app.post('/Genre', async (req, res, next) => {
    console.log("Posted");
    res.set('Content-Type','application/json');
    var data = req.body
    try{
        const genre = await MovieSchema.find({Genre:{$regex : ".*"+data.Title+".*"}});
        let g = []
        for(let i = 0; i < genre.length; i++){
            g.push(genre[i].Title);
        }
        let a = JSON.stringify(g);
        let parsed = JSON.parse(a);
        res.send(parsed)
    }
    catch(err){
        return next(err);
    }
  })

  app.post('/Year', async (req, res, next) => {
    console.log("Posted");
    res.set('Content-Type','application/json');
    var data = req.body
    try{
        const year = await MovieSchema.find({Year:data.Title});
        let g = []
        for(let i = 0; i < year.length; i++){
            g.push(year[i].Title);
        }
        let a = JSON.stringify(g);
        let parsed = JSON.parse(a);
        res.send(parsed)
    }
    catch(err){
        return next(err);
    }
  })


app.post('/findFilm',async (req, res, next) => {
    console.log("Posted");
    res.set('Content-Type','text/plain');
    var movie = req.body
    try{
        const movies = await MovieSchema.find({Title:movie});
        //console.log(movies);
        //console.log(movie);
        if(movies[0].Title === movie){
            res.send("/MovieInfo.html");
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
        const movies = await MovieSchema.find({Title:data.Title});
        //console.log(movies[0]);
        //console.log(data);
        if(movies[0].Title === data.Title){
            res.send(movies[0]);
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
        //console.log(movies[1].Title);
        for(let i = 0; i < movies.length; i++){
            if(movies[i].Title === "#DUPE#"){
                continue;
            }
            else{
                m.push(movies[i]);
            }
        }
        //console.log(m)
        let a = JSON.stringify(m);
        let parsed = JSON.parse(a);
        res.send(parsed);
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
        const actor = await MovieSchema.exists({Actors:{$regex : ".*"+data+".*"}});
        if(actor === true){
            res.send("Actor");
        }
        else{
            const director = await MovieSchema.exists({Director:{$regex : ".*"+data+".*"}});
            if(director === true){
                res.send("Director");
            }
            else{
                const writer = await MovieSchema.exists({Writer:{$regex : ".*"+data+".*"}});
                if(writer === true){
                    res.send("Writer");
                }
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
    console.log(data)
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
        console.log(Obj);
        res.send(Obj);
    }
    catch(err){
        return next(err);
    }
})

var server = app.listen(8080, function() {
    var host = server.address().address;
    var port = server.address().port;
    console.log("Movie Database listening at http://%s:%s", host, port);
  });
