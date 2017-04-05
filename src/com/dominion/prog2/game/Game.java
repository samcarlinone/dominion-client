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

    private CardGrid grid1;
    private CardGrid grid2;

    public Game(Driver d) {
        this.d = d;
        this.window = d.getWindow();
        buttonList = new ArrayList<Button>();

        CardStack s = new CardStack();
        s.add(new Card("Gold"));
        s.add(new Card("Gold"));
        s.add(new Card("Copper"));
        s.add(new Card("Silver"));

        grid1 = new CardGrid(s, 0, 0, 400, 300);
        grid1.border = true;
        grid1.backgroundColor = new Color(40, 84, 168);
        UIManager.get().addElement(grid1);

        grid2 = new CardGrid(s, 500, 0, 400, 300);
        grid2.border = true;
        grid2.backgroundColor = new Color(40, 84, 168);
        UIManager.get().addElement(grid2);
    }

    public void render(Graphics g) {
        UIManager.get().render(g);
    }

    public void tick() {
        if(grid1.lastClicked != null) {
            grid2.stack.add(grid1.stack.remove(grid1.lastClicked));
            grid1.lastClicked = null;
        }
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
