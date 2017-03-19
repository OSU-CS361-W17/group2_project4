package edu.oregonstate.cs361.battleship;

/**
 * Created by joseph on 2/23/2017.
 */
public class CivilianShip extends Ship{

    CivilianShip(String n, int l, Coordinate s, Coordinate e, boolean st)
    {
        super(n, l, s, e, st);
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


}