var gameModel;
var DevToolsEnabled = false;

$( document ).ready(function() {
  // Handler for .ready() called.
  $.getJSON("model", function( json ) {
  gameModel = json;
    console.log( "JSON Data: " + json );
   });

});

function GameState_START(IsDonePlacingShip)
{
    if(IsDonePlacingShip == false){
        $("#EnemyPanel").addClass("hidden");
        $("#PlaceShipControl").removeClass("hidden");
    }
    else{
        $("#EnemyPanel").removeClass("hidden");
        $("#PlaceShipControl").addClass("hidden");
    }
}

function CheckAllPlayerShipsPlaced()
{
    var aircraftCarrier_placed = false;
    var battleship_placed = false;
    var submarine_placed = false;
    var clipper_placed = false;
    var dinghy_placed = false;

    //battleship
    if(gameModel.aircraftCarrier.end.Down != 0 ||gameModel.aircraftCarrier.end.Across != 0){
        aircraftCarrier_placed = true;
    }
    //battleship
    if(gameModel.battleship.end.Down != 0 ||gameModel.battleship.end.Across != 0){
        battleship_placed = true;
    }
    //submarine
    if(gameModel.submarine.end.Down != 0 ||gameModel.submarine.end.Across != 0){
        submarine_placed = true;
    }
    //clipper
    if(gameModel.clipper.end.Down != 0 ||gameModel.clipper.end.Across != 0){
        clipper_placed = true;
    }
    //dinghy
    if(gameModel.dinghy.end.Down != 0 ||gameModel.dinghy.end.Across != 0){
        dinghy_placed = true;
    }

    return (aircraftCarrier_placed &&
            battleship_placed &&
            submarine_placed &&
            clipper_placed &&
            dinghy_placed);
}


function placeShip() {
   console.log($( "#shipSelec" ).val());
   console.log($( "#rowSelec" ).val());
   console.log($( "#colSelec" ).val());
   console.log($( "#orientationSelec" ).val());

   var row_start = parseInt($("#rowSelec").val());
   var col_start = parseInt($("#colSelec").val());
   var row_end = row_start;
   var col_end = col_start;

  console.log($("#orientationSelec").val().toUpperCase());
  console.log($("#shipSelec").val().toUpperCase());
   if($("#orientationSelec").val().toUpperCase() === "HORIZONTAL") {
     if($("#shipSelec").val().toUpperCase() === "AIRCRAFTCARRIER") {
       col_end = col_start + 4;
     }
     else if($("#shipSelec").val().toUpperCase() === "BATTLESHIP") {
       col_end = col_start + 3;
     }
     else if($("#shipSelec").val().toUpperCase() === "CLIPPER") {
                 col_end = col_start + 2;
          }
     else if($("#shipSelec").val().toUpperCase() === "DINGHY") {
                 col_end = col_start;
     }
     else {
       col_end = col_start + 2;
     }
   }

   else {
     if($("#shipSelec").val().toUpperCase() === "AIRCRAFTCARRIER") {
       row_end = row_start + 4;
     }
     else if($("#shipSelec").val().toUpperCase() === "BATTLESHIP") {
       row_end = row_start + 3;
     }
     else if($("#shipSelec").val().toUpperCase() === "CLIPPER") {
             row_end = row_start + 2;
     }
     else if($("#shipSelec").val().toUpperCase() === "DINGHY") {
             row_end = row_start;
     }else {
       row_end = row_start + 2;
     }
   }

    console.log(row_start);
    console.log(col_start);
    console.log(row_end);
    console.log(col_end);

  if((row_start > gameModel.aircraftCarrier.end.Across || row_end < gameModel.aircraftCarrier.start.Across || col_start > gameModel.aircraftCarrier.end.Down || col_end < gameModel.aircraftCarrier.start.Down)
    && (row_start > gameModel.battleship.end.Across || row_end < gameModel.battleship.start.Across || col_start > gameModel.battleship.end.Down || col_end < gameModel.battleship.start.Down)
    && (row_start > gameModel.submarine.end.Across || row_end < gameModel.submarine.start.Across || col_start > gameModel.submarine.end.Down || col_end < gameModel.submarine.start.Down)
    && (row_start > gameModel.clipper.end.Across || row_end < gameModel.clipper.start.Across || col_start > gameModel.clipper.end.Down || col_end < gameModel.clipper.start.Down)
    && (row_start > gameModel.dinghy.end.Across || row_end < gameModel.dinghy.start.Across || col_start > gameModel.dinghy.end.Down || col_end < gameModel.dinghy.start.Down)
    && row_end <= 10 && col_end <= 10)
    {
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
       GameState_START(CheckAllPlayerShipsPlaced());
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

     if(gameModel.scanResult){
        alert("Scan found at least one Ship")}
     else{
        alert("Scan found no Ships")
     }

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}


function log(logContents){
    console.log(logContents);
}

function displayGameState(gameModel){

 displayShip(gameModel.aircraftCarrier);
 displayShip(gameModel.battleship);
 displayShip(gameModel.submarine);
 displayShip(gameModel.clipper);
 displayShip(gameModel.dinghy);

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
