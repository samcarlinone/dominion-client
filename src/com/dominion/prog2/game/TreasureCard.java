package com.dominion.prog2.game;


import com.dominion.prog2.modules.Game;

public class TreasureCard extends Card
{
    private int addCoins;

    /**
     * Constructor for Treasure Card
     *      Child class of Card
     * Finds values using CardInfo, based off the name of the card
     * AddCoins: if card played, this will add coins to the player for that turn
     */
    public TreasureCard(String name)
    {
        super(name);

        int[] vals = CardInfo.getVals(name);
        addCoins = vals[3];
    }

    /**
     *This method plays the card.
     * All the basic variables are used for the player
     * There is only one card (Merchant), which would affect how these cards are played
     */
    public void play(Player p, Game g)
    {
        if(p.playedMerchant && name.equals("Silver"))
        {
            p.playedMerchant = false;
            p.turnMoney += 1;
        }

        p.turnMoney += addCoins;
    }
}
