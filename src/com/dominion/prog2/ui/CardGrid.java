package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by CARLINSE1 on 4/3/2017.
 */
public class CardGrid extends UIElement {
    public ArrayList<CardStack> stacks;

    public boolean scrollable;
    public boolean border;

    public CardGrid(int x, int y, int w, int h) {
        super(x, y, w, h);

        stacks = new ArrayList<>();
        scrollable = true;
        border = false;
    }

    public void render(Graphics g) {
        //TODO: Implement
    }
}
