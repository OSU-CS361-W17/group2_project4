package edu.oregonstate.cs361.battleship;

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
    public Coordinate lineTarget;
    public Coordinate adjacentTarget;
    public Coordinate scanTarget;

    public static final int RANDOM_FIRING = 1;
    public static final int ADJACENT_FIRING = 2;
    public static final int LINE_FIRING = 3;
    public static final int LINE_FIRING_2 = 4;

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    public static final int CANT_FIRE = 0;
    public static final int KEEP_SCANNING = 1;
    public static final int CAN_FIRE = 2;

    boolean scanResult = false;
    boolean hardMode = false;
    boolean hasFlipped = false;
    int fireMode = RANDOM_FIRING;
    int direction = UP;
    int currentDirection = UP;

    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses = new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses = new ArrayList<>();
    }

    Random random = new Random();

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
        int max = 10;
        int min = 1;

        if(coor.getAcross() <= max && coor.getAcross() >= min && coor.getDown() <= max && coor.getDown() >= min) {
            return true;
        }
        else {
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

     public boolean validShot(Coordinate coor) {
        if(WithinBounds(coor) == false) {
            return false;
        }
        else if(playerHitsHas(coor)== true) {
            return false;
        }
        else if(playerMissesHas(coor) == true) {
            return false;
        }
        else {
            return true;
        }
    }

    public Coordinate directionShot(int fireDirection, Coordinate target) {
        Coordinate c = new Coordinate(0,0);
        if(fireDirection == UP) {
            c.setAcross(target.getAcross());
            c.setDown(target.getDown() - 1);
        }
        else if(fireDirection == RIGHT) {
            c.setAcross(target.getAcross() + 1);
            c.setDown(target.getDown());
        }
        else if(fireDirection == DOWN) {
            c.setAcross(target.getAcross());
            c.setDown(target.getDown() + 1);
        }
        else if(fireDirection == LEFT) {
            c.setAcross(target.getAcross() - 1);
            c.setDown(target.getDown());
        }
        return c;
    }

    public Coordinate hardModeShot(Coordinate coor) {
        int numDirections = 4;
        if(fireMode == RANDOM_FIRING) {
            //random fire until hit then go to adjacent firing
            System.out.println("Random Firing");
            return randFire(coor);
        }
        else if(fireMode == ADJACENT_FIRING){
            //scan for shots around initial hit
            coor = directionShot(direction, adjacentTarget);
            System.out.println("Adjacent Firing: Current Direction: " + direction);
            direction++;
            while (!validShot(coor) && direction < numDirections) {
                //pick direction from initial hit
                coor = directionShot(direction, adjacentTarget);
                System.out.println("Adjacent Firing: Previous Direction Invalid. New Direction: " + direction);
                direction++;
            }
            if(!playerShotHit(coor) && !playerShotSunk(coor) && direction != numDirections) {
                //if not a hit or not sunk pick a new direction (done automatically in while loop)
                System.out.println("Adjacent Firing: valid direction but not hit or sink.");
            }
            else if(playerShotSunk(coor) && direction != numDirections) {
                //if sunk, return to random firing after this shot
                fireMode = RANDOM_FIRING;
                System.out.println("Adjacent Firing: Ship sunk. Now entering Random Firing Mode.");
            }
            else if(playerShotHit(coor) && direction != numDirections) {
                //if shot is a hit go to line firing mode
                fireMode = LINE_FIRING;
                System.out.println("Adjacent Firing: Hit. Now entering Line Firing mode.");
                currentDirection = direction - 1;
                lineTarget = coor;
                hasFlipped = false;
            }
            else {
                //if all adjacent directions are exhausted, fire randomly
                fireMode = RANDOM_FIRING;
                System.out.println("Adjacent Firing: All directions exhausted, firing randomly.");
                coor = randFire(coor);
            }
            System.out.println("Firing at: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
            return coor;
        }
        else if(fireMode == LINE_FIRING) {
            //fire in one direction
            coor = directionShot(currentDirection, lineTarget);
            System.out.println("Line Firing: current direction: " + currentDirection);
            if(validShot(coor)) {
                if(playerShotSunk(coor)) {
                    fireMode = RANDOM_FIRING;
                    System.out.println("Line Firing: Ship Sunk. Entering Random Firing Mode.");
                }
                else if(playerShotHit(coor)) {
                    System.out.println("Line Firing: Hit. Continuing Line Firing Mode.");
                    lineTarget = coor;
                }
                else {
                    if(!hasFlipped) {
                        System.out.println("Line Firing: Miss and haven't flipped. Will try firing in other direction.");
                        fireMode = LINE_FIRING_2;
                    }
                    else {
                        System.out.println("Line Firing: Miss and have already flipped. Entering Random Firing Mode.");
                        fireMode = RANDOM_FIRING;
                    }
                }
                System.out.println("Firing at: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
                return coor;
            }
            else if(playerHitsHas(coor)) {
                System.out.println("Line Firing: invalid fire location: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64) + " is a hit");
                while(playerHitsHas(coor)) {
                    coor = directionShot(currentDirection, coor);
                    System.out.println("Line Firing: Scanning past a hit. New fire location: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
                }
                if(validShot(coor)) {
                    System.out.println("Line Firing: fire location now valid.");
                    lineTarget = coor;
                    System.out.println("Firing at: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
                    return coor;
                }
                else {
                    if(!hasFlipped) {
                        System.out.println("Line Firing: reached a miss or out of bounds. Now scanning in other direction.");
                        return flip(coor);
                    }
                    else {
                        System.out.println("Line Firing: reached a miss or out of bounds and have already flipped. Firing randomly.");
                        fireMode = RANDOM_FIRING;
                        return randFire(coor);
                    }
                }
            }
            else {
                //if reached the end of a ship try flipping direction and firing on the other side
                System.out.println("Line Firing: invalid fire location and not a hit: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
                if(!hasFlipped){
                    System.out.println("Will scan in other direction.");
                    return flip(coor);
                }
                else {
                    System.out.println("Have already flipped. Firing randomly.");
                    fireMode = RANDOM_FIRING;
                    return randFire(coor);
                }
            }
        }
        else if(fireMode == LINE_FIRING_2) {
            System.out.println("Line Firing 2");
            flip(lineTarget);
        }
        System.out.println("Firing at: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
        return coor;
    }

    public Coordinate flip(Coordinate coor) {
        System.out.println("Flip: Previous: " + currentDirection + ". New: " + (currentDirection + 2) % 4);
        hasFlipped = true;
        int numDirections = 4;
        currentDirection = (currentDirection + 2) % 4;
        coor = directionShot(currentDirection, coor);
        while(directionScan(coor) == KEEP_SCANNING) {
            System.out.println("Scanning past a hit: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
            coor = directionShot(currentDirection, coor);
        }
        if(directionScan(coor) == CANT_FIRE) {
            //miss or out of bounds return to random firing
            System.out.println("Flip: Can't fire here because miss or OOB: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
            fireMode = RANDOM_FIRING;
            coor = randFire(coor);
        }
        else {
            //fire on empty space and stay in line-firing mode
            System.out.println("Flip: Firing on empty space and continuing Line Firing Mode.");
            fireMode = LINE_FIRING;
            lineTarget = coor;
        }

        if(coor == null){
            coor = new Coordinate(0,0);
        }
        System.out.println("Firing at: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
        return coor;
    }

    public Coordinate randFire(Coordinate coor) {
        while(!validShot(coor)) {
            System.out.println("Randfire: current shot invalid: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
            coor.setAcross(random.nextInt(10) + 1);
            coor.setDown(random.nextInt(10) + 1);
        }
        if(playerShotHit(coor)) {
            System.out.println("Randfire: entering Adjacent Firing Mode because shot hit.");
            fireMode = ADJACENT_FIRING;
            direction = UP;
            adjacentTarget = coor;
        }
        System.out.println("Firing at: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
        return coor;
    }

    int directionScan(Coordinate coor){
        System.out.println("Scanning coordinate: " + coor.getDown() + ", " + (char)(coor.getAcross() + 64));
        if(playerMissesHas(coor) || !WithinBounds(coor)) {
            System.out.println("Returning CANT_FIRE");
            return CANT_FIRE;
        }
        else if(playerHitsHas(coor)) {
            System.out.println("Returning KEEP_SCANNING");
            return KEEP_SCANNING;
        }
        else {
            System.out.println("Returning CAN_FIRE");
            return CAN_FIRE;
        }
    }

    public boolean playerHitsHas(Coordinate coor) {
        for (int i = 0; i < playerHits.size(); i++) {
            if((playerHits.get(i).getAcross() == coor.getAcross()) && (playerHits.get(i).getDown() == coor.getDown())) {
                return true;
            }
        }
        return false;
    }

    public boolean playerMissesHas(Coordinate coor) {
        for (int i = 0; i < playerHits.size(); i++) {
            if((playerMisses.get(i).getAcross() == coor.getAcross()) && (playerMisses.get(i).getDown() == coor.getDown())) {
                return true;
            }
        }
        return false;
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
           coor = new Coordinate(randRow, randCol);
        }
        playerShot(coor);
    }

    void playerShot(Coordinate coor) {
        if(playerMissesHas(coor)) {
            System.out.println("Duplicate fire.");
        }

        if(aircraftCarrier.covers(coor) && aircraftCarrier.sunk == false) {
            aircraftCarrier.TakeDamage();
            System.out.println(coor.getDown() + ", " + (char)(coor.getAcross() + 64) + " added to hits list.");
            playerHits.add(coor);
        }
        else if (battleship.covers(coor) && battleship.sunk == false) {
            battleship.TakeDamage();
            System.out.println(coor.getDown() + ", " + (char)(coor.getAcross() + 64) + " added to hits list.");
            playerHits.add(coor);
        }
        else if (submarine.covers(coor) && submarine.sunk == false) {
            submarine.TakeDamage();
            System.out.println(coor.getDown() + ", " + (char)(coor.getAcross() + 64) + " added to hits list.");
            playerHits.add(coor);
        }
        else if (clipper.covers(coor) && clipper.sunk == false) {
            clipper.TakeDamage();
            System.out.println(coor.getDown() + ", " + (char)(coor.getAcross() + 64) + " added to hits list.");
            clipper.sunk = true;
            playerHits.add(coor);
        }
        else if (dinghy.covers(coor) && dinghy.sunk == false) {
            System.out.println(coor.getDown() + ", " + (char)(coor.getAcross() + 64) + " added to hits list.");
            dinghy.TakeDamage();
            dinghy.sunk = true;
            playerHits.add(coor);
        }
        else {
            System.out.println(coor.getDown() + ", " + (char)(coor.getAcross() + 64) + " added to misses list.");
            playerMisses.add(coor);
        }
    }

    boolean playerShotHit(Coordinate coor) {
        boolean validHit = false;
        if(playerMissesHas(coor)) {
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
        if(playerMissesHas(coor)) {
            System.out.println("Duplicate fire.");
        }
        if(aircraftCarrier.covers(coor) && aircraftCarrier.health == 1) {
            return true;
        }
        if (battleship.covers(coor) && battleship.health == 1) {
            return true;
        }
        if (submarine.covers(coor) && submarine.health == 1) {
            return true;
        }
        if (clipper.covers(coor)) {
            return true;
        }
        if (dinghy.covers(coor)) {
            return true;
        }
        return false;
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