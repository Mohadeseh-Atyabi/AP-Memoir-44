package com.company;

/**
 * It represents the board of the game.
 * It's cells are hexagonal.
 */
public class Board extends PrintCell {
    //List of the cells in the board.
    private Cell[][] cells;

    public Board(){
        cells = new Cell[13][9];
    }

    /**
     * It resets the board to start the game.
     */
    public void resetBoard(){
        for (int i=0 ; i<9 ; i++){
            for(int j=0 ; j<13 ; j++){
                cells[j][i] = new Cell(j,i);
            }
        }

        //set types
        cells[3][1].setType(Type.FOREST);
        cells[4][1].setType(Type.FOREST);
        cells[8][1].setType(Type.FOREST);
        cells[7][2].setType(Type.FOREST);
        cells[8][2].setType(Type.FOREST);
        cells[3][3].setType(Type.FOREST);
        cells[11][3].setType(Type.FOREST);
        cells[1][4].setType(Type.FOREST);
        cells[8][4].setType(Type.FOREST);
        cells[12][4].setType(Type.FOREST);
        cells[1][5].setType(Type.FOREST);
        cells[3][5].setType(Type.FOREST);
        cells[11][5].setType(Type.FOREST);
        cells[9][6].setType(Type.FOREST);
        cells[12][6].setType(Type.FOREST);
        cells[4][3].setType(Type.HILL);
        cells[10][3].setType(Type.HILL);
        cells[5][4].setType(Type.HILL);
        cells[11][4].setType(Type.HILL);
        cells[6][6].setType(Type.HILL);
        cells[6][7].setType(Type.HILL);
        cells[0][8].setType(Type.HILL);
        cells[1][8].setType(Type.HILL);
        cells[11][0].setType(Type.SPECIAL);
        cells[2][2].setType(Type.CITY);
        cells[6][4].setType(Type.CITY);
        cells[10][4].setType(Type.CITY);
        cells[0][6].setType(Type.SPECIAL);
        cells[3][8].setType(Type.CITY);
        cells[4][7].setType(Type.BUNKER);
        cells[0][4].setType(Type.RIVER);
        cells[1][6].setType(Type.RIVER);
        cells[1][7].setType(Type.RIVER);
        cells[2][7].setType(Type.RIVER);
        cells[3][7].setType(Type.RIVER);
        cells[4][8].setType(Type.BRIDGE);
        cells[0][5].setType(Type.BRIDGE);

        for(int i=0 ; i<9 ; i++){
            for(int j=0 ; j<13 ; j++){
                if(i%2==1 && j==12){
                    continue;
                }
                if(cells[j][i].getType()==null)
                    cells[j][i].setType(Type.NORMAL);
            }
        }

        setPieces();

        /*for(int i=8 ; i>=0 ; i--){
            for(int j=0 ; j<13 ; j++){

                System.out.print(cells[j][i].getPiece());
                System.out.print("  ");
            }
            System.out.println();
        }*/
        
    }

    public void setPieces(){
        //Allied tanks
        cells[0][0].setPiece(new Tank(Team.ALLIED) , 3);
        cells[1][0].setPiece(new Tank(Team.ALLIED) , 3);
        cells[12][0].setPiece(new Tank(Team.ALLIED) ,3);
        //Allied infantries
        cells[8][0].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[0][1].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[9][1].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[7][2].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[3][3].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[1][4].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[6][4].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[8][4].setPiece(new Infantry(Team.ALLIED) , 4);
        cells[11][4].setPiece(new Infantry(Team.ALLIED) , 4);
        //Allied artillery
        cells[1][1].setPiece(new Artillery(Team.ALLIED) ,2);
        cells[5][1].setPiece(new Artillery(Team.ALLIED) ,2);

        //Axis tanks
        cells[0][8].setPiece(new Tank(Team.AXIS) , 4);
        cells[5][8].setPiece(new Tank(Team.AXIS) , 4);
        cells[8][8].setPiece(new Tank(Team.AXIS) , 4);
        cells[11][8].setPiece(new Tank(Team.AXIS) , 4);
        cells[5][7].setPiece(new Tank(Team.AXIS) , 4);
        cells[10][7].setPiece(new Tank(Team.AXIS) , 4);
        //Axis infantries
        cells[1][8].setPiece(new Infantry(Team.AXIS) , 4);
        cells[2][8].setPiece(new Infantry(Team.AXIS) , 4);
        cells[7][8].setPiece(new Infantry(Team.AXIS) , 4);
        cells[10][8].setPiece(new Infantry(Team.AXIS) , 4);
        cells[12][8].setPiece(new Infantry(Team.AXIS) , 4);
        cells[4][7].setPiece(new Infantry(Team.AXIS) , 4);
        cells[8][7].setPiece(new Infantry(Team.AXIS) , 4);

    }

    /**
     * Returns the cell which x and y is given
     * @param x Position on x_axis
     * @param y Position on y_axis
     * @return Cell
     */
    public Cell getCell(int x, int y){
        if( x<0 || x>13 || y<0 || y>9 ){
            System.out.println("Index is not in the range!");
            return null;
        }
        return cells[x][y];
    }

    public void print(){
        int i=0, shift =0;
        int row=8 , limit=13;
        String background;
        String whatToWrite;

        while (row >= 0) {
            limit = 13;

            if (row%2 == 1){
                System.out.print("    ");
                limit = 12;
            }
            for (i = 0; i < limit; i++) {
                background = setBackground(cells[i][row]);
                System.out.print(background + BLACK_BOLD + " ——————— " + TEXT_RESET);
            }
            System.out.print("\n");

            if (row%2 == 1){
                System.out.print("    ");
            }
            for (i = 0; i < limit; i++) {
                background = setBackground(cells[i][row]);
                if (cells[i][row].getPiece() != null){
                    whatToWrite = setPieceToPrint(cells[i][row]);
                    System.out.print(background + VERTICAL + " " + whatToWrite + background + " " + VERTICAL + TEXT_RESET);
                }
                else
                    System.out.print(background + VERTICAL + "       " + VERTICAL + TEXT_RESET);
            }
            System.out.print("\n");

            if (row%2 == 1){
                System.out.print("    ");
            }
            for (i = 0; i < limit; i++) {
                background = setBackground(cells[i][row]);
                System.out.print(background + BLACK_BOLD + " ——————— " + TEXT_RESET);
            }
            System.out.print("\n");

            row--;

        }

    }
}
