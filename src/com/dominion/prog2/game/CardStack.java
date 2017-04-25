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
            cards.add(new Card(name));
        }
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
     * Clears the arrayList of Cards
     */
    public void clear()
    {
        cards.clear();
    }
}
