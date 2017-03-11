package edu.oregonstate.cs361.battleship;

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
    public Coordinate currentComputerShot;

    boolean scanResult = false;
    // (͡°͜ʖ͡°)
    boolean hardMode = false;
    // (͡°͜ʖ͡°)
    int fireMode = 0;


/*
/*            ╔══╗ Put this on your wall*/
/*            ║╔╗║    if you love anime!*/
/*            ║╚╝╠══╦╦══╦═╗*/
/*            ║╔╗║╔╗║║║║║╩╣*/
/*            ╚╝╚╩╝╚╩╩╩╩╩═╝*/
        /*╔╦╦╦╗OMG */
        /*╠╬╬╬╣CHOCOLATE!! */
        /*╠╬╬╬╣Put this on your page */
        /*╠╬╬╬╣If you LOVE */
        /*╚╩╩╩╝♥ CHOCOLATE */
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

    public boolean CoversComputerShips(Coordinate coorStart, Coordinate coorEnd){
        Coordinate coor;
        int Shiplength;
        char Orientation;

        if(coorStart.getAcross() == coorEnd.getAcross()){
            Shiplength = coorEnd.getDown() - coorStart.getDown();
            Orientation = 'A';
        }
        else{
            Shiplength = coorEnd.getAcross() - coorStart.getAcross();
            Orientation = 'D';
        }

        for(int i = 0; i < Shiplength;i++){
            if (Orientation== 'A'){
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

    public void placeShipRandomly(Ship ship){
        int max = 10;
        int min = 1;
        int shipLength = ship.length - 1;

        int randRow;
        int randCol;
        int randOren;

        Random random = new Random();
        Coordinate coorS;
        Coordinate coorE;

        while(true){
            randRow = random.nextInt(max - min + 1) + min;
            randCol = random.nextInt(max - min + 1) + min;
            randOren = random.nextInt(2) + 0;

            if(randOren == 1){
                coorS = new Coordinate(randRow,randCol);
                coorE = new Coordinate(randRow + shipLength,randCol);
                if(!CoversComputerShips(coorS,coorE) && WithinBounds(coorE)){
                    ship.setLocation(coorS,coorE);
                    break;
                }
            }
            else{
                coorS = new Coordinate(randRow,randCol);
                coorE = new Coordinate(randRow ,randCol + shipLength);
                if(!CoversComputerShips(coorS,coorE) && WithinBounds(coorE)){
                    ship.setLocation(coorS,coorE);
                    break;
                }
            }
        }
    }

    public BattleshipModel placeComputerShipsHard(){
        placeShipRandomly(computer_aircraftCarrier);
        placeShipRandomly(computer_battleship);
        placeShipRandomly(computer_submarine);
        placeShipRandomly(computer_clipper);
        placeShipRandomly(computer_dinghy);

        return this;
    }

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
            int CoorStartRow = computer_clipper.start.getAcross();
            int CoorStartCol = computer_clipper.start.getDown();

            if(computer_clipper.AxisPositioning() == 'V') {
                for (int i = 0; i < 3; i++) {
                    Coordinate CivShipCoor = new Coordinate(CoorStartRow, CoorStartCol + i);
                    computerHits.add(CivShipCoor);
                }
            }
            else {
                for (int i = 0; i < 3; i++) {
                    Coordinate CivShipCoor = new Coordinate(CoorStartRow + i, CoorStartCol);
                    computerHits.add(CivShipCoor);
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

    public boolean inRange(int x, int y){
        if(x < 10 && x > 1 && y < 10 && y > 1){
            return true;
        }else{
            return false;
        }
    }


    public boolean validShot(Coordinate coor)
    {
        boolean shot = false;

        if(inRange(coor.getAcross(), coor.getDown()) == false){
            return false;
        }else if(computerMisses.contains(coor)== true || computerHits.contains(coor) == true){
            return false;
        }else if(lastComputerShot == playerMisses.get(playerMisses.size() -1)){

        }else if(true ){

        }else if(true){

        }
        return true;
    }

    public void shootAtPlayer() {
       int randRow = 0;
       int randCol = 0;
       Coordinate coor = new Coordinate(randRow,randCol);

        if(hardMode == true) {
           if (playerMisses.get(playerMisses.size() - 1) == lastComputerShot) {
               fireMode = 0;
           } else if (playerHits.get(playerHits.size() - 1) == lastComputerShot) {
               fireMode = 1;
           } else if (coor) {

           }
       }
       else {
           int max = 10;
           int min = 1;
           Random random = new Random();
           randRow = random.nextInt(max - min + 1) + min;
           randCol = random.nextInt(max - min + 1) + min;
       }

        lastComputerShot = coor;
        playerShot(coor);
    }

    void playerShot(Coordinate coor) {
        if(playerMisses.contains(coor)) {
            System.out.println("Duplicate fire.");
        }

        if(aircraftCarrier.covers(coor)) {
            playerHits.add(coor);
        }
        else if (battleship.covers(coor)) {
            playerHits.add(coor);
        }
        else if (submarine.covers(coor)) {
            playerHits.add(coor);
        }
        else if (clipper.covers(coor)) {
            int CoorStartRow = clipper.start.getAcross();
            int CoorStartCol = clipper.start.getDown();

            if(clipper.AxisPositioning() == 'V') {
                for (int i = 0;i<3;i++) {
                    Coordinate CivShipCoor = new Coordinate(CoorStartRow, CoorStartCol + i);
                    playerHits.add(CivShipCoor);
                }
            }
            else {
                for (int i = 0;i<3;i++) {
                    Coordinate CivShipCoor = new Coordinate(CoorStartRow + i, CoorStartCol);
                    playerHits.add(CivShipCoor);
                }
            }
        }
        else if (dinghy.covers(coor)) {
            playerHits.add(coor);
        }
        else {
            playerMisses.add(coor);
        }
    }

    public void shootAtPlayer() {
        int randRow = 0;
        int randCol = 0;
        Coordinate coor = new Coordinate(randRow,randCol);

        if(hardMode == true) {
            if (playerMisses.get(playerMisses.size() - 1) == lastComputerShot) {
                fireMode = 0;
            } else if (playerHits.get(playerHits.size() - 1) == lastComputerShot) {
                fireMode = 1;
            } else if (playerShotHit(coor) == true) {

            }
        }
        else {
            int max = 10;
            int min = 1;
            Random random = new Random();
            randRow = random.nextInt(max - min + 1) + min;
            randCol = random.nextInt(max - min + 1) + min;
        }

        lastComputerShot = coor;
        playerShot(coor);
    }

    boolean playerShotHit(Coordinate coor) {
        boolean validHit = false;
        if(playerMisses.contains(coor)) {
            System.out.println("Duplicate fire.");
        }

        if(aircraftCarrier.covers(coor)) {
            validHit = true;
        }
        else if (battleship.covers(coor)) {
            validHit = true
        }
        else if (submarine.covers(coor)) {
            validHit = true;
        }
        else if (clipper.covers(coor)) {
            int CoorStartRow = clipper.start.getAcross();
            int CoorStartCol = clipper.start.getDown();

            if(clipper.AxisPositioning() == 'V') {
                for (int i = 0;i<3;i++) {
                    Coordinate CivShipCoor = new Coordinate(CoorStartRow, CoorStartCol + i);
                    validHit = true;
                }
            }
            else {
                for (int i = 0;i<3;i++) {
                    Coordinate CivShipCoor = new Coordinate(CoorStartRow + i, CoorStartCol);
                    validHit = true;
                }
            }
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