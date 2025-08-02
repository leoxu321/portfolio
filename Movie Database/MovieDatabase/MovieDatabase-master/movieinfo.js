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
function sendMovie(){
  var url = document.location.href,
  params = url.split('?')[1].split('&'),
  data = {}, tmp;
  for (var i = 0, l = params.length; i < l; i++) {
      tmp = params[i].split('=');
      data[tmp[0]] = tmp[1];
  }
  let movie = data.search
  //console.log(movie);
  let movie2 = movie.split("%20");
  movie2 = movie2.join(' ')
  movie2 = movie2.replace('%3A', ":")
  movie2 = movie2.replace('%26', "&")
  movie2 = movie2.replace('%27',"'");
  let xHttp = new XMLHttpRequest();
  xHttp.open('POST','/theMovie',true);
  xHttp.setRequestHeader('Content-Type','application/json');
  xHttp.onreadystatechange=(e)=>{
    let x = JSON.parse(xHttp.responseText);
      document.getElementById("movieTitle").innerHTML = x.Title;
      document.getElementById("imgfilm").src =x.Poster;
      let y = ""
      y = "Actors: "+ x.Actors+"<br />"+" Director: "+ x.Director+"<br /> Writers: "+ x.Writer
      document.getElementById("mainCast").innerHTML =y;
      let z = "";
      //console.log(x.Ratings[0].Source)
      for(let i = 0; i < x.Ratings.length; i++){
          z += x.Ratings[i].Source + ": "+ x.Ratings[i].Value+"<br />"
        }
        document.getElementById("Rating").innerHTML =z;
        document.getElementById("Votes").innerHTML ="Awards: "+x.Awards+"<br/> IMDB Votes: "+x.imdbVotes;
        let t = "<br />"+"<br />"+"Rated: "+x.Rated+" Released: "+x.Released+" Runtime: "+x.Runtime+"<br><br> Production: "+x.Production+"<br><br> Languages: "+x.Language+"<br /><br />"+"Genre: "+x.Genre+"<br />"+"<br />"+"Plot: "+"<br />"+x.Plot
        t = "<a style=\'text-align:left;'"+t+"</a>"
        document.getElementById("Summary").innerHTML =t;
  }
  //console.log(movie2);
  let data2 = movie2;
  data2 = new sendTitle(data2);
  //console.log(data2);
  data2 = JSON.stringify(data2)
  xHttp.send(data2);
}

function AllReviews(){
  var url = document.location.href,
    params = url.split('?')[1].split('&'),
    data = {}, tmp;
    for (var i = 0, l = params.length; i < l; i++) {
        tmp = params[i].split('=');
        data[tmp[0]] = tmp[1];
    }
    let movies = data.search
    let movies2 = movies.split("%20");
    movies2 = movies2.join(' ')
    movies2 = movies2.replace('%3A', ":")
    movies2 = movies2.replace('%26', "&")
    movies2 = movies2.replace('%27',"'");
    url = 'http://localhost:3000/Review.html?search='+encodeURIComponent(movies2);
    document.location.href = url;
}

function sendRatingReq(){
        var url = document.location.href,
        params = url.split('?')[1].split('&'),
        data = {}, tmp;
        for (var i = 0, l = params.length; i < l; i++) {
          tmp = params[i].split('=');
          data[tmp[0]] = tmp[1];
        }
        let movie = data.search
        //console.log(movie);
        let movie2 = movie.split("%20");
        movie2 = movie2.join(' ')
        movie2 = movie2.replace('%3A', ":")
        movie2 = movie2.replace('%26', "&")
        movie2 = movie2.replace('%27',"'");
        let xHttp = new XMLHttpRequest();
        xHttp.open('POST','/sendRatingReq',true);
        xHttp.setRequestHeader('Content-Type','application/json');
        xHttp.onreadystatechange=(e)=>{
          let review = xHttp.responseText;
          document.getElementById("numberRating").innerHTML = "User Ratings: "+review+"/10";
        }
        let temp = new sendTitle(movie2);
        temp = JSON.stringify(temp)
        xHttp.send(temp);
  }
  function TextReview(){
    var url = document.location.href,
      params = url.split('?')[1].split('&'),
      data = {}, tmp;
      for (var i = 0, l = params.length; i < l; i++) {
          tmp = params[i].split('=');
          data[tmp[0]] = tmp[1];
      }
      let movies = data.search
      let movies2 = movies.split("%20");
      movies2 = movies2.join(' ')
      movies2 = movies2.replace('%3A', ":")
      movies2 = movies2.replace('%26', "&")
      movies2 = movies2.replace('%27',"'");
      var xHttp = new XMLHttpRequest();
      xHttp.open("POST","/TextRating",true);
      xHttp.setRequestHeader("Content-type","application/json");
      xHttp.onreadystatechange=(e)=>{
          console.log(xHttp.responseText);
          location.reload();
        }
        let rating = document.getElementById("reviews").value;
        data = new rateFilm(movies2,rating,localStorage.getItem("currentuser"));
        data = JSON.stringify(data);
        xHttp.send(data);
}
function makeRating(){
  var url = document.location.href,
    params = url.split('?')[1].split('&'),
    data = {}, tmp;
    for (var i = 0, l = params.length; i < l; i++) {
        tmp = params[i].split('=');
        data[tmp[0]] = tmp[1];
    }
    let movies = data.search
    let movies2 = movies.split("%20");
    movies2 = movies2.join(' ')
    movies2 = movies2.replace('%3A', ":")
    movies2 = movies2.replace('%26', "&")
    movies2 = movies2.replace('%27',"'");
  if (document.getElementById("ratingbox").value <= 10 && document.getElementById("ratingbox").value >= 0 && document.getElementById("ratingbox").value != ""){
    console.log(document.getElementById("ratingbox").value);
    var xHttp = new XMLHttpRequest();
    xHttp.open("POST","/numRating",true);
    xHttp.setRequestHeader("Content-type","application/json");
    xHttp.onreadystatechange=(e)=>{
        location.reload();
      }
      let rating = document.getElementById("ratingbox").value;
      data = new rateFilm(movies2,rating,localStorage.getItem("currentuser"));
      data = JSON.stringify(data);
      xHttp.send(data);
  }
}
function sendTitle(Title){
    this.Title= Title;
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
function SimMovies(){
  var url = document.location.href,
    params = url.split('?')[1].split('&'),
    data = {}, tmp;
    for (var i = 0, l = params.length; i < l; i++) {
        tmp = params[i].split('=');
        data[tmp[0]] = tmp[1];
    }
    let movies = data.search
    //console.log(movie);
    let movies2 = movies.split("%20");
    movies2 = movies2.join(' ')
    movies2 = movies2.replace('%3A', ":")
    movies2 = movies2.replace('%26', "&")
    movies2 = movies2.replace('%27',"'");
    url = 'http://localhost:3000/SimilarMovies.html?search='+encodeURIComponent(movies2);
    document.location.href = url;
}
function rateFilm(title,rating,user){
  this.Title = title;
  this.Rating = rating;
  this.User=user;
}
function toggleEditMovie(){
  let xHttp = new XMLHttpRequest();
  xHttp.open('POST','/contributing?',true);
  xHttp.setRequestHeader('Content-Type','text/plain');
  xHttp.onreadystatechange=(e)=>{
    console.log(xHttp.responseText);
    document.getElementById("contributing").style.visibility=xHttp.responseText;
  }
  let data= localStorage.getItem("currentuser");
  if (data === null){

  }else{
    xHttp.send(data);
  }
}
