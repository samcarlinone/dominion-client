package com.dominion.prog2.game;

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

    public Card(Card c) {
        this(c.getName());
    }

    public void play(Player p, Game g) {

    }

    public String getName() {
        return name;
    }

    public CardType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getVictoryValue() { return victoryValue; }
}
