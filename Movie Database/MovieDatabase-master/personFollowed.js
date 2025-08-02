function lookCycle(user,pages,num,look){
  this.username=user;
  this.pages=pages;
  this.action=num;
  this.look = look;
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
function castMovie(){
    let xHttp = new XMLHttpRequest();
    xHttp.open("POST","/findCast",true);
    xHttp.setRequestHeader("Content-type","text/plain");
    xHttp.onreadystatechange=(e)=>{
      var b = xHttp.responseText;
      url = 'http://localhost:3000/CastView.html?search='+encodeURIComponent(b);
      document.location.href = url;
    }
    let cast = document.getElementById('search2').value;
    let data = cast;
    xHttp.send(data);
}
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
