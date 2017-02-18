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
    IsAllPlaced = false;

    //aircraftCarrier
    /*if(gameModel.aircraftCarrier.end.Down != 0 ||gameModel.aircraftCarrier.end.Across != 0){
        IsAllPlaced = true;
    }
    else{
        IsAllPlaced = false
    }*/

    //battleship
    if(gameModel.battleship.end.Down != 0 ||gameModel.battleship.end.Across != 0){
        IsAllPlaced = true;
    }
    else{
        IsAllPlaced = false
    }

    //cruiser
    if(gameModel.cruiser.end.Down != 0 ||gameModel.cruiser.end.Across != 0){
        IsAllPlaced = true;
    }
    else{
        IsAllPlaced = false
    }

    //destroyer
    if(gameModel.destroyer.end.Down != 0 ||gameModel.destroyer.end.Across != 0){
        IsAllPlaced = true;
    }
    else{
        IsAllPlaced = false
    }

    //submarine
    if(gameModel.submarine.end.Down != 0 ||gameModel.submarine.end.Across != 0){
        IsAllPlaced = true;
    }
    else{
        IsAllPlaced = false
    }

    return IsAllPlaced;
}

function placeShip() {
   console.log($( "#shipSelec" ).val());
   console.log($( "#rowSelec" ).val());
   console.log($( "#colSelec" ).val());
   console.log($( "#orientationSelec" ).val());

   //var menuId = $( "ul.nav" ).first().attr( "id" );
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
