package com.company;

/**
 * It represents a tank in the game
 */
public class Tank extends Piece {

    public Tank(Team team){
        super(team);
        setBord(3);
        setToMove(3);
    }

    @Override
    public int move(String movement){ return super.move(movement); }

    @Override
    public String toString(){
        super.toString();
        return "Tank";
    }
}
