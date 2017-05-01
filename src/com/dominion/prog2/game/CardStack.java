package com.dominion.prog2.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


public class CardStack implements Iterable<Card> {
    private ArrayList<Card> cards;
    private ArrayList<StackChanged> listeners = new ArrayList<>();

    /**
     *initializes the arraylist for the cards in the stack
     */
    public CardStack() {
        cards = new ArrayList<>();
    }

    /**
     *initializes the arraylist for the cards in the stack
     * sets the cards based off the param
     * @param cards ArrayList of Cards
     */
    public CardStack(ArrayList<Card> cards) {
        this.cards = new ArrayList<>();

        add(cards);
    }

    /**
     * Initializes card stack from list of names
     */
    public CardStack(String[] names) {
        cards = new ArrayList<>();

        for(String name : names) {
            cards.add(CardInfo.getCard(name));
        }
    }

    /**
     * Gets a card stack of only the name
     * @param name
     * @return Card stack
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
     * @return
     */
    public Iterator<Card> iterator() {
        return new CardStackIterator(this);
    }

    /**
     * adds cards to the list
     * @param cards ArrayList of cards
     */
    public void add(ArrayList<Card> cards) {
        for(Card c : cards) {
            this.cards.add(c);
        }

        notifyListeners();
    }

    /**
     * adds individual card
     * @param c card
     */
    public void add(Card c) {
        this.cards.add(c);
        notifyListeners();
    }

    /**
     * Add a card to the top of the deck
     * @param c Card
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
     */
    public void addMultiple(String name, int number) {
        for(int i=0; i<number; i++) {
            this.cards.add(CardInfo.getCard(name));
        }
        notifyListeners();
    }

    /**
     * removed a specific card
     * @param c card
     * @return the card that was passed in
     */
    public Card remove(Card c) {
        this.cards.remove(c);
        notifyListeners();
        return c;
    }

    /**
     * removed a card by name
     * @param name of card
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
     * splices the list, REMOVES the card too
     * @param startIndex int
     * @param number of cards to include
     * @return the new arraylist
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
     * shuffles all the cards
     */
    public void shuffle() {
        Collections.shuffle(cards);
        notifyListeners();
    }

    /**
     * gets a specific card
     * @param i index of the card
     * @return the card requested
     */
    public Card get(int i) {
        return cards.get(i);
    }

    /**
     * Sees if the Arraylist has the card
     * @param c card
     * @return true or false if the array has the card
     */
    public boolean has(Card c) { return cards.contains(c); }

    /**
     * Sees if the ArrayList has a card with a specific name
     * @param name string
     * @return true of false if array has a card with the name
     */
    public boolean has(String name)
    {
        for(Card c: cards)
            if(c.getName().equals(name))
                return true;
        return false;
    }

    /**
     * Sees if the ArrayList has a card with a specific type
     * @param type CardType
     * @return true if the array has card with the type, else returns false
     */
    public boolean has(CardType type)
    {
        for(Card c: cards)
            if(c.getType().equals(type))
                return true;
        return false;
    }

    /**
     * Get card by name or returns null if not found
     * @return First card in array with given name or null
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
     * gets all the cards that have a specific card type
     * @param type CardType
     * @return arraylist of cards
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
     * Gets a card and returns as a whole stack
     * @param pos of card
     * @return Stack.
     */
    public CardStack getPosAsStack(int pos)
    {
        CardStack stack = new CardStack();
        stack.add(cards.get(pos));
        return stack;
    }

    /**
     * Gets a card and returns as a whole stack
     * @param pos of card
     * @return Stack.
     */
    public CardStack getPosAsStackIfAttack(int pos)
    {
        CardStack stack = new CardStack();
        if(cards.get(pos).type.equals(CardType.ACTION))
            stack.add(cards.get(pos));
        return stack;
    }

    /**
     * gets all the cards in the CardStack
     * @return arraylist of cards
     */
    public ArrayList<Card> getAll() {
        ArrayList<Card> result = new ArrayList<>();

        for(Card c : cards) {
            result.add(c);
        }

        return result;
    }

    /**
     * getter for the size of the arraylist
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
     * gets hashmap of name as key and value is the number of those cards within the cardStack
     */
    public HashMap<String, Integer> getCounts() {
        HashMap<String, Integer> result = new HashMap<>();

        for(Card c : cards) {
            result.put(c.getName(), result.getOrDefault(c.getName(), 0)+1);
        }

        return result;
    }

    /**
     * merges two card Stack
     * @param map1 HashMap(String, Integer)
     * @param map2 HashMap(String, Integer)
     * @return the merged stack
     */
    public static HashMap<String, Integer> mergeCounts(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {
        HashMap<String, Integer> result = new HashMap<>();

        result.putAll(map1);

        map2.forEach((k, v) -> {
            result.merge(k, v, (v1, v2) -> v1 + v2);
        });

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
