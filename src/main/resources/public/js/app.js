var gameModel;

$( document ).ready(function() {
  // Handler for .ready() called.
  $.getJSON("model", function( json ) {
  gameModel = json;
    console.log( "JSON Data: " + json );
   });
});

function placeShip() {
   console.log($( "#shipSelec" ).val());
   console.log($( "#rowSelec" ).val());
   console.log($( "#colSelec" ).val());
   console.log($( "#orientationSelec" ).val());

   var valid = false;
   var row_start = parseInt($("#rowSelec").val());
   var col_start = parestInt($("#colSelec").val());
   var row_end = row_start;
   var col_end = col_start;
   if($("#orientationSelec").val().toUpperCase === "HORIZONTAL") {
     if($("#shipSelec").val().toUpperCase() === "AIRCRAFTCARRIER") {
       col_end = col_start + 5;
     }
     if($("#shipSelec").val().toUpperCase() === "BATTLESHIP") {
       col_end = col_start + 4;
     }
     if($("#shipSelec").val().toUpperCase() === "CRUISER") {
       col_end = col_start + 3;
     }
     if($("#shipSelec").val().toUpperCase() === "DEESTROYER") {
       col_end = col_start + 2;
     }
     if($("#shipSelec").val().toUpperCase() === "SUBMARINE") {
       col_end = col_start + 2;
     }
   }

   else {
     if($("#shipSelec").val().toUpperCase() === "AIRCRAFTCARRIER") {
       row_end = row_start + 5;
     }
     if($("#shipSelec").val().toUpperCase() === "BATTLESHIP") {
       row_end = row_start + 4;
     }
     if($("#shipSelec").val().toUpperCase() === "CRUISER") {
       row_end = row_start + 3;
     }
     if($("#shipSelec").val().toUpperCase() === "DEESTROYER") {
       row_end = row_start + 2;
     }
     if($("#shipSelec").val().toUpperCase() === "SUBMARINE") {
       row_end = row_start + 2;
     }
   }

   if((row_start > gameModel.aircraftCarrier.end.Across || row_end < gameModel.aircraftCarrier.start.Across || col_start > gameModel.aircraftCarrier.end.Down || col_end < gameModel.aircraftCarrier.start.Down) && (row_start > gameModel.battleship.end.Across || row_end < gameModel.battleship.start.Across || col_start > gameModel.battleship.end.Down || col_end < gameModel.battleship.start.Down) && (row_start > gameModel.cruiser.end.Across || row_end < gameModel.cruiser.start.Across || col_start > gameModel.cruiser.end.Down || col_end < gameModel.cruiser.start.Down) && (row_start > gameModel.destroyer.end.Across || row_end < gameModel.destroyer.start.Across || col_start > gameModel.destroyer.end.Down || col_end < gameModel.destroyer.start.Down) && (row_start > gameModel.submarine.end.Across || row_end < gameModel.submarine.start.Across || col_start > gameModel.submarine.end.Down || col_end < gameModel.submarine.start.Down) && row_end <= 10 && col_end >= 10) {
     var request = $.ajax({
       url: "/placeShip/"+$( "#shipSelec" ).val()+"/"+$( "#rowSelec" ).val()+"/"+$( "#colSelec" ).val()+"/"+$( "#orientationSelec" ).val(),
       method: "post",
       data: JSON.stringify(gameModel),
       contentType: "application/json; charset=utf-8",
       dataType: "json"
     });

     request.done(function( currModel ) {
       displayGameState(currModel);
       gameModel = currModel;
     });

     request.fail(function( jqXHR, textStatus ) {
       alert( "Request failed: " + textStatus );
     });
   }

   else {
     alert("Error: Invalid Ship Placement.");
   }
}




function fire(){
  console.log($( "#colFire" ).val());
  console.log($( "#rowFire" ).val());
//var menuId = $( "ul.nav" ).first().attr( "id" );
  var request = $.ajax({
    url: "/fire/"+$( "#colFire" ).val()+"/"+$( "#rowFire" ).val(),
    method: "post",
    data: JSON.stringify(gameModel),
    contentType: "application/json; charset=utf-8",
    dataType: "json"
  });

  request.done(function( currModel ) {
    displayGameState(currModel);
    gameModel = currModel;
  });

  request.fail(function( jqXHR, textStatus ) {
    alert( "Request failed: " + textStatus );
  });
}

function scan(){
 console.log($( "#colScan" ).val());
   console.log($( "#rowScan" ).val());
//var menuId = $( "ul.nav" ).first().attr( "id" );
   var request = $.ajax({
     url: "/scan/"+$( "#colScan" ).val()+"/"+$( "#rowScan" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}


function log(logContents){
    console.log(logContents);
}

function displayGameState(gameModel){


if(gameModel.scanResult){
alert("Scan found at least one Ship")}
else{
alert("Scan found no Ships")}

displayShip(gameModel.aircraftCarrier);
displayShip(gameModel.battleship);
displayShip(gameModel.cruiser);
displayShip(gameModel.destroyer);
displayShip(gameModel.submarine);

for (var i = 0; i < gameModel.computerMisses.length; i++) {
   $( '#TheirBoard #' + gameModel.computerMisses[i].Across + '_' + gameModel.computerMisses[i].Down ).css("background-color", "none");
   $( '#TheirBoard #' + gameModel.computerMisses[i].Across + '_' + gameModel.computerMisses[i].Down ).css("background-image", "url('../images/missPIC.png')");

}
for (var i = 0; i < gameModel.computerHits.length; i++) {
   $( '#TheirBoard #' + gameModel.computerHits[i].Across + '_' + gameModel.computerHits[i].Down ).css("background-color", "none");
      $( '#TheirBoard #' + gameModel.computerHits[i].Across + '_' + gameModel.computerHits[i].Down ).css("background-image", "url('../images/hitPIC.png')");

}

for (var i = 0; i < gameModel.playerMisses.length; i++) {
   $( '#MyBoard #' + gameModel.playerMisses[i].Across + '_' + gameModel.playerMisses[i].Down ).css("background-color", "none");
   $( '#MyBoard #' + gameModel.playerMisses[i].Across + '_' + gameModel.playerMisses[i].Down ).css("background-image", "url('../images/missPIC.png')");
}
for (var i = 0; i < gameModel.playerHits.length; i++) {
   $( '#MyBoard #' + gameModel.playerHits[i].Across + '_' + gameModel.playerHits[i].Down ).css("background-color", "none");
   $( '#MyBoard #' + gameModel.playerHits[i].Across + '_' + gameModel.playerHits[i].Down ).css("background-image", "url('../images/hitPIC.png')");

}



}



function displayShip(ship){
 startCoordAcross = ship.start.Across;
 startCoordDown = ship.start.Down;
 endCoordAcross = ship.end.Across;
 endCoordDown = ship.end.Down;
// console.log(startCoordAcross);
 if(startCoordAcross > 0){
    if(startCoordAcross == endCoordAcross){
        for (i = startCoordDown; i <= endCoordDown; i++) {
            $( '#MyBoard #'+startCoordAcross+'_'+i  ).css("background-color", "none");
            $( '#MyBoard #'+startCoordAcross+'_'+i  ).css("background-image", "url('../images/ironPIC.png')");
        }
    } else {
        for (i = startCoordAcross; i <= endCoordAcross; i++) {
            $( '#MyBoard #'+i+'_'+startCoordDown  ).css("background-color", "none");
            $( '#MyBoard #'+i+'_'+startCoordDown  ).css("background-image", "url('../images/ironPIC.png')");
        }
    }
 }



}
