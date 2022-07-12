package com.company;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the player ofthe game.
 */
public class Player {
    //The team of the player
    private Team team;
    //The cards of the player
    private ArrayList<Card> cards;
    //Number of medals achieved
    private int medal;
    //Checks if the special cell is achieved once, do not get medal fo it anymore
    private boolean specialCell;

    public Player(Team team){
        this.team = team;
        medal = 0;
        cards = new ArrayList<Card>();
        specialCell = false;
    }

    /**
     * Sets the special cell as true.
     */
    public void setSpecialCell(){ this.specialCell = true; }

    /**
     * Gets the special cell.
     * @return Special cell
     */
    public boolean isSpecialCell(){ return specialCell; }

    /**
     * Returns the team of the player
     * @return Team of the player
     */
    public Team getTeam(){ return team; }

    /**
     * Adds a card to the list of cards
     * @param card Card to add
     */
    public void addCard(Card card){ cards.add(card); }

    /**
     * Prints the cards of the player
     */
    public void printCards(){
        int i=1;
        Iterator<Card> it = cards.iterator();
        while ( it.hasNext() ){
            System.out.printf("Card %d: " , i);
            System.out.println( it.next().toString() );
            i++;
        }
    }

    /**
     * Returns the card you asked and remove it from the list
     * @param index Index of the card to get
     * @return Asked card
     */
    public Card getCard(int index){

        if(index<1 || index>cards.size()){
            System.out.println("Invalid index for the card");
            return null;
        }

        Card toReturn = cards.get(index-1);
        cards.remove(toReturn);
        return toReturn;
    }

    /**
     * It adds one unit to the medal of this player
     */
    public void addMedal(){ medal++; }

    /**
     * Returns the number of medals
     * @return Number of medals
     */
    public int getMedal(){ return medal; }
}
