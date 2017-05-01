package com.dominion.prog2.game;

import com.dominion.prog2.modules.Game;

public class ReactionCard extends ActionCard
{

    /**
     * Constructor for Reaction Card
     *      Child class of ActionCard
     */
    public ReactionCard(String name)
    {
        super(name);
    }


    /**
     *  This method plays the card.
     *  This card blocks any attacks (handled within Game)
     *  Also the player will pick up 2 cards
     */
    @Override
    public void play(Player p, Game g)
    {
        switch(name)
        {
            case "Moat":
                p.pickUpCards(2);
                break;
        }
    }
}
