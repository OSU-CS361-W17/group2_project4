package edu.oregonstate.cs361.battleship;

/**
 * Created by michaelhilton on 1/5/17.
 */
public class Ship {
    protected String name;
    protected int length;
    protected Coordinate start;
    protected Coordinate end;
    protected boolean sunk;
    protected int health;
    protected boolean placed;

    public Ship(String n, int l, Coordinate s, Coordinate e) {
        name = n;
        length = l;
        health = l;
        sunk = false;
        start = s;
        end = e;
    }

    public void setLocation(Coordinate s, Coordinate e) {
        start = s;
        end = e;
    }

    public boolean covers(Coordinate test) {
        //horizontal
        if(start.getAcross() == end.getAcross()){
            if(test.getAcross() == start.getAcross()){
                if((test.getDown() >= start.getDown()) &&
                (test.getDown() <= end.getDown()))
                return true;
            }
            else {
                return false;
            }
        }
        //vertical
        else{
            if(test.getDown() == start.getDown()){
                if((test.getAcross() >= start.getAcross()) &&
                        (test.getAcross() <= end.getAcross()))
                    return true;
            }
            else {
                return false;
            }

        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void TakeDamage(){
        health--;
        if(health == 0){
            sunk = true;
        }
    }
}