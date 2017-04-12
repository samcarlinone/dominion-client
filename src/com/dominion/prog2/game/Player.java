package com.dominion.prog2.game;

public class Player
{
    public CardStack deck;
    public CardStack hand;
    public CardStack discard;
    public String name;

    public int turnBuys;
    public int turnAction;
    public int turnMoney;

    //Todo: figure out what methods we want to include

    /**
     * Creates a new Player
     * @param name ID of Player
     */
    public Player(String name)
    {
        this.name = name;
        deck = new CardStack();
        hand = new CardStack();
        discard = new CardStack();

        //Initializes Deck
        for(int i = 0; i < 7; i ++)
            deck.add(new Card("Copper"));
        for(int i = 0; i < 3; i ++)
            deck.add(new Card("Estate"));

        nextTurn();
    }

    /**
     * Resets the player so they are ready for their turn
     */
    public void nextTurn()
    {
        turnBuys = 1;
        turnAction = 1;
        turnMoney = 0;

        pickUpCards(5);
    }

    public void pickUpCards(int numCards)
    {
        int deckSize = deck.size();

        if(deckSize >= numCards)
            hand.add(deck.splice(0,numCards));
        else
        {
            discard.shuffle();
            deck.add(discard.getAll());
            discard.clear();

            hand.add(deck.splice(0,numCards));
        }
    }

}
