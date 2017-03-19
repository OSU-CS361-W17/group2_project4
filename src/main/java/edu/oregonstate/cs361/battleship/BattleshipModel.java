package edu.oregonstate.cs361.battleship;

import sun.plugin.dom.core.CoreConstants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {
    private Ship aircraftCarrier = new Ship("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0),false);
    private Ship battleship = new Ship("Battleship",4, new Coordinate(0,0),new Coordinate(0,0),true);
    private Ship submarine = new Ship("Submarine",3, new Coordinate(0,0),new Coordinate(0,0),true);
    private  CivilianShip clipper = new CivilianShip("Clipper", 3, new Coordinate(0,0), new Coordinate(0,0),false);
    private  CivilianShip dinghy = new CivilianShip("Dinghy", 1, new Coordinate(0,0), new Coordinate(0,0),false);

    public Ship computer_aircraftCarrier = new Ship("Computer_AircraftCarrier",5, new Coordinate(2,2),new Coordinate(2,6),false);
    public Ship computer_battleship = new Ship("Computer_Battleship",4, new Coordinate(2,8),new Coordinate(5,8),true);
    public Ship computer_submarine = new Ship("Computer_Submarine",3, new Coordinate(9,6),new Coordinate(9,8),true);
    public  CivilianShip computer_clipper = new CivilianShip("Computer_Clipper", 3, new Coordinate(1,10), new Coordinate(3,10),false);
    public CivilianShip computer_dinghy = new CivilianShip("Computer_Dinghy", 1, new Coordinate(1,1), new Coordinate(1,1),false);

    public ArrayList<Coordinate> playerHits;
    public ArrayList<Coordinate> playerMisses;
    public ArrayList<Coordinate> computerHits;
    public ArrayList<Coordinate> computerMisses;
    public Coordinate lastComputerShot;
    public Coordinate currentTarget;

    boolean scanResult = false;

    boolean hardMode = false;

    int fireMode = 1;
    char direction = 1;
    int currentDirection = 1;

    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
    }

    public Ship getShip(String shipName) {
        if (shipName.equalsIgnoreCase("aircraftcarrier")) {
            return aircraftCarrier;
        }
        if(shipName.equalsIgnoreCase("battleship")) {
            return battleship;
        }
        if(shipName.equalsIgnoreCase("submarine")) {
            return submarine;
        }
        if(shipName.equalsIgnoreCase("clipper")) {
            return clipper;
        }
        if(shipName.equalsIgnoreCase("dinghy")) {
            return dinghy;
        }
        if (shipName.equalsIgnoreCase("computer_aircraftcarrier")) {
            return computer_aircraftCarrier;
        }
        if(shipName.equalsIgnoreCase("computer_battleship")) {
            return computer_battleship;
        }
        if(shipName.equalsIgnoreCase("computer_submarine")) {
            return computer_submarine;
        }
        if(shipName.equalsIgnoreCase("computer_clipper")) {
            return computer_clipper;
        }
        if(shipName.equalsIgnoreCase("computer_dinghy")) {
            return computer_dinghy;
        }
        else {
            return null;
        }
    }
    /*This function takes in two coordinates for a potential ship placement and checks to make sure that another computer ship
    has not already been placed at the desired locations
     */
    public boolean CoversComputerShips(Coordinate coorStart, Coordinate coorEnd){
        Coordinate coor;
        int Shiplength;
        char Orientation;

        if(coorStart.getAcross() == coorEnd.getAcross()){
            Shiplength = coorEnd.getDown() - coorStart.getDown();
            Orientation = 'V';
        }
        else{
            Shiplength = coorEnd.getAcross() - coorStart.getAcross();
            Orientation = 'H';
        }
        for(int i = 0; i < Shiplength;i++){
            if (Orientation== 'H'){
                coor = new Coordinate(coorStart.getAcross()+i,coorStart.getDown());
                if( computer_aircraftCarrier.covers(coor) || computer_battleship.covers(coor) || computer_submarine.covers(coor) || computer_clipper.covers(coor) || computer_dinghy.covers(coor)){
                    return true;
                }
            }
            else{
                coor = new Coordinate(coorStart.getAcross(),coorStart.getDown()+i);
                if( computer_aircraftCarrier.covers(coor) || computer_battleship.covers(coor) || computer_submarine.covers(coor) || computer_clipper.covers(coor) || computer_dinghy.covers(coor)){
                    return true;
                }
            }
        }
        return false;
    }
    /*checks if a particular coordinate is within range*/
    public boolean WithinBounds(Coordinate coor){
        int max = 11;
        int min = 0;

        if(coor.getAcross() < max && coor.getAcross() > min && coor.getDown() < max && coor.getDown() > min){
            return true;
        }
        else{
            return false;
        }
    }
    /*Helper method to placeComputerShipsHard Checks for previous computer ship placement*/
    public void placeShipRandomly(Ship ship){
        int max = 10;
        int shipLength = ship.length;

        int randRow;
        int randCol;
        int randOren;

        Random random = new Random();
        Coordinate start;
        Coordinate end ;

        while(true){
            randRow = random.nextInt(max) + 1;
            randCol = random.nextInt(max) + 1;
            randOren = random.nextInt(2);

            //vertical
            if(randOren == 1){
                start = new Coordinate(randRow,randCol);
                end = new Coordinate(randRow + shipLength,randCol);
                if(!CoversComputerShips(start,end) && WithinBounds(end)){
                    ship.setLocation(start,end);
                    break;
                }
            }
            //horizontal
            else{
                start = new Coordinate(randRow,randCol);
                end = new Coordinate(randRow ,randCol + shipLength);
                if(!CoversComputerShips(start,end) && WithinBounds(end)){
                    ship.setLocation(start,end);
                    break;
                }
            }
        }
    }
    /*Places computer ships randomly*/
    public BattleshipModel placeComputerShipsHard(){
        placeShipRandomly(computer_aircraftCarrier);
        placeShipRandomly(computer_battleship);
        placeShipRandomly(computer_submarine);
        placeShipRandomly(computer_clipper);
        placeShipRandomly(computer_dinghy);

        return this;
    }
    /*This function places the player's ships according the specifications passed in and returns the whole model*/
    public BattleshipModel placeShip(String shipName, String row, String col, String orientation) {
        int rowint = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        if(orientation.equals("horizontal")) {
            if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 4));
            }
            if (shipName.equalsIgnoreCase("battleship")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 3));
            }
            if (shipName.equalsIgnoreCase("submarine")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2));
            }
            if (shipName.equalsIgnoreCase("clipper")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2));
            }
            if (shipName.equalsIgnoreCase("dinghy")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt));
            }
        }
        else{
        //vertical
            if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint + 4, colInt));
            }
            if(shipName.equalsIgnoreCase("battleship")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint + 3, colInt));
            }
            if(shipName.equalsIgnoreCase("submarine")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt));
            }
            if(shipName.equalsIgnoreCase("clipper")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt));
            }
            if(shipName.equalsIgnoreCase("dinghy")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt));
            }
        }
        return this;
    }
    /*This method checks if the fire coordinate passed in hits a computer ship*/
    public void shootAtComputer(int row, int col) {
        Coordinate coor = new Coordinate(row,col);

        if(computer_aircraftCarrier.covers(coor)) {
            computerHits.add(coor);
        }
        else if (computer_battleship.covers(coor)) {
            computerHits.add(coor);
        }
        else if (computer_submarine.covers(coor)) {
            computerHits.add(coor);
        }
        else if (computer_clipper.covers(coor)) {
            int coorStartRow = computer_clipper.start.getAcross();
            int coorStartCol = computer_clipper.start.getDown();

            if(computer_clipper.AxisPositioning() == 'V') {
                for (int i = 0; i < 3; i++) {
                    Coordinate CivShipCoor = new Coordinate(coorStartRow, coorStartCol + i);
                    computerHits.add(CivShipCoor);
                }
            }
            else {
                for (int i = 0; i < 3; i++) {
                    Coordinate civShipCoor = new Coordinate(coorStartRow + i, coorStartCol);
                    computerHits.add(civShipCoor);
                }
            }

        }
        else if (computer_dinghy.covers(coor)) {
            computerHits.add(coor);
        }
        else {
            computerMisses.add(coor);
        }
    }
    /*Checks that the player's shot is valid*/
     public boolean validShot(Coordinate coor)
    {
        if(WithinBounds(coor) == false){
            return false;
        }else if(playerHits.contains(coor)== true || playerMisses.contains(coor) == true){
            return false;
        }else{
            return true;
        }
    }
    /*This method resets the target based upon what direction is passed in*/
    public Coordinate directionShot(int fireDirection){
        if(fireDirection == 1){
            //up
            currentTarget.setDown(currentTarget.getDown() - 1);
        }else if(fireDirection == 2) {
            //down
            currentTarget.setDown(currentTarget.getDown() + 1);
            //left
        }else if(fireDirection == 3){
                //left
            currentTarget.setAcross(currentTarget.getAcross() - 1);
        }else if(fireDirection == 4){
            //right
            currentTarget.setAcross(currentTarget.getAcross() + 1);
        }
        return currentTarget;
    }

    public Coordinate hardModeShot(Coordinate coor){
        Random random = new Random();
        if(fireMode == 1){
            while(validShot(coor) == false) {
                coor.setAcross(random.nextInt(10) + 1);
                coor.setDown(random.nextInt(10) + 1);
            }
            if(playerShotHit(coor) == true) {
                fireMode = 2;
                currentTarget = coor;
            }
            return coor;
        }
        else if(fireMode == 2){
            coor = directionShot(random.nextInt(5)+1);
            while (validShot(coor) ==  false && direction < 5){
                coor = directionShot(direction);
                direction++;
            }
            if(direction == 5){
                fireMode = 1;
                direction = 1;
            }else{
                currentDirection = direction;
                fireMode = 3;
            }
            return coor;
        }
        else if(fireMode == 3){
            coor = directionShot(currentDirection);
            if(validShot(coor) == false){
                while(validShot(coor) == false){
                    coor.setAcross(random.nextInt(10) + 1);
                    coor.setDown(random.nextInt(10) + 1);
                }
                fireMode = 1;
            }
            if(playerShotHit(coor) == false){
                fireMode = 1;
            }
        }
        return coor;
    }
    /*computer fires at player randomly if in hard mode and in a pattern if in easy*/
    public void shootAtPlayer() {
       int randRow = 0;
       int randCol = 0;
       int currRow = lastComputerShot.getAcross();
       int currCol=lastComputerShot.getDown();
        Coordinate currCompShot = new Coordinate(randRow,randCol);

        Coordinate coor = new Coordinate(randRow,randCol);
       int patternRow;
       int patternCol;

        if(hardMode == true) {
            coor = hardModeShot(coor);
       }
       else {
            do {
                currCompShot.setAcross(currRow+2);
                currCompShot.setDown(currCol+2);
            }
            while(WithinBounds(currCompShot));
        }
        lastComputerShot = coor;
        playerShot(coor);
    }
    /*Damages ships on player's end if shot at*/
    void playerShot(Coordinate coor) {
        if(playerMisses.contains(coor)) {
            System.out.println("Duplicate fire.");
        }
        if(aircraftCarrier.covers(coor) && aircraftCarrier.sunk == false) {
            aircraftCarrier.TakeDamage();
            playerHits.add(coor);
        }
        else if (battleship.covers(coor) && battleship.sunk == false) {
            battleship.TakeDamage();
            playerHits.add(coor);
        }
        else if (submarine.covers(coor) && submarine.sunk == false) {
            submarine.TakeDamage();
            playerHits.add(coor);
        }
        else if (clipper.covers(coor) && clipper.sunk == false) {
                    clipper.TakeDamage();
                    clipper.sunk = true;
                    playerHits.add(coor);
        }
        else if (dinghy.covers(coor) && dinghy.sunk == false) {
            dinghy.TakeDamage();
            dinghy.sunk = true;
            playerHits.add(coor);
        }
        else {
            playerMisses.add(coor);
        }
    }
    /*checks if the computer's shot hit player's ships*/
     boolean playerShotHit(Coordinate coor) {
        boolean validHit = false;
        if(playerMisses.contains(coor)) {
            System.out.println("Duplicate fire.");
        }

        if(aircraftCarrier.covers(coor)) {
            validHit = true;
        }
        else if (battleship.covers(coor)) {
            validHit = true;
        }
        else if (submarine.covers(coor)) {
            validHit = true;
        }
        else if (clipper.covers(coor)) {
            validHit = true;
        }
        else if (dinghy.covers(coor)) {
            validHit = true;
        }
        else {
            validHit = false;
        }
        return validHit;
    }

    public void scan(int rowInt, int colInt) {
        Coordinate coor = new Coordinate(rowInt,colInt);
        scanResult = false;
        if(computer_aircraftCarrier.scan(coor)){
            scanResult = true;
        }
        else if (computer_battleship.scan(coor)){
            scanResult = true;
        }
        else if (computer_submarine.scan(coor)){
            scanResult = true;
        }
        else if (computer_clipper.scan(coor)){
            scanResult = true;
        }
        else if (computer_dinghy.scan(coor)) {
            scanResult = true;
        }
        else {
            scanResult = false;
        }
    }

    public int getPlayerHits() {
        return playerHits.size();
    }

    public int getPlayerMisses() {
        return playerMisses.size();
    }

    public int getComputerHits() {
        return computerHits.size();
    }

    public int getComputerMisses() {
        return computerMisses.size();
    }

    public boolean getScanResult() {
        return scanResult;
    }
}