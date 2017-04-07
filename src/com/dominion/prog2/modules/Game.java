package com.dominion.prog2.modules;


import com.dominion.prog2.Driver;
import com.dominion.prog2.game.*;
import com.dominion.prog2.network.NodeCommunicator;
import com.dominion.prog2.ui.Button;
import com.dominion.prog2.ui.CardGrid;
import com.dominion.prog2.ui.ImageCache;
import com.dominion.prog2.ui.UIManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Module {
    private ArrayList<Button> buttonList;
    private Driver d;
    private com.dominion.prog2.game.Window window;

    private CardGrid grid1;
    private CardGrid grid2;

    private NodeCommunicator comm;
    private HashMap<String, String> dataSent;
    private ArrayList<HashMap<String, String>> dataReceived;
    private int timer;

    public Game(Driver d, NodeCommunicator comm) {
        this.d = d;
        this.window = d.getWindow();
        this.comm = comm;
        buttonList = new ArrayList<Button>();

        dataSent = new HashMap<>();
        dataReceived = new ArrayList<>();

        CardStack s = new CardStack();
        s.add(new Card("Gold"));
        s.add(new Card("Gold"));
        s.add(new Card("Copper"));
        s.add(new Card("Silver"));

        grid1 = new CardGrid(s, 20, 0, 400, 300);
        grid1.border = true;
        grid1.backgroundColor = new Color(40, 84, 168);
        UIManager.get().addElement(grid1);

        CardStack s2 = new CardStack();
        grid2 = new CardGrid(s2, 500, 0, 400, 300);
        grid2.border = true;
        grid2.backgroundColor = new Color(40, 84, 168);
        UIManager.get().addElement(grid2);
    }

    @Override
    public void render(Graphics g) {
        //Nothing for now
    }

    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
        if(grid1.lastClicked != null) {
            grid2.stack.add(grid1.stack.remove(grid1.lastClicked));
            grid1.lastClicked = null;
        }

        if(grid2.lastClicked != null) {
            grid1.stack.add(grid2.stack.remove(grid2.lastClicked));
            grid2.lastClicked = null;
        }

        return this;
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
