package com.company;

/**
 * Represents the artillery in the game.
 */
public class Artillery extends Piece {

    public Artillery(Team team){
        super(team);
        setBord(6);
        setToMove(1);
    }

    @Override
    public int move(String movement){ return super.move(movement); }

    @Override
    public String toString(){
        super.toString();
        return "Artillery";
    }

}
