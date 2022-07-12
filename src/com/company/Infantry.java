package com.company;

/**
 * It represents the Infantry in the game.
 */
public class Infantry extends Piece {

    public Infantry(Team team) {
        super(team);
        setBord(3);
        setToMove(2);
    }

    @Override
    public int move(String movement){ return super.move(movement); }

    @Override
    public String toString(){
        return "Infantry";
    }

}
