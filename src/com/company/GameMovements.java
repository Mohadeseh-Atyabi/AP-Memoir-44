package com.company;

/**
 * Represents different kind of movements during the game.
 */
public class GameMovements {

    /**
     * Goes right one cell
     * @param xPosition Origin x_position
     * @return Final position
     */
    public int goRight(int xPosition){
        return ++xPosition;
    }

    /**
     * Goes left one cell
     * @param xPosition Origin x_position
     * @return Final position
     */
    public int goLeft(int xPosition){
        return --xPosition;
    }

    /**
     * Goes up one cell
     * @param yPosition Origin y_position
     * @return Final position
     */
    public int goUp(int yPosition){
        return ++yPosition;
    }

    /**
     * Goes down one cell
     * @param yPosition Origin y_position
     * @return Final position
     */
    public int goDown(int yPosition){
        return --yPosition;
    }

    public Cell displace(int originX, int originY, String description){
        switch (description) {

            case "R":
                originX = goRight(originX);
                break;

            case "L":
                originX = goLeft(originX);

                break;

            case "UL":
                if (originY % 2 == 1) {
                    originY = goUp(originY);
                } else {
                    originX = goLeft(originX);
                    originY = goUp(originY);
                }

                break;

            case "UR":
                if (originY % 2 == 1) {
                    originX = goRight(originX);
                    originY = goUp(originY);
                } else {
                    originY = goUp(originY);
                }
                break;

            case "DL":
                if (originY % 2 == 1) {
                    originY = goDown(originY);
                } else {
                    originX = goLeft(originX);
                    originY = goDown(originY);
                }
                break;

            case "DR":
                if (originY % 2 == 1) {
                    originY = goDown(originY);
                    originX = goRight(originX);
                } else {
                    originY = goDown(originY);
                }
                break;
        }
        return (new Cell(originX, originY));
    }

    /**
     * It calculates the least distance between origin cell and destination cell to attack.
     * @param originX X position of origin cell
     * @param originY Y position of origin cell
     * @param destX X position of destination cell
     * @param destY Y position of destination cell
     * @return Distance between two cells
     */
    public int displaceToAttack(int originX, int originY, int destX, int destY){
        Cell result = new Cell(originX,originY);
        int distance = 0;


        while ( !(originX==destX && originY==destY) ) {

            //If x_position of destination is equal to x_position of origin
            if (destX == originX) {

                //If y_position of destination is greater than y_position of origin
                if (destY > originY) {
                    if(originY % 2 == 0) {
                        result = displace(originX, originY, "UR");
                        originX = result.getX_position();
                        originY = result.getY_position();
                        distance++;
                    }
                    else{
                        result = displace(originX, originY, "UL");
                        originX = result.getX_position();
                        originY = result.getY_position();
                        distance++;
                    }
                }

                //If y_position of destination is less than y_position of origin
                if(destY < originY){
                    if(originY % 2 == 0) {
                        result = displace(originX, originY, "DR");
                        originX = result.getX_position();
                        originY = result.getY_position();
                        distance++;
                    }
                    else{
                        result = displace(originX, originY, "DL");
                        originX = result.getX_position();
                        originY = result.getY_position();
                        distance++;
                    }
                }
            }

            //If x_position of destination is less than x_position of origin
            else if( destX < originX ){

                //If y-position of destination equals to y_position of origin
                if (destY == originY){
                    result = displace(originX, originY, "L");
                    originX = result.getX_position();
                    originY = result.getY_position();
                    distance++;
                }

                //If y-position of destination is less than y_position of origin
                if (destY < originY){
                    result = displace(originX, originY, "DL");
                    originX = result.getX_position();
                    originY = result.getY_position();
                    distance++;
                }

                //If y-position of destination is greater than y_position of origin
                if (destY > originY){
                    result = displace(originX, originY, "UL");
                    originX = result.getX_position();
                    originY = result.getY_position();
                    distance++;
                }

            }

            //If x_position of destination is greater than x_position of origin
            else {

                //If y-position of destination equals to y_position of origin
                if (destY == originY){
                    result = displace(originX, originY, "R");
                    originX = result.getX_position();
                    originY = result.getY_position();
                    distance++;
                }

                //If y-position of destination is less than y_position of origin
                if (destY < originY){
                    result = displace(originX, originY, "DR");
                    originX = result.getX_position();
                    originY = result.getY_position();
                    distance++;
                }

                //If y-position of destination is greater than y_position of origin
                if (destY > originY){
                    result = displace(originX, originY, "UR");
                    originX = result.getX_position();
                    originY = result.getY_position();
                    distance++;
                }

            }
            //System.out.printf("%d %d\n", originX, originY);
        }

        return distance;
    }

}
