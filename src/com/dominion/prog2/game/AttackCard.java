package com.dominion.prog2.game;

import com.dominion.prog2.modules.Game;

import java.util.HashMap;

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
                if(g.getShoppe().has("Silver")) {
                    //Broadcast silver taken
                    HashMap<String, String> buy = new HashMap<>();
                    buy.put("type", "gained");
                    buy.put("player", p.name);
                    buy.put("cardName", "Silver");
                    g.getDriver().broadcast(buy);

                    p.deck.addTop(g.getShoppe().get("Silver"));
                }
                break;
            case "Militia":
                //Nothing
                break;
            case "Bandit":
                if(g.getShoppe().has("Gold"))
                    g.gainCard("Gold");
                break;
            case "Witch":
                g.getShoppe().remove("Curse");
                break;
        }
    }
}
