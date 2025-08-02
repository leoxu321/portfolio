//let us = require("./Users.json"); this is to be done for the server implementation
let currentuser = null;
let users = [];
let filmlist = [];

//Constructor to make a film object
function User(firstname,lastname,username,password, film){
  this.firstname= firstname;
  this.lastname= lastname;
  this.username= username;
  this.password= password;
  this.favouritefilm = film;
  this.friends = [];
  this.signedin = false;
}
//constructor to make film objects
function film(title,year,director, poster, rating, genre){
  this.title= title;
  this.date= year;
  this.director= director;
  this.image = poster;
  this.rating = rating;
  this.genre = genre;
}


//Function creates a film object
function createFilm(title,date,director,poster,rating, genre){
  let newFilm = new film(title,date,director,poster,rating,genre);
  return newFilm;
}

//Prints out information of the film
function printFilm(film){
  console.log("title:",film.title,", Release Date:",film.date);
  console.log("Directed by:",film.director);
  console.log("Rating:",film.rating);
  console.log("Genre:",film.genre);
  console.log("Poster:",film.image);
  console.log("");
}

//Function to print out films
function listFilms(){
  console.log("List of Films:");
  for(let i=0; i< filmlist.length; i++){
    printFilm(filmlist[i]);
  }
}

//function to addfilm
function addFilm(film){
  filmlist.push(film);
}

//function to removefilm
function removeFilm(film){
  let temp =[];
  for(let i=0; i < filmlist.length; i++){
     if(filmlist[i].title !== film.title){
       temp.push(filmlist[i]);
       console.log("Film removed succesfully");
     }
  }
  filmlist = temp;
}

//Function to edit film elements
function editFilm(film,title,year,director,poster,rating, genre){
  film=film;
  film.title = title;
  film.date = year;
  film.director = director;
  film.poster = poster;
  film.rating = rating;
  film.genre = genre;
  printFilm(film);
}

//Searches for film objects
function findFilm(filmname){
  let found = 0;
  for(let i=0; i< filmlist.length; i++){
    if(filmlist[i].title===filmname){
       printFilm(filmlist[i]);
       found +=1;
    }
  }
  if(found === 0){
    console.log("Sorry we do not have that film in our database");
  }
}

//Creates a user object
function createUser(firstname,lastname,username,password,film){
  let freshUser = new User(firstname,lastname,username,password,film);
  return freshUser;
}

function addUser(user){
  users.push(user);
}

//Function deletes users account
function deleteAccount(user){
  let temp =[];
  for(let i=0; i < users.length; i++){
     if(users[i].username !== user.username){
       temp.push(users[i]);
     }
  }
  users= temp;
  console.log("Youre Account was succesfuly deleted!");
}

//Function toggles if user is signed in or not
function toggleSignin(user){
  if(user.signedin === true){
    user.signedin = false;
  }else{
    user.signedin = true;
  }
}

//Function logs the user in
function login(username, password){
  let found=0;
  if(currentuser === null){
    for(let i=0; i < users.length; i++){
      if(users[i].username == username){
        if(users[i].password === password){
          console.log("You have succesfuly signed in");
          toggleSignin(users[i]);
          found +=1;
        }
      }
    }
    if (found === 0){
      console.log("username or password is not correct");
    }
  }else{
    console.log("You are already logged in");
  }
}

//Function prints users account info
function accountInfo(user){
  console.log("Name:",user.firstname,user.lastname);
  console.log("Username:",user.username);
  console.log("Password: ***********");
  console.log("Friends:", user.friends);
  console.log("Online:", user.signedin);
  console.log("Favourite Film:",user.favouritefilm.title);
}

//Function searches for users
function searchUsers(searchinput){
    let found = 0;
    for(let i=0; i<users.length; i++){
      if(users[i].username === searchinput){
        console.log("Found User:----------------");
        accountInfo(users[i]);
        found+=1;
      }
    }
    if(found ===0){
      console.log("That user does not exist!");
    }
}

//Function adds friends to user
function addFriend(user,userfriend){
      user.friends.push(userfriend.username);
      console.log("Friends:",user.friends);
}

//Function lists users friends
function listFriends(user){
    console.log("Friends: ");
  if(user.friends.length===0){
    console.log("You dont have any friends: T T");
  }else{
    for(let j=0; j<user.friends.length; j++){
      console.log(user.friends[i]);
    }
  }
}

//Function removes a friend from users friend list
function removeFriend(user,userfriend){
      let temp =[];
      for(let i=0; i < user.friends.length; i++){
         if(user.friends[i] !== userfriend.username){
           temp.push(user.friends[i]);
         }
      }
      user.friends = temp;
      console.log("Friends:",user.friends);
}


//Creates a films
let filmA = createFilm("Toy Story","22 Nov 1995","John Lasseter","https://m.media-amazon.com/images/M/MV5BMDU2ZWJlMjktMTRhMy00ZTA5LWEzNDgtYmNmZTEwZTViZWJkXkEyXkFqcGdeQXVyNDQ2OTk4MzI@._V1_SX300.jpg","G","ANIMATION");
let filmB = createFilm("The Lord of the Rings: The Fellowship of the Ring", "December 19, 2001","Peter Jackson","https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcT9J7XACn3tlD6v4UXRMvT2wJN8FGCCPeh8U3RkZ6__tR4wGhSo","R","ACTION/ADVENTURE");
//Adds films
addFilm(filmA);
addFilm(filmB);
console.log("-----------------------------------");
//prints out a list of the films
listFilms();
console.log("-----------------------------------");
//Removes a film and searches to see if the film was removed
removeFilm(filmA);
findFilm("Toy Story");
console.log("-----------------------------------");
console.log("Find a film that does exist");
findFilm("The Lord of the Rings: The Fellowship of the Ring");
console.log("-----------------------------------");
listFilms();
console.log("-----------------------------------");
//prints out the info of filmA
printFilm(filmA);
console.log("-----------------------------------");
//Edits the details of film a
console.log("editedFilm")
editFilm(filmB,filmB.title,filmB.date,filmB.director, filmB.poster,"PG", filmB.genre);
console.log("-----------------------------------");
//Tests to see if the users account has been made
let userA = createUser("leeroy","Jenkins","Ruined","password",filmA);
let userB = createUser("Bilbo","Baggins","TheOneRing","theshire",filmB);
addUser(userA);
addUser(userB);
//Test user if they try to sign in to account
console.log("Is user A signed in: ",userA.signedin);
login(userA.username, userA.password);
console.log("Is userA signed in now: ", userA.signedin);
console.log("-----------------------------------");
//Test user if they fail to sign in to account
login(userA.username,"memes");
console.log("-----------------------------------");

//Print out users account info;
accountInfo(userA);
console.log("-----------------------------------");

//Search for a user that does exist
searchUsers("TheOneRing");
console.log("-----------------------------------");
//Search for a user that does not exist
searchUsers("TotallyaUsername");
console.log("-----------------------------------");
//Add a friend to a user
addFriend(userA,userB);
console.log("-----------------------------------");
//Remove a friend : (
removeFriend(userA, userB);
console.log("-----------------------------------");
//Delete an account
deleteAccount(userA);
console.log("-----------------------------------");
//List all the friends a user has
listFriends(userA);
