function loadGetMsg() {
    let nameVar = document.getElementById("name").value;
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        var jsonResponse = JSON.parse(this.responseText);
        var Content = "<h2>Title: " + jsonResponse.Title + "</h2>";
        Content += "<p>Year: " + jsonResponse.Year + "</p>";
        Content += "<p>Rated: " + jsonResponse.Rated + "</p>";
        Content += "<p>Released: " + jsonResponse.Released + "</p>";
        Content += "<p>Runtime: " + jsonResponse.Runtime + "</p>";
        Content += "<p>Genre: " + jsonResponse.Genre + "</p>";
        Content += "<p>Director: " + jsonResponse.Director + "</p>";
        Content += "<p>Writer: " + jsonResponse.Writer + "</p>";
        Content += "<p>Actors: " + jsonResponse.Actors + "</p>";
        Content += "<p>Plot: " + jsonResponse.Plot + "</p>";
        Content += "<p>Language: " + jsonResponse.Language + "</p>";
        Content += "<p>Country: " + jsonResponse.Country + "</p>";
        Content += "<p>Awards: " + jsonResponse.Awards + "</p>";
        Content += "<p>Poster: <br> " +`<img src="${jsonResponse.Poster}" alt="Movie poster" width="150">`+ "</p>";
        Content += "<p>Metascore: " + jsonResponse.Metascore + "</p>";
        Content += "<p>imdbRating: " + jsonResponse.imdbRating + "</p>";
        Content += "<p>imdbVotes: " + jsonResponse.imdbVotes + "</p>";
        Content += "<p>imdbID: " + jsonResponse.imdbID + "</p>";
        Content += "<p>Type: " + jsonResponse.Type + "</p>";
        Content += "<p>DVD: " + jsonResponse.DVD + "</p>";
        Content += "<p>BoxOffice: " + jsonResponse.BoxOffice + "</p>";
        Content += "<p>Production: " + jsonResponse.Production + "</p>";
        Content += "<p>Website: " + jsonResponse.Website + "</p>";
        Content += "<p>Response: " + jsonResponse.Response + "</p>";
        document.getElementById("getrespmsg").innerHTML = Content;
    }
    xhttp.open("GET", "http://localhost:35000/Movies?t="+nameVar);
    xhttp.send();
};
