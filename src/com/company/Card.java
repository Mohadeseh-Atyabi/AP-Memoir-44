package com.company;

/**
 * Saves the number of groups it can move
 */
public class Card {
    //Number of group to move
    private int number;
    //If it works for only one unit
    private boolean oneUnit;

    public Card(int number, boolean oneUnit){

        this.number = number;
        this.oneUnit = oneUnit;
    }

    /**
     * Reurns the number
     * @return Number
     */
    public int getNumber(){ return number; }

    /**
     * Returns oneUnit field
     * @return oneUnit field
     */
    public boolean isOneUnit(){ return oneUnit; }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Order ");
        builder.append(number);

        if(number==1){
            builder.append(" unit");
        }
        else
            builder.append(" units");

        if(oneUnit){
            builder.append(" of the same group");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ( !(obj instanceof Card) ){
            return false;
        }
        Card other = (Card) obj;
        return this.oneUnit == other.oneUnit &&
                this.getNumber() == other.getNumber();
    }

    @Override
    public int hashCode(){
        int res = 17;
        res = 37*res + number;
        res = 37*res + (oneUnit ? 1 : 0);
        return res;
    }
}
