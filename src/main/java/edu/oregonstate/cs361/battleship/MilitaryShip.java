package edu.oregonstate.cs361.battleship;

/**
 * Created by josep on 3/16/2017.
 */
public class MilitaryShip extends Ship{

    MilitaryShip(String n, int l, Coordinate s, Coordinate e, boolean st)
    {
        super(n, l, s, e);
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
