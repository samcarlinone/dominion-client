package com.dominion.prog2.game;


import com.dominion.prog2.modules.Game;

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

    public void play(Player p, Game g)
{
    p.turnMoney += addCoins;
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
