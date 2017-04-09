package com.dominion.prog2.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class CardStack {
    private ArrayList<Card> cards;

    /**
     *initializes the arraylist for the cards in the stack
     */
    public CardStack() {
        cards = new ArrayList<>();
    }

    /**
     *initializes the arraylist for the cards in the stack
     * sets the cards based off the param
     * @param cards
     */
    public CardStack(ArrayList<Card> cards) {
        this.cards = new ArrayList<>();

        add(cards);
    }

    /**
     * adds cards to the list
     * @param cards
     */
    public void add(ArrayList<Card> cards) {
        for(Card c : cards) {
            this.cards.add(c);
        }
    }

    /**
     * adds individual card
     * @param c card
     */
    public void add(Card c) {
        this.cards.add(c);
    }

    /**
     * removed a specific card
     * @param c card
     * @return the card that was passed in
     */
    public Card remove(Card c) {
        this.cards.remove(c);
        return c;
    }

    /**
     * splices the list
     * @param startIndex
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

        return result;
    }

    /**
     * shuffles all the cards
     */
    public void shuffle() {
        Collections.shuffle(cards);
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
     * gets all the cards that have a specific card type
     * @param type
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
     * @return int size
     */
    public int size() {
        return cards.size();
    }

    /**
     * gets hashmap of name as key and value is the number of those cards
     * within the cardStack
     * @return hashmap String, int
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
     * @param map1
     * @param map2
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
}
