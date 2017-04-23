package com.dominion.prog2.game;


public class Card
{
    protected String name;
    protected CardType type;
    protected int price;

    /**
     * Creates a card and adds values based off the card name
     * @param name
     */
    public Card(String name) {
        this.name = name;

        int[] vals = CardInfo.getVals(name);

        type = CardType.values()[vals[0]];
        price = vals[1];
    }

    /**
     * Deep copy of a card
     * @param c card to be copied
     */
    public Card(Card c) {
        this(c.getName());
    }

    /**
     * getter of name
     * @return the name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * getter of the type of card
     * @return card type based off the enum
     */
    public CardType getType() {
        return type;
    }

    /**
     * getter of the price of the card
     * @return price (int)
     */
    public int getPrice() {
        return price;
    }

}
