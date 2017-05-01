package com.dominion.prog2.game;


import com.dominion.prog2.modules.Game;

public class ReactionCard extends ActionCard
{
    public ReactionCard(String name)
    {
        super(name);
    }

    /**
     * Plays the card based off the variables of the card and the card
     * @param p player
     * @param g game
     */
    @Override
    public void play(Player p, Game g)
    {
        switch(name)
        {
            case "moat":
                //TODO: Check working?
                p.pickUpCards(2);
                break;
        }
    }
}
