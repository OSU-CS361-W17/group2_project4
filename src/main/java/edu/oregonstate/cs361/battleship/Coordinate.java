package edu.oregonstate.cs361.battleship;

/**
 * Created by michaelhilton on 1/8/17.
 */
public class Coordinate {
    private int across;
    private int down;

    public Coordinate(int letter, int number) {
        across = letter;
        down = number;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getAcross() {
        return across;
    }

    public void setAcross(int across) {
        this.across = across;
    }
}
