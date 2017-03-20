package edu.oregonstate.cs361.battleship;

import sun.plugin.dom.core.CoreConstants;

import java.util.ArrayList;
import java.util.Random;

public class BattleshipModel {
    private MilitaryShip aircraftCarrier = new MilitaryShip("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0), false);
    private MilitaryShip battleship = new MilitaryShip("Battleship",4, new Coordinate(0,0),new Coordinate(0,0),true);
    private MilitaryShip submarine = new MilitaryShip("Submarine",3, new Coordinate(0,0),new Coordinate(0,0),true);
    private CivilianShip clipper = new CivilianShip("Clipper", 3, new Coordinate(0,0), new Coordinate(0,0));
    private CivilianShip dinghy = new CivilianShip("Dinghy", 1, new Coordinate(0,0), new Coordinate(0,0));

    public MilitaryShip computer_aircraftCarrier = new MilitaryShip("Computer_AircraftCarrier",5, new Coordinate(2,2),new Coordinate(2,6), false);
    public MilitaryShip computer_battleship = new MilitaryShip("Computer_Battleship",4, new Coordinate(2,8),new Coordinate(5,8),true);
    public MilitaryShip computer_submarine = new MilitaryShip("Computer_Submarine",3, new Coordinate(9,6),new Coordinate(9,8),true);
    public CivilianShip computer_clipper = new CivilianShip("Computer_Clipper", 3, new Coordinate(1,10), new Coordinate(3,10));
    public CivilianShip computer_dinghy = new CivilianShip("Computer_Dinghy", 1, new Coordinate(1,1), new Coordinate(1,1));

    public ArrayList<Coordinate> playerHits;
    public ArrayList<Coordinate> playerMisses;
    public ArrayList<Coordinate> computerHits;
    public ArrayList<Coordinate> computerMisses;
    public Coordinate lastComputerShot;
    public Coordinate currentTarget;

    public static final int RANDOM_FIRING = 1;
    public static final int ADJACENT_FIRING = 2;
    public static final int LINE_FIRING = 3;

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    boolean scanResult = false;
    boolean hardMode = false;
    boolean hasFlipped = false;
    int fireMode = RANDOM_FIRING;
    int direction = UP;
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

     public boolean validShot(Coordinate coor)
    {
        if(WithinBounds(coor) == false){
            return false;
        }else if(playerHits.contains(coor)== true){
            return false;
        }else if(playerMisses.contains(coor) == true){
            return false;
        }else {
            return true;
        }
    }

    public Coordinate directionShot(int fireDirection){
        if(fireDirection == 0){
            //up
            currentTarget.setDown(currentTarget.getDown() - 1);
        }else if(fireDirection == 1){
            //right
            currentTarget.setAcross(currentTarget.getAcross() + 1);

        }else if(fireDirection == 2){
            //down
            currentTarget.setDown(currentTarget.getDown() + 1);
        }else if(fireDirection == 3){
            //left
            currentTarget.setAcross(currentTarget.getAcross() - 1);
        }
        return currentTarget;
    }




    public Coordinate hardModeShot(Coordinate coor){
        Random random = new Random();
        if(fireMode == 1){
            //random fire until hit then go to firemode 2
            while(validShot(coor) == false) {
                coor.setAcross(random.nextInt(10 - 1 + 1) + 1);
                coor.setDown(random.nextInt(10 - 1 + 1) + 1);
            }
            if(playerShotHit(coor) == true) {
                fireMode = 2;
                currentTarget = coor;
            }
            return coor;
        }
        else if(fireMode == 2){
            //scan for shots around initial hit
            while (validShot(coor) ==  false && direction < 4){
                //pick direction from initial hit
                coor = directionShot(direction);
                direction++;
            }
            if(direction == 4){
                fireMode = 1;
                direction = 1;
            }else if(!playerShotHit(coor) && !playerShotSunk(coor) && direction != 5){
                //if not a hit or not sunk pick a new direction
                direction++;
            }else if(playerShotHit(coor)){
                //if shot is a hit go to firemode 3 and fire in one direction
                currentDirection = direction;
                fireMode = 3;
            }
            return coor;
        }
        else if(fireMode == 3){
            //fire in one direction
            coor = directionShot(currentDirection);
            if(validShot(coor) == true && !playerShotSunk(coor)){
                hasFlipped = false;
                if(playerShotSunk(coor)){
                    fireMode = 1;
                }
                return coor;
            }
            else{
                //if reached the end of a ship try flipping direction and firing on the other side
                if(!hasFlipped){
                    hasFlipped = true;
                    //flip firing direction
                    currentDirection = (currentDirection + 2) % 4;
                    coor = directionShot(currentDirection);
                    while(directionScan(coor) == 1) {
                        //move in new direction until reach a miss/bounds or an empty space
                        coor = directionShot(currentDirection);
                    }
                    if(directionScan(coor) == 0){
                        //miss or outof bounds return to firemode 1
                        fireMode = 1;
                        while(!validShot(coor)) {
                            coor.setAcross(random.nextInt(10 - 1 + 1) + 1);
                            coor.setDown(random.nextInt(10 - 1 + 1) + 1);
                        }
                        return coor;
                    }
                    else if(directionScan(coor) == 2){
                        //fire on empty space stay in firemode 3
                        return coor;
                    }
                }
            }
        }
        return coor;
    }

    int directionScan(Coordinate coor){
       if(playerMisses.contains(coor) || !WithinBounds(coor)) {
           return 0;
       }else if(playerHits.contains(coor)){
           return 1;
       }else{
           return 2;
       }
    }

    public void shootAtPlayer() {
       int randRow = 0;
       int randCol = 0;
       Coordinate coor = new Coordinate(randRow,randCol);

        if(hardMode == true) {
            coor = hardModeShot(coor);
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

    boolean playerShotSunk(Coordinate coor) {
        boolean validHit = false;
        if(playerMisses.contains(coor)) {
            System.out.println("Duplicate fire.");
        }

        if(aircraftCarrier.covers(coor) && aircraftCarrier.sunk == true) {
            validHit = true;
        }
        else if (battleship.covers(coor) && battleship.sunk == true) {
            validHit = true;
        }
        else if (submarine.covers(coor) && submarine.sunk == true) {
            validHit = true;
        }
        else if (clipper.covers(coor) && clipper.sunk == true) {
            validHit = true;
        }
        else if (dinghy.covers(coor) && dinghy.sunk == true) {
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