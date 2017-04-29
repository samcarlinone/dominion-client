package com.dominion.prog2.game;


import com.dominion.prog2.modules.Game;

public class AttackCard extends Card
{
    private int addCoins;
    private int addCard;

    /**
     * Constructor for Treasure Card
     * @param name
     */
    public AttackCard(String name)
    {
        super(name);

        int[] vals = CardInfo.getVals(name);
        addCoins = vals[3];
        addCard = vals[6];
    }

    /**
     * Plays the card based off the variables of the card
     * @param p player
     * @param g game
     */
    public void play(Player p, Game g)
    {
        switch(name)
        {
            case "Bureaucrat":

                break;
            case "Militia":

                break;
            case "Bandit":

                break;
            case "Witch":

                break;
        }
    }


    /**
     * Getter for number of Cards to be picked up
     * @return cards to be picked up (int)
     */
    public int getAddCard() {   return addCard;
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
