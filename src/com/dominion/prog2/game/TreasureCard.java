package com.dominion.prog2.game;


public class TreasureCard extends Card
{
    private int addCoins;

    /**
     * Constructor for Treasure Card
     * @param name
     */
    public TreasureCard(String name)
    {
        super(name);

        int[] vals = CardInfo.getVals(name);
        addCoins = vals[3];
    }

    /**
     * Getter for the Coin Value
     * @return Coin Value (int)
     */
    public int getAddCoins()
    {
        return addCoins;
    }
}
