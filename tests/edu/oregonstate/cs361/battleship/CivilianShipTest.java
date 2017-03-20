package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by zain on 3/2/17.
 */
class CivilianShipTest {
    @Test
    void AxisPositioning()
    {
        CivilianShip ModelHorizontal = new CivilianShip("TEST1", 3, new Coordinate(1,10), new Coordinate(3,10));
        CivilianShip ModelVertical = new CivilianShip("TEST2", 1, new Coordinate(10,1), new Coordinate(10,3));

        assertEquals('H',ModelHorizontal.AxisPositioning());
        assertEquals('V',ModelVertical.AxisPositioning());

    }
}
