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
        name = new Textbox(10, 200, 200, 100, 20);
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
