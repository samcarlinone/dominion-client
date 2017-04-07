package com.dominion.prog2.modules;

import com.dominion.prog2.ui.Textbox;
import com.dominion.prog2.ui.UIManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cobra on 4/6/2017.
 */
public class ChooseName implements Module {
    private Textbox name;

    public ChooseName() {
        name = new Textbox(10, 200, 200, 150, 40);
        name.font = new Font("Arial", Font.PLAIN, 30);
        UIManager.get().addElement(name);

        name = new Textbox(10, 220, 220, 150, 40);
        name.font = new Font("Arial", Font.PLAIN, 30);
        name.depth = 1;
        UIManager.get().addElement(name);
    }

    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
        //TODO: Implement
        return this;
    }

    @Override
    public void render(Graphics g) {
        //TODO: Implement
    }
}
