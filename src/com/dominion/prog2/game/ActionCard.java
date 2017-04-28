package com.dominion.prog2.game;


import com.dominion.prog2.modules.Game;

public class ActionCard extends Card
{
    private int addCoins;
    private int addCard;
    private int addAction;
    private int addBuy;

    /**
     * Constructor for Treasure Card
     * @param name
     */
    public ActionCard(String name)
    {
        super(name);

        int[] vals = CardInfo.getVals(name);
        addCoins = vals[3];
        addAction = vals[4];
        addBuy = vals[5];
        addCard = vals[6];
    }


    /**
     * Plays the card based off the variables of the card
     * @param p player
     * @param g game
     */
    public void play(Player p, Game g)
    {
        p.turnAction += addAction;
        p.turnBuys += addBuy;
        p.turnMoney += addCoins;
        p.pickUpCards(addCard);

        switch(name)
        {
            case "Cellar":
                g.popup = true;
                break;
            case "Chapel":
                break;
            case "Harbinger":
                break;
            case "Merchant":
                break;
            case "Vassal":
                break;
            case "Workshop":
                break;
            case "Moneylender":
                break;
            case "Poacher":
                break;
            case "Remodel":
                break;
            case "Throne Room":
                break;
            case "Council Room":
                break;
            case "Library":
                break;
            case "Mine":
                break;
            case "Sentry":
                break;
            case "Artisan":
                break;
        }
    }

    /**
     * Getter for number of Cards to be picked up
     * @return cards to be picked up (int)
     */
    public int getAddCard() {return addCard;}

    /**
     * Getter for buys added
     * @return buys added (int)
     */
    public int getAddBuy() {return addBuy;}

    /**
     * Getter for Action added
     * @return Action added (int)
     */
    public int getAddAction() {return addAction;}

    /**
     * Getter for the Coin Value
     * @return Coin Value (int)
     */
    public int getAddCoins()
    {
        return addCoins;
    }
}
