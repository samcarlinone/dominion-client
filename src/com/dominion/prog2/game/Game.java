package com.dominion.prog2.game;


import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.Button;
import com.dominion.prog2.ui.CardGrid;
import com.dominion.prog2.ui.ImageCache;
import com.dominion.prog2.ui.UIManager;

import java.awt.*;
import java.util.ArrayList;

public class Game
{
    private ArrayList<Button> buttonList;
    private Driver d;
    private Window window;


    public Game(Driver d) {
        this.d = d;
        this.window = d.getWindow();
        buttonList = new ArrayList<Button>();

        CardStack s = new CardStack();
        s.add(new Card("Gold"));
        s.add(new Card("Copper"));
        s.add(new Card("Silver"));

        CardGrid grid = new CardGrid(0, 0, 400, 300);
        grid.border = true;
        grid.backgroundColor = new Color(40, 84, 168);
        grid.stacks.add(s);

        UIManager.get().addElement(grid);
    }

    public void render(Graphics g) {
        UIManager.get().render(g);
    }

    /**
     * Custom Card Functions
     */

    public void discard(CardStack s) {
        //TODO: Implement
    }

    public void trash(CardStack s) {
        //TODO: Implement
    }


}
