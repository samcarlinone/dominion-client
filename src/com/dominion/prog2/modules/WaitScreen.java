package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.ui.CardGrid;
import com.dominion.prog2.ui.UIManager;
import com.dominion.prog2.ui.Button;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WaitScreen implements Module
{
    private CardGrid allCards;
    private CardGrid chosenCards;
    private Button submit;

    private Font ui_font = new Font("Arial", Font.PLAIN, 30);
    private Driver d;

    /**
     * Module for the use to choose the cards that will be in the game
     * @param d Driver
     */
    public WaitScreen(Driver d)
    {
        this.d = d;

        CardStack fullList = new CardStack();

        allCards = new CardGrid(fullList, 0,100,100,500);
        UIManager.get().addElement(allCards);

        chosenCards = new CardGrid(new CardStack(), 100,100,100,500);
        UIManager.get().addElement(chosenCards);

        submit = new Button("Submit", 100, 239, 300, 40);
        submit.font = ui_font;
        UIManager.get().addElement(submit);
    }

    /**
     * Updates the module
     * @param server_msg
     * @return Module
     */
    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
        return null;
    }

    /**
     * Renders everything
     * @param g Graphics
     */
    @Override
    public void render(Graphics g) {

    }
}
