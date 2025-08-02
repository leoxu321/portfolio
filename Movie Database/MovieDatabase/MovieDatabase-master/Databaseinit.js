
var mongoose = require('mongoose');

// make a connection
mongoose.connect('mongodb://localhost:27017/MovieDatabase');

// get reference to database
var db = mongoose.connection;

db.on('error', console.error.bind(console, 'connection error:'));

db.once('open', function() {
   console.log("Connection Successful!");

   // define Schema
   var userSchema = mongoose.Schema({
    firstname: String,
    lastname: String,
   	username: String,
   	password: String,
   	signedin: Boolean,
    contributing: Boolean,
    profilepic: String,
   	Friends: [String]
   });

   // compile schema to model
   var User = mongoose.model('Users', userSchema, 'userlist');

   // a document instance
   var book1 = new User({"firstname":"ADMIN","lastname":"Account","username":"ADMIN","password":"send","signedin":false,"contributing":false,"profilepic":"https://i.kym-cdn.com/entries/icons/original/000/021/807/ig9OoyenpxqdCQyABmOQBZDI0duHk2QZZmWg2Hxd4ro.jpg"});

   // save model to database
   book1.save(function (err, User) {
     if (err) return console.error(err);
     console.log(User.username + " saved to bookstore collection.");
   });
});
