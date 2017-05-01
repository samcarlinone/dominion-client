package com.dominion.prog2.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


public class CardStack implements Iterable<Card> {
    private ArrayList<Card> cards;
    private ArrayList<StackChanged> listeners = new ArrayList<>();

    /**
     * Constructor of Card Stack
     *      This class is an arrayList of Cards
     * Used mainly in CardGrid
     */
    public CardStack() {
        cards = new ArrayList<>();
    }

    /**
     * Constructor of Card Stack
     *      This class is an arrayList of Cards
     *      Copies the passed in ArrayList to the Card variable
     * Used mainly in CardGrid
     */
    public CardStack(ArrayList<Card> cards) {
        this.cards = new ArrayList<>();

        add(cards);
    }

    /**
     * Constructor of Card Stack
     *      This class is an arrayList of Cards
     *      Creates and Adds Cards based off the list of names that were passed in
     * Used mainly in CardGrid
     */
    public CardStack(String[] names) {
        cards = new ArrayList<>();

        for(String name : names) {
            cards.add(CardInfo.getCard(name));
        }
    }

    /**
     * This method returns a CardStack with only cards with the passed in name
     * Used for playing a card within Action
     */
    public CardStack getStackOfName(String name)
    {
        CardStack names = new CardStack();
        for(Card c: cards)
            if(c.getName().equals(name))
                names.add(c);
        return names;
    }

    /**
     * Implement Iterable interface to allow enhanced for loops
     */
    public Iterator<Card> iterator() {
        return new CardStackIterator(this);
    }

    /**
     * Adds an ArrayList of Cards to the list of Cards that is already in the CardStack
     *      adds to the end of the List
     */
    public void add(ArrayList<Card> cards) {
        for(Card c : cards) {
            this.cards.add(c);
        }

        notifyListeners();
    }

    /**
     * Adds a specific Card to the list of Cards that is already in the CardStack
     *      adds to the end of the List
     */
    public void add(Card c) {
        this.cards.add(c);
        notifyListeners();
    }

    /**
     * Add a specific card to the top of the CardStack
     */
    public void addTop(Card c)
    {
        ArrayList<Card> before = cards;
        cards.clear();
        cards.add(c);
        cards.addAll(before);
    }

    /**
     * Adds an arbitrary number of new cards by name
     *      Used mainly for creating the shop, and making sure there is a correct amount of each card
     */
    public void addMultiple(String name, int number) {
        for(int i=0; i<number; i++) {
            this.cards.add(CardInfo.getCard(name));
        }
        notifyListeners();
    }

    /**
     * Removes a specific Card
     * Returns the card that was removed
     */
    public Card remove(Card c) {
        this.cards.remove(c);
        notifyListeners();
        return c;
    }

    /**
     * Removed the first Card that has the specified name
     * Returns the card that was removed;
     */
    public Card remove(String name) {
        for(int i = 0; i < cards.size(); i ++)
        {
            if(cards.get(i).getName().equals(name)){
                Card c = cards.get(i);
                cards.remove(c);
                notifyListeners();
                return c;
            }
        }
        notifyListeners();
        return null;
    }

    /**
     * splices the list, REMOVES the card(s) too
     * Returns the spliced list
     */
    public ArrayList<Card> splice(int startIndex, int number) {
        ArrayList<Card> result = new ArrayList<>();

        int i = 0;
        while(i<number) {
            result.add(cards.get(startIndex));
            cards.remove(startIndex);
            i++;
        }

        notifyListeners();
        return result;
    }

    /**
     * Shuffles all the cards
     * Used by Player
     */
    public void shuffle() {
        Collections.shuffle(cards);
        notifyListeners();
    }

    /**
     * Getter for the card at the specified index
     * return null if the Index is larger than the ArrayList within CardStack
     */
    public Card get(int i) {
        if(i > cards.size())
            return null;
        else
            return cards.get(i);
    }

    /**
     * Sees if the CardStack contains the specified card
     * returns false if it does not
     */
    public boolean has(Card c) { return cards.contains(c); }

    /**
     * Sees if the CardStack contains a Card with a specific name
     * returns false if it does not
     */
    public boolean has(String name)
    {
        for(Card c: cards)
            if(c.getName().equals(name))
                return true;
        return false;
    }

    /**
     * Sees if the CardStack contains a Card with a specific Type
     * returns false if it does not
     */
    public boolean has(CardType type)
    {
        for(Card c: cards)
            if(c.getType().equals(type))
                return true;
        return false;
    }

    /**
     * Gets the first Card with the specified name
     * returns null if there is no card with that name
     */
    public Card get(String name) {
        for(Card card : cards) {
            if(card.getName().equals(name)) {
                return card;
            }
        }

        return null;
    }

    /**
     * Gets all the cards within the CardStack that has a specific type
     *  returns empty ArrayList if there is none with that type
     */
    public ArrayList<Card> get(CardType type) {
        ArrayList<Card> result = new ArrayList<>();

        for(Card c : cards) {
            if(c.getType() == type) {
                result.add(c);
            }
        }

        return result;
    }

    /**
     * Gets a CardStack that only has the Card at specified position
     * Used within ActionCard for one of the extra functions
     */
    public CardStack getPosAsStack(int pos)
    {
        CardStack stack = new CardStack();
        stack.add(cards.get(pos));
        return stack;
    }

    /**
     * Gets a CardStack that only has the Card at specified position
     *      Only if the card has a type Action
     * Used within ActionCard for one of the extra functions
     */
    public CardStack getPosAsStackIfAttack(int pos)
    {
        CardStack stack = new CardStack();
        if(cards.get(pos).type.equals(CardType.ACTION))
            stack.add(cards.get(pos));
        return stack;
    }

    /**
     * Gets all the cards in the CardStack
     */
    public ArrayList<Card> getAll() {
        ArrayList<Card> result = new ArrayList<>();

        for(Card c : cards) {
            result.add(c);
        }

        return result;
    }

    /**
     * Getter for the size of the ArrayList of Cards
     */
    public int size() {
        return cards.size();
    }

    /**
     * Listeners are notified whenever the cards in a stack change
     */
    public void addListener(StackChanged listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners of a change to this stack
     */
    private void notifyListeners() {
        for(StackChanged listener : listeners) {
            listener.change();
        }
    }

    /**
     * Gets hashMap of name as key and value is the number of those cards within the cardStack
     */
    public HashMap<String, Integer> getCounts() {
        HashMap<String, Integer> result = new HashMap<>();

        for(Card c : cards) {
            result.put(c.getName(), result.getOrDefault(c.getName(), 0)+1);
        }

        return result;
    }

    /**
     * Gets how many different cards there are
     * @return types
     */
    public int getNumberTypesOfCards()
    {
        ArrayList<String> names = new ArrayList<>();
        for(Card c: cards)
            if(!names.contains(c.getName()))
                names.add(c.getName());
        return names.size();
    }

    /**
     * Filters a CardStack to have cards with the price or less
     * @param price int
     * @return CardStack based off Prices
     */
    public CardStack filterPrice(int price)
    {
        CardStack filtered = new CardStack();
        for(Card c: cards)
            if(c.getPrice() <= price)
                filtered.add(c);
        return filtered;
    }

    /**
     * Filters a Cardstack based off cardType
     * @param types Array of CardTypes
     * @return Cardstack which only has the CardTypes
     */
    public CardStack filterType(ArrayList<CardType> types)
    {
        CardStack filtered = new CardStack();
        for(Card c: cards)
            if(types.contains(c.getType()))
                filtered.add(c);
        return filtered;
    }

    /**
     * Clears the arrayList of Cards
     */
    public void clear()
    {
        cards.clear();
    }
}
