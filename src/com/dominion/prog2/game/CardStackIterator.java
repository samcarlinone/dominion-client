package com.dominion.prog2.game;

import java.util.Iterator;

public class CardStackIterator implements Iterator {
    private int index;
    private CardStack stack;

    public CardStackIterator(CardStack stack) {
        index = 0;
        this.stack = stack;
    }

    @Override
    public Card next() {
        if(index < this.stack.size())
            return this.stack.get(index++);

        return null;
    }

    @Override
    public boolean hasNext() {
        return index < this.stack.size();
    }
}
