package com.dominion.prog2.game;


public class VictoryCard extends Card
{
    private int victoryValue;

    /**
     * Constructor for Victory Card
     *      Child class of Card
     * Finds values using CardInfo, based off the name of the card
     * VictoryValue: At the end of the game, these cards will be used to find the final scores
     */
    public VictoryCard(String name) {
        super(name);

        int[] vals = CardInfo.getVals(name);
        victoryValue = vals[2];
    }

    /**
     * Getter for the Victory Score
     * Most cards are straight forward with their victory value
     * The Garden Card has a victory value based off the number of cards the Player has
     *      The Garden counts as 1 Victory Value for every 10 cards (rounding down)
     */
    public int getVictoryValue(int totalCards)
    {
        if(name.equals("Garden"))
            return totalCards/10;
        else
            return victoryValue;
    }

}
