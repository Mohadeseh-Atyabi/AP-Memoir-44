package com.company;

/**
 * Represents a piece in the game.
 */
public class Piece {
    //The team of the piece
    public Team team;
    //Shows whether the piece is killed or not.
    public boolean killed;
    //Bord of the piece
    public int bord;
    //Number of cells it can move
    public int toMove;

    public Piece(Team team){
        this.team = team;
        killed = false;
    }

    /**
     * Sets the bord of the piece
     * @param bord Bord of the piece
     */
    public void setBord(int bord){ this.bord = bord; }

    /**
     * Returns the bord of the piece
     * @return Bord of the piece
     */
    public int getBord(){ return bord; }

    /**
     * Returns the number of cells it can move
     * @param toMove Number of cells it can move
     */
    public void setToMove(int toMove){ this.toMove = toMove; }

    /**
     * Returns number of the cells this piece can moves
     * @return Number of cells it can move
     */
    public int getToMove(){ return toMove; }

    /**
     * Sets the killed field
     * @param killed Killed state
     */
    public void setKilled(boolean killed){ this.killed = killed; }

    /**
     * Returns the killed field
     * @return Killed field
     */
    public boolean isKilled(){ return killed; }

    /**
     * Returns the team of the piece
     * @return Team of the piece
     */
    public Team getTeam(){ return team; }


    /**
     * Shows the movement of the piece
     *
     * @param movement Shows how to move
     * @return Number of cells it moves
     */
    public int move(String movement){
        String[] parts = movement.split(" ");
        int moved = 0;

        for(int i=0 ; i<parts.length ; i++){
            StringBuilder test = new StringBuilder();

            for(Character c : parts[i].toCharArray()){
                if( Character.isDigit(c) ){
                    test.append(c);
                }
            }
            if(test.toString().length()==0){
                continue;
            }
            moved += Integer.parseInt(test.toString());
        }

        return moved;
    }

}
