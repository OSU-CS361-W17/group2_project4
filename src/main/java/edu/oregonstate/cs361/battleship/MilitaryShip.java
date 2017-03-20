package edu.oregonstate.cs361.battleship;

/**
 * Created by josep on 3/16/2017.
 */
public class MilitaryShip extends Ship {
    protected boolean stealth;

    MilitaryShip(String n, int l, Coordinate s, Coordinate e, boolean st) {
        name = n;
        length = l;
        sunk = false;
        start = s;
        end = e;
        health = l;
        stealth = st;
    }

    public char AxisPositioning()
    {
        if(start.getDown() == end.getDown()){
            return 'H';
        }
        else {
            return 'V';
        }
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
}
