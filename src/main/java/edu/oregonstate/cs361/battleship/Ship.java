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
    protected boolean stealth;

    public Ship(String n, int l, Coordinate s, Coordinate e, boolean st) {
        name = n;
        length = l;
        health = l;
        sunk = false;
        start = s;
        end = e;
        stealth = st;
    }

    public void setLocation(Coordinate s, Coordinate e) {
        start = s;
        end = e;
    }
    /*This method checks if the passed in coordinate is covered by the ship it is invoked on*/
    public boolean covers(Coordinate test) {
        //vertical
        if((start.getAcross() == end.getAcross())&&
                (test.getAcross() == start.getAcross()))
        {
            if((test.getDown() >= start.getDown()) && (test.getDown() <= end.getDown()))
                return true;

            else{
            return false;
            }
        }
        //horizontal
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

    public boolean scan(Coordinate coor) {
        if(stealth) {
            return false;
        }
        if(covers(coor)){
            return true;
        }
        if(covers(new Coordinate(coor.getAcross()-1,coor.getDown()))){
            return true;
        }
        if(covers(new Coordinate(coor.getAcross()+1,coor.getDown()))){
            return true;
        }
        if(covers(new Coordinate(coor.getAcross(),coor.getDown()-1))){
            return true;
        }
        if(covers(new Coordinate(coor.getAcross(),coor.getDown()+1))){
            return true;
        }
        return false;
    }

    public void TakeDamage(){
        health--;
        if(health == 0){
            sunk = true;
        }
    }
}