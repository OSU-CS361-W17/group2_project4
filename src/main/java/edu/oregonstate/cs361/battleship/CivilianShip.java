package edu.oregonstate.cs361.battleship;

/**
 * Created by josep on 2/23/2017.
 */
public class CivilianShip extends Ship{

    CivilianShip(String n, int l, Coordinate s, Coordinate e) {
        name = n;
        length = l;
        sunk = false;
        start = s;
        end = e;
        health = 1;
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