package com.dominion.prog2.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by cobra on 4/1/2017.
 */
public class CardStack {
    private ArrayList<Card> cards;

    public CardStack() {
        cards = new ArrayList<>();
    }

    public CardStack(ArrayList<Card> cards) {
        this.cards = new ArrayList<>();

        add(cards);
    }

    public void add(ArrayList<Card> cards) {
        for(Card c : cards) {
            this.cards.add(c);
        }
    }

    public void add(Card c) {
        this.cards.add(c);
    }

    public void remove(Card c) {
        this.cards.remove(c);
    }

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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card get(int i) {
        return cards.get(i);
    }

    public ArrayList<Card> get(CardType type) {
        ArrayList<Card> result = new ArrayList<>();

        for(Card c : cards) {
            if(c.getType() == type) {
                result.add(c);
            }
        }

        return result;
    }

    public ArrayList<Card> getAll() {
        ArrayList<Card> result = new ArrayList<>();

        for(Card c : cards) {
            result.add(c);
        }

        return result;
    }

    public int getSize() {
        return cards.size();
    }

    public HashMap<String, Integer> getCounts() {
        HashMap<String, Integer> result = new HashMap<>();

        for(Card c : cards) {
            result.put(c.getName(), result.getOrDefault(c.getName(), 0)+1);
        }

        return result;
    }

    public static HashMap<String, Integer> mergeCounts(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {
        HashMap<String, Integer> result = new HashMap<>();

        result.putAll(map1);

        map2.forEach((k, v) -> {
            result.merge(k, v, (v1, v2) -> v1 + v2);
        });

        return result;
    }
}
