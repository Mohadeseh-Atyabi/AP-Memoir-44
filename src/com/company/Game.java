package com.company;

import java.util.*;

/**
 * It is the main class to handle the game.
 */
public class Game extends GameMovements{
    //The board of the game
    private final Board board;
    //List of players
    private final Player[] players;
    //The player whose turn is
    private Player currentPlayer;
    //If the game is finished, it is true
    private boolean finished;
    //List of stored cards
    private ArrayList<Card> storedCards;
    //List of used cards
    private ArrayList<Card> usedCards;
    //List of cells which are selected.The boolean shows whether the piece can battle or not
    private HashMap<Cell,Boolean> currentCells;

    public Game(){
        board = new Board();
        players = new Player[2];
        currentPlayer = new Player(Team.ALLIED);
        finished = false;
        storedCards = new ArrayList<Card>();
        usedCards = new ArrayList<Card>();
        currentCells = new HashMap<Cell,Boolean>();
    }

    /**
     * Gets the players and initializes the game.
     * @param player1 First player
     * @param player2 Second player
     */
    public Player initialize(Player player1, Player player2, Team team){
        //Sets stored cards
        setStoredCards();

        //Sets cards for players
        int num1=2 , num2=4;
        if(player1.getTeam().equals(Team.ALLIED)){
            num1 = 4;
            num2 = 2;
        }

        updateCards(player1,num1);
        players[0] = player1;

        updateCards(player2,num2);
        players[1] = player2;

        //Resets the board of the game
        board.resetBoard();

        //Sets the current player
        if( player1.getTeam().equals(team) ){
            currentPlayer = player1;
        }
        else{
            currentPlayer = player2;
        }
        return currentPlayer;

    }

    /**
     * If the game is finished returns true< else returns false.
     * @return Finished field
     */
    public boolean isFinished(){ return finished; }

    /**
     * Sets the finished state of the game
     */
    private void setFinished(){ this.finished = true; }

    /**
     * It sets the stored cards.
     */
    private void setStoredCards(){
        int num = 0;
        boolean oneUnit = false;

        for(int i=0 ; i<40; i++){
            if( i<6 ){
                num = 1;
            }
            else if( i<19 ){
                num = 2;
            }
            else if( i<29 ){
                num = 3;
            }
            else if( i<35 ){
                num = 4;
            }
            else{
                num = 3;
                oneUnit = true;
            }

            storedCards.add( new Card(num,oneUnit) );
        }
    }

    /**
     * It returns a random generated numbers
     * @param limit Limit of generating
     * @return Random number
     */
    private int generateRandom(int limit){

        Random generate = new Random();
        return generate.nextInt(limit);
    }

    /**
     * Updates the cards of the player.
     * @param player Player want to update cards
     * @param numberOfCards Number of cards want to update
     */
    public void updateCards(Player player, int numberOfCards){
        int result, i=0;
        Card card = new Card(0,false);

        while (i < numberOfCards){

            //If stored cards are finished, uses the used cards instead.
            if (storedCards.size() == 0){
                storedCards.addAll(usedCards);
                usedCards.clear();
            }

            result = generateRandom(storedCards.size());
            card = storedCards.get(result);
            player.addCard( card );
            storedCards.remove(card);

            i++;
        }

    }

    /**
     * It receives the origin block and how to move and makes movement if possible.
     * @param player Who plays the role
     * @param start_x x_position of origin cell
     * @param start_y y_position of origin cell
     * @param description String to say how to move
     * @return True if the movement is done and false if not
     */
    public boolean makeMove(Player player, int start_x, int start_y, String description){
        Cell origin = board.getCell(start_x,start_y);
        Piece startPiece = origin.getPiece();
        int numberOfMoved;

        //When the cell is empty, cannot be used.
        if( startPiece ==null ){
            System.out.println("The origin cell is empty!");
            return false;
        }

        if ( !player.equals(currentPlayer) ){
            System.out.println("It is not your turn");
            return false;
        }

        //If the player wants to move the piece which is not his/her.
        if( !startPiece.getTeam().equals( player.getTeam() ) ){
            System.out.println("This piece is not yours!");
            return false;
        }

        numberOfMoved = startPiece.move(description);
        //Checks whether the number of blocks want to move is accepted or not.
        if( !(numberOfMoved<=startPiece.getToMove()) ){
            System.out.println("This movement is not possible");
            return false;
        }

        //One cell cannot be selected more than one time in each turn.
        if (currentCells.size() != 0){
            for (Map.Entry<Cell,Boolean> it : currentCells.entrySet()){
                if (it.getKey().equals(origin)){
                    System.out.println("This cell was selected previously.");
                    return false;
                }
            }
        }

        //Destination cell on the board
        Cell destination = findDestination(origin, description);
        if (destination==null){
            return false;
        }

        //If the destination cell is not the range.
        if( destination.getType()==null ){
            System.out.println("Invalid input");
            return false;
        }

        //If no movement is asked or in an special condition the movement is not valid.
        if ( destination.equals(origin) ){
            System.out.println("No movement is asked");
            currentCells.put(origin,true);
            return true;
        }

        //Moves the piece in the origin cell into the destination cell.
        destination.setPiece(startPiece, origin.getNumOfPieces());
        origin.setPiece(null,0);

        //Adds the destination cell to the current cell list.
        addToCurrentCell(numberOfMoved,destination);

        //If the player moved into the special cell.
        //If each of the players gain 6 medal, the game is finished.
        if (destination.getX_position() == 11 && destination.getY_position() == 0){
            if (player.getTeam().equals(Team.AXIS)){
                if ( !player.isSpecialCell() ) {
                    System.out.println("You achieved a special cell!");
                    player.addMedal();
                    player.setSpecialCell();
                    if (player.getMedal() == 6) {
                        setFinished();
                    }
                }
            }
        }
        if (destination.getX_position() == 0 && destination.getY_position() == 6){
            if (player.getTeam().equals(Team.ALLIED)){
                if ( !player.isSpecialCell() ) {
                    System.out.println("You achieved a special cell!");
                    player.addMedal();
                    player.setSpecialCell();
                    if (player.getMedal() == 6) {
                        setFinished();
                    }
                }
            }
        }

        System.out.println("Moved successfully");

        return true;
    }

    /**
     * Adds the selected cell to the current cell which should play in this role
     * @param numberOfMove Number of movements the piece made
     * @param destination Destination cell
     */
    public void addToCurrentCell(int numberOfMove, Cell destination){
        //If the destination is forest or city, it cannot battle
        if (destination.getType().equals(Type.FOREST) || destination.getType().equals(Type.CITY)){
            currentCells.put(destination,false);
            return;
        }

        //Sets the current cell and determine whether it can battle or not
        switch (destination.getPiece().toString()){
            case "Infantry":
                if(numberOfMove == 2){
                    currentCells.put(destination,false);
                }
                else{
                    currentCells.put(destination,true);
                }
                break;

            case "Tank":
                currentCells.put(destination,true);
                break;

            case "Artillery":
                if(numberOfMove == 1){
                    currentCells.put(destination,false);
                }
                else{
                    currentCells.put(destination,true);
                }
                break;
        }
    }

    /**
     * It returns the destination cell if the movement is valid
     * @param origin Origin cell
     * @param description Guide how to move
     * @return Destination cell
     */
    public Cell findDestination(Cell origin, String description){
        String[] parts = description.split(" ");
        int destX = origin.getX_position() ;
        int destY = origin.getY_position();
        int prevX = origin.getX_position(), prevY = origin.getY_position();
        Cell result ;

        for(int i=0 ; i<parts.length ; i++){
            int number = 0, j=0;

            StringBuilder temp = new StringBuilder();

            for(Character c : parts[i].toCharArray()){
                if( Character.isDigit(c) ){
                    temp.append(c);
                }
            }
            //If do not want to move the piece
            if(temp.toString().length()==0){
                if (!parts[i].toUpperCase().equals("NO")){
                    System.out.println("Invalid input");
                    return null;
                }
                return origin;
            }

            //Number of movements the player asked.
            number += Integer.parseInt(temp.toString());

            parts[i] = parts[i].replaceAll(temp.toString(),"");

            while ( j<number ) {

                result = displace(destX,destY,parts[i]);
                destX = result.getX_position();
                destY = result.getY_position();

                //Checks whether we are out of the range or not.
                if( destY>=0 && destY<9 ) {
                    if (destY % 2 == 1) {
                        if (destX < 0 || destX > 11) {
                            System.out.println("Invalid input");
                            return null;
                        }
                    }
                    else {
                        if(destX < 0 || destX > 12){
                            System.out.println("Invalid input");
                            return null;
                        }
                    }
                }
                else{
                    System.out.println("Invalid input");
                    return null;
                }

                //Tank and Artillery can not pass bunker cell.
                if (destX == 4 && destY == 7) {
                    if (origin.getPiece().toString().equals("Tank") || origin.getPiece().toString().equals("Artillery")) {
                        System.out.println("You cannot pass this cell");
                        return board.getCell(prevX,prevY);
                    }
                }

                //No one can pass the river.
                if (board.getCell(destX, destY).getType().equals(Type.RIVER)) {
                    System.out.println("You cannot pass the river");
                    return board.getCell(prevX,prevY);
                }

                //When enter the forest or city, must stay there and cannot battle.
                if (board.getCell(destX, destY).getType().equals(Type.FOREST) || board.getCell(destX, destY).getType().equals(Type.CITY)) {
                    System.out.println("You are in the forest and cannot move anymore");
                    return board.getCell(destX, destY);
                }

                //Checks whether the pass is blocked or not.
                if( board.getCell(destX,destY).getPiece()!=null ){
                    System.out.println("The path is blocked");
                    return board.getCell(prevX,prevY);
                }
                prevX = destX;
                prevY = destY;

                j++;
            }
        }

        return board.getCell(destX,destY);
    }

    /**
     * Represents the process of attacking to one unit.
     * @param originX x_position of origin cell
     * @param originY y_position of origin cell
     * @param destX x_position of destination cell
     * @param destY y_position of destination cell
     */
    public void attack(int originX, int originY, int destX, int destY){
        Cell destination = board.getCell(destX,destY);
        Cell origin = board.getCell(originX,originY);

        //If the destination cell is empty, returns
        if (destination.getPiece()==null){
            System.out.println("No enemy is here");
            return;
        }

        //Checks whether the attack to your teammate or nor.
        if (destination.getPiece().getTeam().equals(origin.getPiece().getTeam())){
            System.out.println("You cannot attack to your teammate");
            return;
        }

        int numberOfDice = 0;
        int distance = displaceToAttack(originX, originY, destX, destY);

        //Sets the number of dices.
        switch (origin.getPiece().toString()) {
            case "Infantry" -> {
                if (distance > 3) {
                    System.out.println("Infantries can attack 3 cells far away.");
                    return;
                }
                switch (distance) {
                    case 1 -> numberOfDice = 3;
                    case 2 -> numberOfDice = 2;
                    case 3 -> numberOfDice = 1;
                }
            }
            case "Tank" -> {
                if (distance > 3) {
                    System.out.println("Infantries can attack 3 cells far away.");
                    return;
                }
                numberOfDice = 3;
            }
            case "Artillery" -> {
                if (distance > 6) {
                    System.out.println("Infantries can attack 3 cells far away.");
                    return;
                }
                switch (distance) {
                    case 1, 2 -> numberOfDice = 3;
                    case 3, 4 -> numberOfDice = 4;
                    case 5, 6 -> numberOfDice = 1;
                }
            }
        }

        //Sets the limitation of cells according to their type
        switch (destination.getType()){
            case HILL -> {
                if (origin.getPiece().toString().equals("Tank") || origin.getPiece().toString().equals("Artillery")){
                    numberOfDice --;
                }
            }

            case FOREST, CITY -> {
                if (origin.getPiece().toString().equals("Tank")){
                    numberOfDice -= 2;
                }
                else if (origin.getPiece().toString().equals("Infantry")){
                    numberOfDice --;
                }
            }

            case BUNKER -> {
                if (destination.getPiece().getTeam().equals(Team.AXIS)){

                    if (origin.getPiece().toString().equals("Tank")){
                        numberOfDice -= 2;
                    }
                    else if (origin.getPiece().toString().equals("Infantry")){
                        numberOfDice --;
                    }

                }
            }
        }

        //Tank in city dices 2 times less to attack
        if (origin.getPiece().toString().equals("Tank")){
            if (origin.getType().equals(Type.CITY)){
                numberOfDice -= 2;
            }
        }

        //If the number of dice is not more then 0, the method will be done.
        if (numberOfDice <= 0){
            System.out.println("You cannot dice this turn");
            return;
        }

        ArrayList<Integer> diceResult = new ArrayList<Integer>();
        int i = 0;
        while (i < numberOfDice){
            diceResult.add( generateRandom(6) );
            i++;
        }

        i = 1;
        for (Integer it : diceResult){
            it++;
            System.out.print("dice" + i + ": " + it + "; ");
            i++;
        }
        System.out.println();

        //Check the results of the dice and exterminate them if possible
        for (Integer it : diceResult){
            switch (it){
                case 0 , 5:
                    if (destination.getPiece().toString().equals("Infantry")){
                        System.out.println("You killed one infantry.");
                        destination.setNumOfPieces(destination.getNumOfPieces() - 1);
                    }
                    break;
                case 1:
                    if (destination.getPiece().toString().equals("Tank")){
                        System.out.println("You exterminated one tank.");
                        destination.setNumOfPieces(destination.getNumOfPieces() - 1);
                    }
                    break;
                case 2 , 3:
                    break;
                case 4:
                    System.out.println("You killed one " + destination.getPiece().toString());
                    destination.setNumOfPieces(destination.getNumOfPieces() - 1);
                    break;

            }
        }

        //Checks whether the destination if free of pieces or not
        if (destination.getNumOfPieces() <= 0){
            System.out.println("The hole group is exterminated");
            destination.setPiece(null,0);
            currentPlayer.addMedal();
            if (currentPlayer.getMedal() == 6){
                setFinished();
            }
        }
    }

    /**
     * When the player choose a card, adds it to used cards.
     * @param index Index of the card to use
     */
    public Card chooseCard(int index){

        Card toRemove = currentPlayer.getCard(index);
        usedCards.add(toRemove);
        storedCards.remove(toRemove);

        updateCards(currentPlayer,1);

        return toRemove;
    }

    /**
     * Prints the board of the game
     */
    public void printBoard(){ board.print(); }

    /**
     * Changes the current player
     * @return Player
     */
    public Player changeTurn(){
        if (currentPlayer.equals(players[0])){
            currentPlayer = players[1];
        }
        else{
            currentPlayer = players[0];
        }
        return currentPlayer;
    }

    /**
     * Returns the name of the piece in this cell
     * @param xPosition Position on x_axis
     * @param yPosition Position on y_axis
     * @return Name of the piece
     */
    public String getPieceName(int xPosition, int yPosition){
        return board.getCell(xPosition,yPosition).getPiece().toString();
    }

    /**
     * Gets the list of cells which are chosen in this turn
     * @return Current cells
     */
    public HashMap<Cell,Boolean> getCurrentCells(){ return currentCells; }
}



