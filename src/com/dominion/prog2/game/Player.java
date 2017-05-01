package com.dominion.prog2.game;

import java.util.ArrayList;

public class Player
{
    public CardStack deck;
    public CardStack hand;
    public CardStack discard;
    public CardStack played;
    public String name;
    public ArrayList<ActionCard> queuedPlayCards;

    public int turnBuys;
    public int turnAction;
    public int turnMoney;

    public boolean playedMerchant = false;
    public boolean actionPhase = true;

    /**
     * Constructor for Player
         * Name: An identifier for the player, each player's username is unique
         * Deck: CardStack this is where the user pulls cards from, cards move from here to Hand
         * Hand: CardStack of what cards the player can see and what cards they can use, cards move from here to Played
         * Played: CardStack of what cards are played during the turn, cards move from here to Discard
         * Discard: CardStack of what cards were played already in past turns. If the deck doesn't have enough cards
         *          the discard pile will shuffle its cards and put them into the Deck
         * queuedPlayCards: ArrayList of Action cards that are in a queue.  This is used for keeping track of which cards are played
         * turnBuys: How many Cards the player can buy from the shop that turn. Starts off each turn with 1
         * turnAction: How many Action Cards that player can use that turn. Starts off each turn with 1
         * turnMoney: How much money the player has that turn. Used to buy cards. Starts off each turn with 0
         * playedMerchant: boolean.  If the merchant is played that turn, used for extra functionality of the card Merchant
         * actionPhase: What phase the player is on that turn
         *          true: can play Action Cards(till they run out of turnActions) or Treasure Cards
         *          false: Buy phase, this is where the player can buy cards from the shop
     */
    public Player(String name)
    {
        this.name = name;
        deck = new CardStack();
        hand = new CardStack();
        discard = new CardStack();
        played = new CardStack();
        queuedPlayCards = new ArrayList<>();

        //Initializes Deck
        for(int i = 0; i < 7; i ++)
            deck.add(CardInfo.getCard("Copper"));
        for(int i = 0; i < 3; i ++)
            deck.add(CardInfo.getCard("Estate"));

        deck.shuffle();

        nextTurn();
    }

    /**
     * The player progresses onto the next turn
     * resets turnBuys, turnAction, turnMoney
     * ActionPhase is set to true
     * playedMerchant is reset back to false
     * Cards move from played to Discard, from Hand to Discard
     * Will pick up 5 cards
     */
    public void nextTurn()
    {
        turnBuys = 1;
        turnAction = 1;
        turnMoney = 0;
        actionPhase = true;
        discard.add(played.getAll());
        played.clear();
        discard.add(hand.getAll());
        hand.clear();

        playedMerchant = false;

        pickUpCards(5);
    }

    /**
     * Transfers a set amount of cards from Deck to Hand
     * if there is not enough cards in Deck, discard will shuffle and transfer its cards to Deck
     */
    public void pickUpCards(int numCards)
    {
        int deckSize = deck.size();

        if(deckSize >= numCards)
            hand.add(deck.splice(0,numCards));
        else
        {
            resetFromDiscard();
            hand.add(deck.splice(0,numCards));
        }
    }

    /**
     * Shuffles Deck and adds to Deck
     */
    public void resetFromDiscard()
    {
        discard.shuffle();
        deck.add(discard.getAll());
        discard.clear();
    }

    /**
     * Getter for the total amount of cards the player has, no matter the CardType
     * Used for Gardens in the getTotalScore()
     */
    private int getTotalCards()
    {
        int count = 0;
        for(Card c: deck) {
            count ++;
        }
        for(Card c: hand) {
            count ++;
        }
        for(Card c: discard) {
            count ++;
        }
        for(Card c: played) {
            count ++;
        }
        return count;
    }

    /**
     * Tallies up all Victory Cards that the Player has
     * Used at the end of the Game to find final score
     */
    public int getTotalScore()
    {
        int score = 0;
        int totalCards = getTotalCards();
        for(Card c: deck) {
            if(c instanceof VictoryCard)
                score += ((VictoryCard) c).getVictoryValue(totalCards);
        }
        for(Card c: hand) {
            if(c instanceof VictoryCard)
                score += ((VictoryCard) c).getVictoryValue(totalCards);
        }
        for(Card c: discard) {
            if(c instanceof VictoryCard)
                score += ((VictoryCard) c).getVictoryValue(totalCards);
        }
        for(Card c: played) {
            if(c instanceof VictoryCard)
                score += ((VictoryCard) c).getVictoryValue(totalCards);
        }

        return score;
    }

}
