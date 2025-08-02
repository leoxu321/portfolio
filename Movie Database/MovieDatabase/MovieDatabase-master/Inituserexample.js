// this line allows us to use the expressjs module
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

//Schema and model for using the database
const UserSchema = mongoose.model(
  'userlist',
  new mongoose.Schema({
    firstname: String,
    lastname: String,
    username: String,
    password: String,
    signedin: Boolean,
    contributing: Boolean,
    profilepic: String,
    Friends: [String]
   })
);

app.get('/', async (_, res, next) => {
  try {
    const users = await UserSchema
      .find({username:"ADMIN"})
    console.log(users[0]);
  } catch (err) { return next(err); }
});


var server = app.listen(8080, function() {
  var host = server.address().address;
  var port = server.address().port;
  console.log("Movie Database listening at http://%s:%s", host, port);
});
