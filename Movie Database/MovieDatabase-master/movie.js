function sendTitle(Title){
    this.Title= Title;
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

function allMovie(){
    let xHttp = new XMLHttpRequest();
    xHttp.open('GET','/AllMovies',true);
    xHttp.setRequestHeader('Content-Type','application/json');
    xHttp.onreadystatechange=(e)=>{
        let x = JSON.parse(xHttp.responseText)
        for(let i = 0; i < 9; i++){
            document.getElementById("img"+i).src = x[i].Poster
        }
    }
    xHttp.send()
}

function Movie(){
    let xHttp = new XMLHttpRequest();
    xHttp.open("POST","/findFilm",true);
    xHttp.setRequestHeader("Content-type","text/plain");
    xHttp.onreadystatechange=(e)=>{
      console.log(xHttp.responseText)
      if(xHttp.responseText === "no movie"){
        let x = "Movie does not exist"
        document.getElementById("status").innerHTML = x;
        console.log("hehe")
      }
      else{
        if(xHttp.responseText !== ""){
          var b = xHttp.responseText;
        url = 'http://localhost:3000/MovieInfo.html?search='+encodeURIComponent(b);
        document.location.href = url;
        }
        //window.location.replace(xHttp.responseText);

      }
    }
    movie = document.getElementById('search').value;
    //movie = movie.toUpperCase();
    let data = movie;
    xHttp.send(data);
  }
  function sendTitle(Title){
    this.Title= Title;
  }
function sortGenre(){
  let xHttp = new XMLHttpRequest();
  xHttp.open('POST','/Genre',true);
  xHttp.setRequestHeader('Content-Type','application/json');
  xHttp.onreadystatechange=(e)=>{
    let x = JSON.parse(xHttp.responseText)
      var string="";
      for(let i=0; i<x.length;i++){
        string+=x[i]+"<br>";
      }
      document.getElementById("mainMovie").innerHTML = string;
      console.log(x)

  }
  let data2 = document.getElementById("search").value;
  data2 = new sendTitle(data2);
  //console.log(data2);
  data2 = JSON.stringify(data2)
  xHttp.send(data2);
}
function sortYear(){
  let xHttp = new XMLHttpRequest();
  xHttp.open('POST','/Year',true);
  xHttp.setRequestHeader('Content-Type','application/json');
  xHttp.onreadystatechange=(e)=>{
    let x = JSON.parse(xHttp.responseText)
    var string="";
      for(let i=0; i<x.length;i++){
        string+=x[i]+"<br>";
    }
    document.getElementById("mainMovie").innerHTML = string;
  }
  let data2 = document.getElementById("search").value;
  data2 = new sendTitle(data2);
  console.log(data2);
  data2 = JSON.stringify(data2)
  xHttp.send(data2);
}
function sortRating(){
  let xHttp = new XMLHttpRequest();
  xHttp.open('POST','/MinRating',true);
  xHttp.setRequestHeader('Content-Type','application/json');
  xHttp.onreadystatechange=(e)=>{
    let x = JSON.parse(xHttp.responseText)
    var string="";
      for(let i=0; i<x.length;i++){
        string+=x[i]+"<br>";
    }
    document.getElementById("mainMovie").innerHTML = string;

  }
  let data2 = document.getElementById("search").value;
  if(data2 > 10){
    data2 = 10;
  }
  data2 = new sendTitle(data2);
  console.log(data2);
  data2 = JSON.stringify(data2);
  console.log(data2);
  xHttp.send(data2);
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

function cast(){
    var url = document.location.href,
    params = url.split('?')[1].split('&'),
    data = {}, tmp;
    for (var i = 0, l = params.length; i < l; i++) {
        tmp = params[i].split('=');
        data[tmp[0]] = tmp[1];
    }
    let cast = data.search
    //console.log(movie);
    let cast2 = cast.split("%20");
    cast2 = cast2.join(' ')
    cast2 = cast2.replace('%3A', ":")
    cast2 = cast2.replace('%26', "&")
    cast2 = cast2.replace('%27',"'");
    let xHttp = new XMLHttpRequest();
    xHttp.open("POST","/castProf",true);
    xHttp.setRequestHeader("Content-type","application/json");
    xHttp.onreadystatechange=(e)=>{
        let x = JSON.parse(xHttp.responseText);
        let y = [];
        let test="";
        y.push("Acted in");
        for(let i = 0; i < x.acted[0].length; i++){
          //console.log(x.acted[0][i].Title);
          if(i===0){
            y.push("<br><br>"+" "+x.acted[0][i].Title);
          }else{
            y.push(" "+x.acted[0][i].Title);
          }
        }
        y.push("<br><br>Directed");
        for(let i = 0; i < x.directed[0].length; i++){
          if(i===0){
            y.push("<br><br>"+x.directed[0][i].Title);
          }else{
              y.push(" "+x.directed[0][i].Title);
          }
        }
        y.push("<br><br>Wrote");
        for(let i = 0; i < x.wrote[0].length; i++){
          if(i===0){
            y.push("<br><br>"+x.wrote[0][i].Title+" ");
          }else{
            y.push(x.wrote[0][i].Title+" ");
          }
        }
        //console.log(y);
        document.getElementById("mainMovie").innerHTML = y;
    }
    document.getElementById("mName").innerHTML = "<br><br><br>"+cast2;
    cast2 = cast2.substring(cast2.indexOf(':')+1,cast2.length);

    let data2 = cast2;
    data2 = new sendTitle(data2);
    data2 = JSON.stringify(data2);
    //console.log(data2);
    xHttp.send(data2);
}



function newMov(Title,Year,Rated,Released,Runtime,Genre,Director,Writer,Actors,Plot,Language,Country,Awards,Poster,Ratings,Metascore,imdbRating,imdbVotes,imdbID,Type,DVD,BoxOffice,Production,Website,Response,user){
  this.Title = Title;
  this.Year= Year;
  this.Rated= Rated;
  this.Released= Released;
  this.Runtime = Runtime;
  this.Genre = Genre;
  this.Director= Director;
  this.Writer= Writer;
  this.Actors= Actors;
  this.Plot = Plot;
  this.Language = Language;
  this.Country= Country;
  this.Awards= Awards;
  this.Poster= Poster;
  this.Ratings = Ratings;
  this.Metascore = Metascore;
  this.imdbRating= imdbRating;
  this.imdbVotes= imdbVotes;
  this.imdbID= imdbID;
  this.Type = Type;
  this.DVD = DVD;
  this.BoxOffice= BoxOffice;
  this.Production= Production;
  this.Website= Website;
  this.Response = Response;
  this.user = user;
}

function newRating(Source, Value){
  this.Source = Source;
  this.Value = Value;
}

function newMovie(){
  let xHttp = new XMLHttpRequest();
  xHttp.open("POST","/movies",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
      document.getElementById("status2").innerHTML = x;
  }
  document.getElementById("Director").required;
  document.getElementById("Writer").required;
  document.getElementById("Actors").required;
  let Title = document.getElementById("Title").value
  let Year = document.getElementById("Year").value
  let Rated = document.getElementById("Rated").value
  let Released = document.getElementById("Released").value
  let Runtime = document.getElementById("Runtime").value
  let Genre = document.getElementById("Genre").value
  let Director = document.getElementById("Director").value
  let Writer = document.getElementById("Writer").value
  let Actors = document.getElementById("Actors").value
  let Plot = document.getElementById("Plot").value
  let Language = document.getElementById("Language").value
  let Country = document.getElementById("Country").value
  let Awards = document.getElementById("Awards").value
  let Poster = document.getElementById("Poster").value
  let Ratings = [];
  let Ratings2 = document.getElementById("Ratings").value
  let Ratings3 = new newRating("BlockedBuster",Ratings2);
  Ratings.push(Ratings3);
  let Metascore = document.getElementById("Metascore").value
  let imdbRating = document.getElementById("imdbRating").value
  let imdbVotes = document.getElementById("imdbVotes").value
  let imdbID = document.getElementById("imdbID").value
  let Type = document.getElementById("Type").value
  let DVD = document.getElementById("DVD").value
  let BoxOffice = document.getElementById("BoxOffice").value
  let Production = document.getElementById("Production").value
  let Website = document.getElementById("Website").value
  let Response = document.getElementById("Response").value
  let user = localStorage.getItem("currentuser");
  let temp = new newMov(Title,Year,Rated,Released,Runtime,Genre,Director,Writer,Actors,Plot,Language,Country,Awards,Poster,Ratings,Metascore,imdbRating,imdbVotes,imdbID,Type,DVD,BoxOffice,Production,Website,Response,user);
  //console.log(Ratings);
  //console.log(temp);
  let data2 = JSON.stringify(temp);
  xHttp.send(data2);
}

function eMovie(Title,Type,Change,user){
  this.Title = Title;
  this.Type = Type;
  this.Change = Change;
  this.user=user;
}

function editMovie(){
  let xHttp = new XMLHttpRequest();
  xHttp.open("POST","/editFilm",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      let x = xHttp.responseText;
      document.getElementById("status").innerHTML = x;
  }
  let Title = document.getElementById("Movie").value
  let Type = document.getElementById("newtext").value
  let Change = document.getElementById("Change").value
  let user = localStorage.getItem("currentuser");
  let check = Type.toUpperCase();
  if(check==="RATINGS"){
     let temp = Change;
     Change = new newRating("BlockedBuster",temp);
  }
  let temp = new eMovie(Title,Type,Change,user);
  let data2 = JSON.stringify(temp);
  xHttp.send(data2);
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

function SimilarMovies(){
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
    movies2 = movies2.replace('%27',"'");

  let xHttp = new XMLHttpRequest();
  xHttp.open("POST","/SMovies",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
      let y = []
      var s = ""
      s+=("Similar Movies of Director: <br><br>")
      for(let i = 0; i < x.Director.length; i++){
        s+=(x.Director[i].Title+"<br>");
      }
      s+=("<br><br>Similar Movies of Genre: <br><br>")
      for(let i = 0; i < x.Genre.length; i++){
        s+=(x.Genre[i].Title+"<br>");
      }

      document.getElementById('sMovies').innerHTML = s+"<br><br>";
  }
  data = movies2;
  let data2 = new sendTitle(data);
  data2 = JSON.stringify(data2);
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

function grabPoster(){
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
    let xHttp = new XMLHttpRequest();
    xHttp.open("POST","/Poster",true);
    xHttp.setRequestHeader("Content-type","text/plain");
    xHttp.onreadystatechange=(e)=>{
      let x=xHttp.responseText;
        document.getElementById("moviepost").src = x;
    }
    xHttp.send(movies2);
}

function Reviews(){
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
  let xHttp = new XMLHttpRequest();
  xHttp.open("POST","/RMovies",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
      if(typeof(x[0]) === 'undefined'){
          document.getElementById("totalrating").innerHTML= "There are No Ratings!";
      }
      let revnum= x[0].Ratings.length
      var string=""
      document.getElementById("mTitle").innerHTML = x[0].Title+" Reviews";
      if(x[0].Totalrating>0){
        document.getElementById("totalrating").innerHTML= "Total Rating: "+x[0].Totalrating+"/10 ("+revnum+" user reviews)";
      }else{
        document.getElementById("totalrating").innerHTML= "There has not yet been any reviews out of 10 for this Film!";
      }

      if(x[0].Review.length >0){
          for(let i=0; i<x[0].Review.length;i++){
            string += "\""+ x[0].Review[i].review+"\" Reviewed by "+x[0].Review[i].username+"<br>";
          }
      }else{
          document.getElementById("mReviews").innerHTML="There are no Text Reviews at this time";
      }
      document.getElementById("mReviews").innerHTML=string;

  }
  data = movies2;
  let data2 = new sendTitle(data);
  data2 = JSON.stringify(data2);
  xHttp.send(data2);
}

function rateFilm(title,rating,user){
  this.Title = title;
  this.Rating = rating;
  this.User=user;
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
function follows(user, person){
  this.user = user;
  this.person = person;
}
function follow(){
  var url2 = document.location.href,
    params2 = url2.split('?')[1].split('&'),
    data2 = {}, tmp2;
    for (var i = 0, l = params2.length; i < l; i++) {
        tmp2 = params2[i].split('=');
        data2[tmp2[0]] = tmp2[1];
    }
    let movies = data2.search
    let movies2 = movies.split("%20");
    movies2 = movies2.join(' ')
    movies2 = movies2.replace('%3A', ":")
    movies2 = movies2.replace('%26', "&")
    movies2 = movies2.replace('%27',"'");
  var xHttp = new XMLHttpRequest();
    xHttp.open("POST","/followers",true);
    xHttp.setRequestHeader("Content-type","application/json");
    xHttp.onreadystatechange=(e)=>{
        console.log(xHttp.responseText);
        window.location.reload();
    }
    let user = localStorage.getItem("currentuser");
    let data = new follows(user,movies2);
    data = JSON.stringify(data);
    xHttp.send(data);
}
function ReccMovies(){
  var xHttp = new XMLHttpRequest();
    xHttp.open("POST","/RecFilms",true);
    xHttp.setRequestHeader("Content-type","application/json");
    xHttp.onreadystatechange=(e)=>{
      let x = JSON.parse(xHttp.responseText);
      document.getElementById('sMovies').innerHTML = x.Title;
    }
    let user = localStorage.getItem("currentuser");
    let data = new sendTitle(user);
    data = JSON.stringify(data);
    xHttp.send(data);
}

function sendRating(User, Rating){
  this.User = User;
  this.Rating = Rating;
}

function minRecc(){
  var xHttp = new XMLHttpRequest();
  xHttp.open("POST","/minReccMovies",true);
  xHttp.setRequestHeader("Content-type","application/json");
  xHttp.onreadystatechange=(e)=>{
    let x = JSON.parse(xHttp.responseText);
    document.getElementById('sMovies').innerHTML = x.Title;
  }
  let user = localStorage.getItem("currentuser");
  let rating = document.getElementById("min").value
  let data = new sendRating(user,rating);
  data = JSON.stringify(data);
  xHttp.send(data);
}

function toggleFollow(){
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
    movies2 = movies2.replace('%27',"'");
  let xHttp = new XMLHttpRequest();
  xHttp.open('POST','/togglePersonfollow?',true);
  xHttp.setRequestHeader('Content-Type','Application/json');
  xHttp.onreadystatechange=(e)=>{
    let x=xHttp.responseText;
    if(x==="true"){
      document.getElementById("Follow").innerHTML = "unfollow";
    }
  }
  let temp = new addFriendName(localStorage.getItem("currentuser"),movies2);
  let data2 = JSON.stringify(temp);
  xHttp.send(data2);
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
