package com.dominion.prog2.game;

import com.dominion.prog2.modules.Game;

public class AttackCard extends ActionCard
{
    private int addCoins;
    private int addCard;

    /**
     * Constructor for Attack Card
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
        p.turnMoney += addCoins;
        p.pickUpCards(addCard);

        switch(name)
        {
            case "Bureaucrat":
                if(g.shoppe.has("Silver"))
                    p.discard.add(g.shoppe.remove("Silver"));
                break;
            case "Militia":
                //Nothing
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
    public int getAddCard() { return addCard; }

    /**
     * Getter for the Coin Value
     * @return Coin Value (int)
     */
    public int getAddCoins()
    {
        return addCoins;
    }
}
