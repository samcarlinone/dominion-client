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

        //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
        switch(name)
        {
            case "Cellar":
                g.selectCards("Choose cards to discard", p.hand,
                        ((stack, game) -> {
                            Player you = game.getYou();
                            you.pickUpCards(stack.size());
                            game.getYou().discard.add(stack.getAll());
                        }),
                        ((stack, game) -> true));
                break;
            case "Chapel":
                g.selectCards("Choose cards to Trash",p.hand,
                        ((stack,game)-> {
                            Player you = game.getYou();
                            for(Card c: stack)
                                you.hand.remove(c);

                        }),
                        ((stack,game)-> true));
                break;
            case "Harbinger":
                g.selectCards("Choose a card from Discard",p.discard,
                        ((stack,game)-> {
                            Player you = game.getYou();
                            if(stack.size() > 0) {
                                you.discard.remove(stack.get(0));
                                you.deck.addTop(stack.get(0));
                            }
                        }),
                        ((stack,game)-> stack.size() <= 1));
                break;
            case "Merchant":
                //TODO: First time u play a silver this turn, +1 Money
                break;
            case "Vassal":
                if(p.deck.getPosAsStackIfAttack(0).size() > 0)
                g.selectCards("Play this card?",p.deck.getPosAsStackIfAttack(0),
                        ((stack,game)-> {
                            Player you = game.getYou();
                            you.turnAction += 1;
                            //TODO: Play the Card
                        }),
                        ((stack,game)-> true));
                break;
            case "Workshop":
                g.selectCards("Gain one of these Cards",g.getShoppe().filterPrice(4),
                        ((stack,game)-> {
                            Player you = game.getYou();
                            you.discard.add(stack.get(0));
                            game.getShoppe().remove(stack.get(0).getName());
                        }),
                        ((stack,game)-> stack.size() <= 1));
                break;
            case "Moneylender":
                g.selectCards("Trash a Copper?",p.hand.getStackOfName("Copper"),
                        ((stack,game)-> {
                            if(stack.size() > 0)
                            {
                                Player you = game.getYou();
                                you.hand.remove("Copper");
                                you.turnMoney += 3;
                            }
                        }),
                        ((stack,game)-> stack.size() <= 1));
                break;
            case "Poacher":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
                break;
            case "Remodel":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
                break;
            case "Throne Room":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
                break;
            case "Council Room":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
                break;
            case "Library":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
                break;
            case "Mine":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
                break;
            case "Sentry":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
                break;
            case "Artisan":
                //g.selectCards("",source,((stack,game)-> {}),((stack,game)-> {}));
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
