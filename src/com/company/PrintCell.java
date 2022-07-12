package com.company;

/**
 * It has methods to print each cell on the board according to it's pieces and
   it's type.
 */
public class PrintCell {

    //Codes to print the cells with different colors.
    public static final String ANSI_BRIGHT_BG_GREEN  = "\u001B[102m";
    public static final String ANSI_BRIGHT_BG_WHITE  = "\u001B[107m";
    public static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
    public static final String ANSI_BRIGHT_BG_CYAN = "\u001B[106m";

    public static final String ANSI_BG_GREEN  = "\u001B[48;2;57;181;74m";
    public static final String ANSI_BG_YELLOW = "\u001B[48;2;252;127;0m";
    public static final String ANSI_BRIGHT_BG_PURPLE = "\u001B[48;2;214;112;214m";

    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";

    public static final String VERTICAL =  "\u2502";
    public static final String TEXT_RESET = "\u001B[0m";

    /**
     * Returns proper background color according to type of the cell
     * @param cell Cell
     * @return Background color
     */
    public String setBackground(Cell cell){
        if (cell.getType()==null){
            return ANSI_BRIGHT_BG_WHITE;
        }

        switch (cell.getType()){
            case FOREST -> {
                return ANSI_BG_GREEN;
            }
            case RIVER -> {
                return ANSI_BRIGHT_BG_CYAN;
            }
            case CITY , SPECIAL-> {
                return ANSI_BRIGHT_BG_YELLOW;
            }
            case BUNKER -> {
                return ANSI_BRIGHT_BG_PURPLE;
            }
            case HILL -> {
                return ANSI_BRIGHT_BG_GREEN;
            }
            case BRIDGE -> {
                return ANSI_BG_YELLOW;
            }
        }
        return ANSI_BRIGHT_BG_WHITE;
    }

    /**
     * Sets what to write for each piece
     * @param cell Cell
     * @return What to write
     */
    public String setPieceToPrint(Cell cell){
        String teamColor;

        //Sets the color of each team
        if (cell.getPiece().getTeam().equals(Team.ALLIED)){
            teamColor = RED_BOLD;
        }else {
            teamColor = BLACK_BOLD;
        }

        switch (cell.getPiece().toString()){
            case "Tank":
                return teamColor + cell.getNumOfPieces() + "TANK" + TEXT_RESET;

            case "Infantry":
                return teamColor + cell.getNumOfPieces() + "INF " + TEXT_RESET;

            case "Artillery":
                return teamColor + cell.getNumOfPieces() + "ART " + TEXT_RESET;

        }
        return null;
    }

}
