package com.dominion.prog2.game;


public class VictoryCard extends Card
{
    private int victoryValue;

    /**
     * Constructor for Victory Card
     * @param name
     */
    public VictoryCard(String name) {
        super(name);

        int[] vals = CardInfo.getVals(name);
        victoryValue = vals[2];
    }

    /**
     * Getter for the Victory Value
     * @return Victory Value (int)
     */
    public int getVictoryValue(int totalCards)
    {
        if(name.equals("Curse"))
            return -1;
        else if(name.equals("Garden"))
            return totalCards/10;
        else
            return victoryValue;
    }

}
