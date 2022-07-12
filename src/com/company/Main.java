package com.company;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Game game = new Game();
		Player p1;
		Player p2;
		Player currentPlayer;

	    boolean run = true;
	    String input;
		Scanner myObj = new Scanner(System.in);

		System.out.println("Welcome to Memoir'44 game.");
		System.out.println("Here is a menu. You can read about the game or start playing!");

	    while ( run ){
	    	System.out.println("1) Game guide");
	    	System.out.println("2) Start playing");
	    	System.out.print(">> ");
	    	input = myObj.nextLine();

			switch (input) {
				case "1" -> {
					System.out.println(">> In this game when you are asked to enter the x and y position of the cell, " +
							"you have to enter them in the form of 'x y' in onr line.");
					System.out.println(">> When you want to choose the cards, you have to enter the number of the card " +
							"which is shown in the list.");
					System.out.println(">> When you do not want to move or attach, enter 'No'.");
					System.out.println(">> When you ask for a movement or attack, the answer to that request is written.");
					System.out.println(">> Hope enjoy it :)");
					run = false;
				}
				case "2" -> run = false;
			}
		}

	    System.out.println("Enter the team of the first player.");
	    System.out.print(">> ");
	    input = myObj.nextLine();
	    p1 = new Player( Team.valueOf(input.toUpperCase()) );

		System.out.println("Enter the team of the second player.");
		System.out.print(">> ");
		input = myObj.nextLine();
		p2 = new Player( Team.valueOf(input.toUpperCase()) );

		System.out.println("Which team will start the game?");
		System.out.print(">> ");
		input = myObj.nextLine();

		currentPlayer = game.initialize(p1, p2, Team.valueOf(input.toUpperCase()));

		//Playing game
	    while ( !game.isFinished() ){

			game.printBoard();
	    	System.out.println("It is " + currentPlayer.getTeam().toString() + "s turn!");

	    	//First step: print cards
			System.out.println("Your cards are here:");
			currentPlayer.printCards();

			//Second step: choose one card
			System.out.println("Enter the number of card you want to play.");
			System.out.print(">> ");
			input = myObj.nextLine();
			Card cardToPlay = game.chooseCard( Integer.parseInt(input) );

			//Third step: move pieces
			System.out.print("Now choose your groups one by one and move them. ");
			System.out.println("If do not want to move them, enter No.");

			int i = 1;
			String pieceName = null;
			while ( i <= cardToPlay.getNumber() ){

				//Gets x and y position of the cell
				System.out.println("Choice " + i + ": ");
				System.out.println("Enter x_position and y_position of origin cell");
				System.out.print(">> ");
				input = myObj.nextLine();

				//Separates x and y positions.
				String[] originPositions = input.split(" ");

				//If the card orders one unit, checks whether the input is valid or not.
				if (cardToPlay.isOneUnit()){
					if (i == 1){
						pieceName = game.getPieceName(Integer.parseInt(originPositions[0]) ,Integer.parseInt(originPositions[1]));
					}
					else if ( !game.getPieceName(Integer.parseInt(originPositions[0]), Integer.parseInt(originPositions[1]) ).equals(pieceName) ){
							System.out.println("You should choose the same type groups");
							continue;
					}
				}

				//Gets the description how to move.
				System.out.println("Enter how to move?");
				System.out.print(">> ");
				input = myObj.nextLine();

				if ( !game.makeMove(currentPlayer, Integer.parseInt(originPositions[0]), Integer.parseInt(originPositions[1]), input) ){
					continue;
				}

				i++;
			}
			game.printBoard();

			//Fourth step: battle
			System.out.println("The list of chosen cells will be printed one by one and you can " +
					"decide whether to attack or not!");

			int counter=1;
			for (Map.Entry<Cell, Boolean> temp : game.getCurrentCells().entrySet()) {
				Cell tempCell = temp.getKey();

				//Print the cell dimensions
				System.out.println("Choice " + counter + " : (" + tempCell.getX_position() + "," + tempCell.getY_position() + ")");

				//If the key is false, this group cannot attack.
				if (!temp.getValue()) {
					System.out.println("You cannot attack with this group.");
					counter++;
					continue;
				}

				System.out.println("Enter the x and y position of the cell you wanna attack");
				System.out.print(">> ");
				input = myObj.nextLine();

				//Check if the player does not want to attack
				if (input.toUpperCase().equals("NO")) {
					System.out.println("No attack is asked");
					continue;
				}

				String[] attackPosition = input.split(" ");
				//Attack to the received cell
				game.attack(tempCell.getX_position(), tempCell.getY_position(), Integer.parseInt(attackPosition[0]),
						Integer.parseInt(attackPosition[1]));

				counter++;
			}

			game.getCurrentCells().clear();

			//Prints the results up to now.
			if (game.isFinished()){
				System.out.println(currentPlayer.getTeam() + " won!");
			}
			System.out.print("Scores: ");
			System.out.print(p1.getTeam() + "=" + p1.getMedal() + " ");
			System.out.println(p2.getTeam() + "=" + p2.getMedal());

			currentPlayer = game.changeTurn();
		}

    }
}
