package com.dominion.prog2.game;


public class Card
{
    protected String name;
    protected CardType type;
    protected int price;

    /**
     * Constructor for Card
     *      Parent class of Action Card, Treasure Card, and Victory Card
     * Finds values using CardInfo, based off the name of the card
     * Price: This is the price of the card, set through CardInfo
     *      Used for buying cards, or trading cards
     * Type: This is the CardType. Victory, Treasure, Action, Reaction, Attack
     */
    public Card(String name) {
        this.name = name;

        int[] vals = CardInfo.getVals(name);

        type = CardType.values()[vals[0]];
        price = vals[1];
    }

    /**
     * Deep copy of a card
     */
    public Card(Card c) {
        this(c.getName());
    }

    /**
     * Getter of the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the type of card
     * Victory, Treasure, Action, Reaction or Attack
     */
    public CardType getType() {
        return type;
    }

    /**
     * Getter for the price of the card
     */
    public int getPrice() {
        return price;
    }

}
