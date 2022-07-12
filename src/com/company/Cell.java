package com.company;

/**
 * It represents each cell in the board which contains a pice.
 * Each cell can has a type that limits the movement of pieces.
 */
public class Cell {
    //Position of the cell on x_axis.
    private int x_position;
    //Position of the cell on y_axis.
    private int y_position;
    //The piece in this cell.
    private Piece piece;
    //number of pieces init
    private int numOfPieces;
    //Type of the cell.
    private Type type;

    public Cell(int x_position, int y_position){
        this.x_position = x_position;
        this.y_position = y_position;
    }

    /**
     * Returns the position of the cell on x_axis.
     * @return X_position
     */
    public int getX_position(){ return x_position; }

    /**
     * Returns the position of the cell on y_axis.
     * @return Y_position
     */
    public int getY_position(){ return y_position; }

    /**
     * Sets the group which is in the cell by receiving the piece and  the number of it.
     * @param piece Piece in the cell
     * @param num number of pieces in it
     */
    public void setPiece(Piece piece, int num){
        this.piece = piece;
        this.numOfPieces = num;
    }

    /**
     * Returns the number of pieces in the cell
     * @return Number of pieces in the cell
     */
    public int getNumOfPieces(){ return numOfPieces; }

    /**
     * Returns the piece which is in the cell.
     * @return Piece in the cell
     */
    public Piece getPiece(){ return piece; }

    /**
     * Sets the type of the cell.
     * @param type Type of the cell
     */
    public void setType(Type type){ this.type = type; }

    /**
     * Returns the type of the cell.
     * @return Type of the cell
     */
    public Type getType(){ return type; }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Cell)){
            return false;
        }

        Cell other = (Cell) obj;
        return this.getY_position()==other.getY_position() &&
                this.getX_position()== other.getX_position();
    }

    @Override
    public int hashCode(){
        int res = 17;
        res = 37*res + this.getX_position();
        res = 37*res + this.getY_position();
        return res;
    }

    /**
     * Sets number of pieces in this cell
     * @param numOfPieces Number of pieces in this cell
     */
    public void setNumOfPieces(int numOfPieces){ this.numOfPieces = numOfPieces; }
}
