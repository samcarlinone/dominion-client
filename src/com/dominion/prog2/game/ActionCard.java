package com.dominion.prog2.game;


import com.dominion.prog2.modules.Game;
import java.util.ArrayList;

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
                    p.playedMerchant = true;
                break;
            case "Vassal":
                if(p.deck.getPosAsStackIfAttack(0).size() > 0)
                g.selectCards("Play this card?", new CardStack(new String[]{p.deck.get(0).getName()}),
                        ((stack,game)-> {
                            Player you = game.getYou();

                            if(stack.size() == 1) {
                                ActionCard c = (ActionCard) stack.get(0);
                                c.play(you, game);
                            }

                            you.discard.add(you.deck.remove(you.deck.get(0)));
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
                int numToDiscard = g.shop.maxCards-g.shoppe.getNumberTypesOfCards();

                g.selectCards("Discard "+numToDiscard+" cards",p.hand,
                        ((stack,game)-> {
                            Player you = game.getYou();
                            you.discard.add(stack.getAll());
                        }),
                        ((stack,game)-> stack.size() == numToDiscard));
                break;
            case "Remodel":
                if(p.hand.size() > 0) {
                    g.selectCards("Choose a Card to Trash", p.hand,
                            ((stack, game) -> {
                                Player you = game.getYou();
                                you.hand.remove(stack.get(0));
                                g.selectCards("Choose one of these cards", game.getShoppe().filterPrice(stack.get(0).getPrice() + 2),
                                        ((stack2, game2) -> {
                                            game2.getYou().discard.add(stack2.get(0));
                                            game2.getShoppe().remove(stack2.get(0));
                                        }),
                                        ((stack2, game2) -> stack2.size() > 0));
                            }),
                            ((stack, game) -> stack.size() > 0));
                }
                break;
            case "Throne Room":
                if(p.hand.size() > 0) {
                    ArrayList<CardType> types = new ArrayList<>();
                    types.add(CardType.ACTION);
                    types.add(CardType.ATTACK);
                    types.add(CardType.REACTION);
                    g.selectCards("Pick an Action to Play twice", p.hand.filterType(types),
                            ((stack, game) -> {
                                ActionCard c = (ActionCard) stack.get(0);

                                c.play(game.getYou(), game);
                                c.play(game.getYou(), game);

                                game.getYou().discard.add(c);
                            }),
                            ((stack, game) -> stack.size() == 1));
                }
                break;
            case "Library":
                //TODO: edit: I don't allow player to skip actions
                    while(p.hand.size() < 7)
                        p.pickUpCards(1);
                break;
            case "Mine":
                ArrayList<CardType> type = new ArrayList<>();
                type.add(CardType.TREASURE);
                g.selectCards("Trash a Treasure for a better Treasure",p.hand.filterType(type),
                        ((stack,game)-> {
                            Player you = game.getYou();
                            you.hand.remove(stack.get(0).getName());
                            switch(stack.get(0).getName()) {
                                case "Copper":
                                    Card s = game.getShoppe().get("Silver");
                                    you.hand.add(s);
                                    game.getShoppe().remove(s);
                                    break;
                                case "Silver":
                                    Card gold = game.getShoppe().get("Gold");
                                    you.hand.add(gold);
                                    game.getShoppe().remove(gold);
                                    break;
                            }
                        }),
                        ((stack,game)-> stack.size() < 2));
                break;
            case "Sentry":
                //TODO: edit: I don't allow player to order their cards
                CardStack twoCards = new CardStack();
                if(p.deck.size() >= 2){
                    twoCards.add(p.deck.get(0));
                    twoCards.add(p.deck.get(1));
                }
                else {
                    p.resetFromDiscard();
                    twoCards.add(p.deck.get(0));
                    twoCards.add(p.deck.get(1));
                }
                g.selectCards("Do you want to trash any of these?",twoCards,
                        ((stack,game)-> {
                            Player you = g.getYou();
                            for(Card c: stack) {
                                you.deck.remove(stack.get(c.getName()));
                                twoCards.remove(c);
                            }
                        }),
                        ((stack,game)-> true));
                if(twoCards.size() > 0)
                    g.selectCards("Do you want to discard any of these?",twoCards,
                            ((stack,game)-> {
                                Player you = g.getYou();
                                for(Card c: stack) {
                                    you.deck.remove(stack.get(c.getName()));
                                    you.discard.add(c);
                                    twoCards.remove(c);
                                }
                            }),((stack,game)-> true));
                break;
            case "Artisan":
                g.selectCards("Gain a Card costing up to 5",g.getShoppe().filterPrice(5),
                        ((stack,game)-> {
                            Player you = game.getYou();
                            Card c = game.getShoppe().get(stack.get(0).getName());
                            you.hand.add(c);
                            game.getShoppe().remove(c);
                        }),
                        ((stack,game)-> stack.size() < 2));
                g.selectCards("Put a card from hand onto your deck",p.hand,
                        ((stack,game)-> {
                            Player you = game.getYou();
                            Card c = stack.get(0);
                            you.hand.remove(c);
                            you.deck.addTop(c);
                        }),
                        ((stack,game)-> stack.size() < 2));
                break;
        }

        g.updateStats();
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
