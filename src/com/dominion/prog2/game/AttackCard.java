package com.dominion.prog2.game;

import com.dominion.prog2.modules.Game;

public class AttackCard extends ActionCard
{
    private int addCoins;
    private int addCard;

    /**
     * Constructor for Attack Card
     *      Child class of Action Card
     * Finds values using CardInfo, based off the name of the card
     * AddCoins: if card played, this will add coins to the player for that turn
     * AddCard: if card played, the player will pick up the specific amount of cards
     */
    public AttackCard(String name)
    {
        super(name);

        int[] vals = CardInfo.getVals(name);
        addCoins = vals[3];
        addCard = vals[6];
    }

    /**
     * This method plays the card.
     * All the basic variables are used for the player
     * Since many of the Attack Cards have extra actions based off the card,
     *      there are specific instructions per those cards
     * Attack cards are mostly handled in the Game, since the other players are affected.
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
                if(g.shoppe.has("Gold"))
                    p.discard.add(g.shoppe.remove("Gold"));
                break;
            case "Witch":
                //Nothing
                break;
        }
    }
}
