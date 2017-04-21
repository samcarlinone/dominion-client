package com.dominion.prog2.game;

//import com.dominion.prog2.modules.Game;

public class Card
{
    private String name;
    private CardType type;
    private int price;
    private int victoryValue;
    private int addCoins;
    private int addAction;
    private int addBuy;
    private int addCard;

    /**
     * Creates a card and adds values based off the card name
     * @param name
     */
    public Card(String name) {
        this.name = name;

        int[] vals = CardInfo.getVals(name);

        type = CardType.values()[vals[0]];
        price = vals[1];
        victoryValue = vals[2];
        addCoins = vals[3];
        addAction = vals[4];
        addBuy = vals[5];
        addCard = vals[6];
    }

    /**
     * Deep copy of a card
     * @param c card to be copied
     */
    public Card(Card c) {
        this(c.getName());
    }

    /**
     * Plays the card based off the variables of the card
     * @param p player
     * @param g game
     */
    //public void play(Player p, Game g) {

    //}

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

    /**
     * getter of victory value
     * only cards with type victory
     * @return value (int)
     */
    public int getVictoryValue() { return victoryValue; }
}
