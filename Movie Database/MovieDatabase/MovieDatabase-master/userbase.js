//localStorage.clear();

Storage.prototype.setObj = function(key, obj) {
    return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function(key) {
    return JSON.parse(this.getItem(key))
}

Storage.prototype.removeObj= function(key){
  return this.removeItem(key);
}

function updateUsername(){
    name =  document.getElementById("textbox").value;
    document.getElementById("Name").innerHTML="Username: "+name;
}

function User(firstname,lastname,username,password,profilepic){
  this.firstname= firstname;
  this.lastname= lastname;
  this.username= username;
  this.password= password;
  this.signedin = false;
  this.contributing = false;
  this.profilepic = profilepic;
}

function Person(firstname,lastname){
  this.firstname= firstname;
  this.lastname= lastname;
}

function updatefirstname(username,firstname){
  this.username = username;
  this.firstname = firstname;
}

function updatelastname(username,lastname){
  this.username = username;
  this.lastname = lastname;
}

function authinfo(username,password){
  this.password = password;
  this.username = username;
}

function updatePfp(username,pfp){
  this.username = username;
  this.pfp = pfp;
}

function addFriendName(username,friend){
  this.username = username;
  this.friend = friend;
}

function MakesUser(user){
  this.username= user;
}

function cycle(user,num){
  this.username=user;
  this.action=num;
}

function lookCycle(user,pages,num,look){
  this.username=user;
  this.pages=pages;
  this.action=num;
  this.look = look;
}

function ratingObj(username,rating){
  this.username = username;
  this.rating = rating;
}

function createUser(){
    if(document.getElementById("username").value!="" && document.getElementById("username").value !=""){
      newUser = new User(document.getElementById("firstname").value,document.getElementById("lastname").value,document.getElementById("username").value,document.getElementById("password").value,"https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      sendUser(newUser);
    }else{
      alert("Please fill in the username and password fields!");
    }
}

function login(){
  var xHttp= new XMLHttpRequest();
  xHttp.open("POST","/authentication",true);
  xHttp.setRequestHeader("Content-type","application/json")
  xHttp.onreadystatechange=(e)=>{
    console.log("sent post");
    if(xHttp.responseText != "Invalid Username or Password" && xHttp.responseText !== ""){
      //stores the usersname in cookies
      localStorage.setItem("currentuser",document.getElementById("username").value);
      window.location.replace(xHttp.responseText);
    }else{
      if(xHttp.responseText !== ""){
          document.getElementById("notif").innerHTML=xHttp.responseText;
      }
    }
  }
  username=document.getElementById("username").value;
  password=document.getElementById("password").value;
  let auth = new authinfo(username,password);
  auth = JSON.stringify(auth)
  xHttp.send(auth);
}


function sendUser(newUser){
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/newuser",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
    if(xHttp.responseText === "LogInPage.html"){
        window.location.replace(xHttp.responseText);
    }
  }
  var data = JSON.stringify(newUser);
  xHttp.send(data);
}

//Checks if the user should have the ability to sign in or out
function toggleLoginInfo(){
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/signedin?",true);
  xHttp.setRequestHeader("Content-type","text/plain");
  xHttp.onreadystatechange=(e)=>{
    if (xHttp.responseText === "true"){
          document.getElementById("Sign").innerHTML = "Log Out";
          document.getElementById("taccounts").style.visibilty="visible";
    }else{
      document.getElementById("Sign").innerHTML = "Log In";
    }
  }
  var data = localStorage.getItem("currentuser");
  xHttp.send(data);
}

function toggleSignin(){
  if (localStorage.getItem("currentuser")){
    var xHttp = new XMLHttpRequest();
    xHttp.open("POST","/signout",true);
    xHttp.setRequestHeader("Content-type","text/plain");
    xHttp.onreadystatechange=(e)=>{
        document.getElementById("Sign").innerHTML = "Log In";
        localStorage.removeItem("currentuser");
    }
    var data = localStorage.getItem("currentuser");
    xHttp.send(data);
  }else{
    window.location.replace("LoginPage.html");
  }
}

function toggleContributing(action){
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/toggleContribute",true);
  xHttp.setRequestHeader("Content-type","text/plain");
  xHttp.onreadystatechange=(e)=>{
      let res = xHttp.responseText;
      var x =document.getElementById("persontextbox").style.display;
      if(res === "true"){
        if(action!="load"){
          document.getElementById("contributing").style.visibility= "visible";
          document.getElementById("contributing2").style.visibility= "visible";
          document.getElementById("persontextbox").style.display="block";
          document.getElementById("contribuser").style.display="block";
          document.getElementById("contributing3").style.visibility= "visible";
        }else{
          document.getElementById("contributing1").style.visibility= "visible";
        }
      }
  }
  var data = localStorage.getItem("currentuser");
  xHttp.send(data);
}

function accountInfo(){
  let name ="Name: "
  let username =  "Username: "
  let friend = "Friend: "
  temp = localStorage.getItem("currentuser");
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/showInfo?",true);
  xHttp.setRequestHeader("Content-type","text/plain");
  xHttp.onreadystatechange=(e)=>{
      let info = JSON.parse(xHttp.responseText);
      document.getElementById("name").innerHTML= name+info.firstname+" "+info.lastname;
      document.getElementById("username").innerHTML= username+info.username;
      document.getElementById("userimg").src= info.profilepic;
      document.getElementById("password").innerHTML = "Password: **************";
      document.getElementById("accountmade").innerHTML = "Account Made: 11/12/2020";
    }
  document.getElementById("userimg").src= "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";

  var data = localStorage.getItem("currentuser");
  xHttp.send(data);
}


function updateFirstname(){
  temp = localStorage.getItem("currentuser");
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/updateProfile",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      document.getElementById("name").innerHTML= name+xHttp.responseText;
      accountInfo();
    }
  data = new updatefirstname(temp,document.getElementById("textbox").value);
  data = JSON.stringify(data);
  xHttp.send(data);

}


function updateLastname(){
  temp = localStorage.getItem("currentuser");
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/updateProfile",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      document.getElementById("name").innerHTML= name+xHttp.responseText;
      accountInfo();
    }
  data = new updatelastname(temp,document.getElementById("textbox").value);
  data = JSON.stringify(data);
  xHttp.send(data);
}

function changePfp(){
  temp = localStorage.getItem("currentuser");
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/updateProfile",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      accountInfo();
    }
  data = new updatePfp(temp,document.getElementById("textbox").value);
  data = JSON.stringify(data);
  xHttp.send(data);
}

function deleteAccount(){
  data = localStorage.getItem("currentuser");
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/deleteAccount",true);
  xHttp.setRequestHeader("Content-type","text/plain");
  xHttp.onreadystatechange=(e)=>{
      window.location.replace(xHttp.responseText);
    }
  xHttp.send(data);
  localStorage.removeItem("currentuser");
}

function searchUsers(){
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/listusers",true);
  xHttp.setRequestHeader("Content-type","text/plain");
  xHttp.onreadystatechange=(e)=>{
      var b = document.getElementById('search').value
      url = 'http://localhost:3000/ViewingPeople.html?search='+encodeURIComponent(b);
      document.location.href = url;
    }
  data = document.getElementById("search").value;
  xHttp.send(data);
}
function goTosearched(b){
    url = 'http://localhost:3000/ViewingPeople.html?search='+encodeURIComponent(b);
    document.location.href = url;
}

function addFollower(user){
  data = localStorage.getItem("currentuser");
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/addFollower?",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      console.log(xHttp.responseText);
      location.reload();
    }
  let temp = new addFriendName(localStorage.getItem("currentuser"),user);
  console.log(temp);
  data = JSON.stringify(temp);
  xHttp.send(data);
}

function showUsers(num){
      user = localStorage.getItem("currentuser");
      let xHttp = new XMLHttpRequest();
      xHttp.open('POST','/queryusers',true);
      xHttp.setRequestHeader('Content-Type','application/json');
      xHttp.onreadystatechange=(e)=>{
        let x = JSON.parse(xHttp.responseText);
        console.log(x);
          for(let i = 0; i < x.length-1; i++){
            if (x[i]!=null){
              document.getElementById("user"+i).innerHTML = x[i].username;
              document.getElementById("img"+i).src = x[i].profilepic;
            }
          }
          if (x[x.length-1]!="1"){
            location.reload();
          }
      }
      let temp= new cycle(user,num);
      data = JSON.stringify(temp);
      xHttp.send(data)
    }

function showUserNotifs(num){
      user = localStorage.getItem("currentuser");
      let xHttp = new XMLHttpRequest();
      xHttp.open('POST','/queryUserNotifs',true);
      xHttp.setRequestHeader('Content-Type','application/json');
      xHttp.onreadystatechange=(e)=>{
        let x = JSON.parse(xHttp.responseText);
          console.log(x);
          if (x[x.length-2]!="1"){
            sessionStorage.setItem("Page",x[x.length-1]);
            location.reload();
          }
          for(let i = 0; i < x.length-2; i++){
            if (x[i]!=null){
              if(x[i].type==="Review"){
                document.getElementById("title"+i).innerHTML=x[i].type+" of "+x[i].title+" by the user "+x[i].username+":";
                document.getElementById("user"+i).innerHTML ="\""+x[i].review+"\"";
              }else{
                document.getElementById("title"+i).innerHTML=x[i].type+", "+x[i].person+":";
                document.getElementById("user"+i).innerHTML ="Added to the film: "+x[i].name;
              }
            }
          }
          console.log(x);
          console.log(x[x.length-2]);
      }
      pages = sessionStorage.getItem("Page");
      let temp= new lookCycle(user,pages,num,null);
      data = JSON.stringify(temp);
      xHttp.send(data)
    }


  function showUserReview(num){
    user = sessionStorage.getItem("look2");
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/queryUserRev',true);
    xHttp.setRequestHeader('Content-Type','application/json');
    xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
        console.log(x);
        if (x[x.length-2]!="1"){
          sessionStorage.setItem("Page",x[x.length-1]);
          location.reload();
        }
        for(let i = 0; i < x.length-2; i++){
          if (x[i]!=null){
            document.getElementById("user"+i).innerHTML = x[i].title+":<br>"+x[i].review;
          }
        }
        console.log(x);
        console.log(x[x.length-2]);
    }
    pages = sessionStorage.getItem("Page");
    let temp= new lookCycle(user,pages,num,null);
    data = JSON.stringify(temp);
    xHttp.send(data)
  }

  function senduserinfo(){
    let friend = "";
    let pass = false;;
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/userlookup?',true);
    xHttp.setRequestHeader('Content-Type','application/json');
    xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
        for (key in x){
              console.log(x[key]);
          if (key ==="Followers"){
             friend="";
             pass = true;
             for (let i=0; i <x[key].length;i++){
               friend+= x[key][i].username+", ";
             }

          }else if(pass === false){
            friend = "This user has no friends";
          }
        }
        document.getElementById("mName").innerHTML = "<font size='6'>"+" "+x.firstname+" "+x.lastname;
        document.getElementById("Image").src = x.profilepic;
        document.getElementById("History").innerHTML= "<font size='6'>"+"Username: "+x.username+"</br>"+x.firstname+" "+x.lastname+" has not been in any films."+"</br>"+"Following the Users: "+friend;
        document.getElementById("Collab").innerHTML="This User has contributed to: "+x.Contributions;
    }

    let user = user2;
    let data = new MakesUser(user);
    //console.log(data2);
    data = JSON.stringify(data)
    xHttp.send(data);
  }

  function toggleFollowInfo(user){
    var xHttp = new XMLHttpRequest();
    xHttp.open("POST","/following?",true);
    xHttp.setRequestHeader("Content-type","application/json");
    xHttp.onreadystatechange=(e)=>{
      if (xHttp.responseText === "true"){
            document.getElementById("followbutton").innerHTML = "Follow";
      }else{
        document.getElementById("followbutton").innerHTML = "UnFollow";
      }
    }
    let temp = new addFriendName(localStorage.getItem("currentuser"),user);
    data = JSON.stringify(temp);
    xHttp.send(data);
  }

  function toggleLoginInfo(){
    var xHttp = new XMLHttpRequest();
    xHttp.open("POST","/signedin?",true);
    xHttp.setRequestHeader("Content-type","text/plain");
    xHttp.onreadystatechange=(e)=>{
      if (xHttp.responseText === "true"){
            document.getElementById("Sign").innerHTML = "Log Out";
      }else{
        document.getElementById("Sign").innerHTML = "Log In";
      }
    }
    var data = localStorage.getItem("currentuser");
    xHttp.send(data);
  }

  function authAccount(){
    var xHttp = new XMLHttpRequest();
    xHttp.open("POST","/showaccountsettings?",true);
    xHttp.setRequestHeader("Content-type","text/plain");
    xHttp.onreadystatechange=(e)=>{
      window.location.replace(xHttp.responseText);
    }
    var data = localStorage.getItem("currentuser");
    xHttp.send(data);
  }

  function showFollowing(num){
    user = localStorage.getItem("currentuser");
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/queryFollowers',true);
    xHttp.setRequestHeader('Content-Type','application/json');
    xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
        if (x[x.length-2]!="1"){
          sessionStorage.setItem("Page",x[x.length-1]);
          location.reload();
        }
        for(let i = 0; i < x.length-2; i++){
          if (x[i]!=null){
            document.getElementById("user"+i).innerHTML = x[i].username;
            document.getElementById("img"+i).src = x[i].profilepic;
          }
        }
        console.log(x);
        console.log(x[x.length-2]);
    }
    pages = sessionStorage.getItem("Page");
    let temp= new lookCycle(user,pages,num,null);
    data = JSON.stringify(temp);
    xHttp.send(data)
  }

  function showWhoFollowing(num){
    user = localStorage.getItem("currentuser");
    looking = sessionStorage.getItem("look");
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/queryWhoFollows',true);
    xHttp.setRequestHeader('Content-Type','application/json');
    xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
        if (x[x.length-2]!="1"){
          sessionStorage.setItem("Page",x[x.length-1]);
          location.reload();
        }
        for(let i = 0; i < x.length-2; i++){
          if (x[i]!=null){
            document.getElementById("user"+i).innerHTML = x[i].username;
            document.getElementById("img"+i).src = x[i].profilepic;
          }
        }
    }
    pages = sessionStorage.getItem("Page");
    let temp= new lookCycle(user,pages,num,looking);
    data = JSON.stringify(temp);
    xHttp.send(data)
  }
  function showPeople(num){
    user = localStorage.getItem("currentuser");
    looking = sessionStorage.getItem("look");
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/queryPeopleFollow',true);
    xHttp.setRequestHeader('Content-Type','application/json');
    xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
       console.log(x);
        if (x[x.length-2]!="1"){
          sessionStorage.setItem("Page",x[x.length-1]);
          location.reload();
        }
        for(let i = 0; i < x.length-2; i++){
          if (x[i]!=null){
            document.getElementById("user"+i).innerHTML = x[i];
          }
        }
    }
    pages = sessionStorage.getItem("Page");
    let temp= new lookCycle(user,pages,num,looking);
    data = JSON.stringify(temp);
    xHttp.send(data)
  }
  function showPersonFollows(viewthem){
    sessionStorage.setItem("look",viewthem);
    sessionStorage.setItem("Page","0");
    window.location.replace("/peopleFollowed");
  }

  function showPersonUser(){
    let viewthem= localStorage.getItem("currentuser");
    sessionStorage.setItem("look",viewthem);
    sessionStorage.setItem("Page","0");
    window.location.replace("/peopleFollowed");
  }

  function showWhoFollows(viewthem){
    sessionStorage.setItem("look",viewthem);
    sessionStorage.setItem("Page","0");
    window.location.replace("searchFollowing");
  }

  function showWhoFollow(){
    sessionStorage.setItem("Page","0");
    window.location.replace("Following");
  }

  function seeUserReviews(user){
    sessionStorage.setItem("look2",user);
    sessionStorage.setItem("Page","0");
    window.location.replace("listUsersReview");
  }

  function goToNotifs(){
    sessionStorage.setItem("Page","0");
    window.location.replace("notifications");
  }

  function addPerson(){
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/addPerson',true);
    xHttp.setRequestHeader('Content-Type','text/plain');
    xHttp.onreadystatechange=(e)=>{
      document.getElementById("response").innerHTML=xHttp.responseText;
    }
    let data= document.getElementById("persontextbox").value;
    xHttp.send(data)
  }

  function frequency(){
    var url = document.location.href,
    params = url.split('?')[1].split('&'),
    data = {}, tmp;
    for (var i = 0, l = params.length; i < l; i++) {
        tmp = params[i].split('=');
        data[tmp[0]] = tmp[1];
    }
    let person = data.search
    let person2 = person.split("%20");
    person2 = person2.join(' ')
    person2 = person2.replace('%3A', ":")
    person2 = person2.replace('%26', "&")
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/findFrequent',true);
    xHttp.setRequestHeader('Content-Type','text/plain');
    xHttp.onreadystatechange=(e)=>{
      let data = JSON.parse(xHttp.responseText);
      var string="";
      for(let i=0; i<data.length;i++){
        if(i!=data.length-1){
          string+=data[i].name+", ";
        }else{
          string+=data[i].name;
        }
      }
      document.getElementById("colleagues").innerHTML =string;
    }
    xHttp.send(person2);
  }

  function toggleUserContributer(){
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/toggleContributing',true);
    xHttp.setRequestHeader('Content-Type','text/plain');
    xHttp.onreadystatechange=(e)=>{
      alert(xHttp.responseText);
    }
    let data= document.getElementById("contribuser").value;
    xHttp.send(data)
  }
  //this is for making the database dont use it
  function initPeoples(){
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/initpeoples',true);
    xHttp.setRequestHeader('Content-Type','text/plain');
    xHttp.onreadystatechange=(e)=>{
      window.location.replace(xHttp.responseText);
    }
    xHttp.send()
  }

  function toggleContrib(){
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/toggleContributing',true);
    xHttp.setRequestHeader('Content-Type','text/plain');
    xHttp.onreadystatechange=(e)=>{
      location.reload();
    }
    let data= localStorage.getItem("currentuser");
    xHttp.send(data);
  }

  function toggleEditMovie(){
    let xHttp = new XMLHttpRequest();
    xHttp.open('POST','/contributing?',true);
    xHttp.setRequestHeader('Content-Type','text/plain');
    xHttp.onreadystatechange=(e)=>{
      document.getElementById("contributing").style.visibility=xHttp.responseText;
    }
    let data= localStorage.getItem("currentuser");
    xHttp.send(data);
  }
