package com.dominion.prog2.game;

import java.util.Iterator;

public class CardStackIterator implements Iterator {
    private int index;
    private CardStack stack;

    /**
     * Iterator for a stack of Cards
     */
    public CardStackIterator(CardStack stack) {
        index = 0;
        this.stack = stack;
    }

    /**
     * Goes to next card within the Stack
     */
    @Override
    public Card next() {
        if(index < this.stack.size())
            return this.stack.get(index++);

        return null;
    }

    /**
     *  Returns whether there is another Card in the Stack
     */
    @Override
    public boolean hasNext() {
        return index < this.stack.size();
    }
}
