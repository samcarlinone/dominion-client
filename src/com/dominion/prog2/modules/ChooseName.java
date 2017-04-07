package com.dominion.prog2.modules;

import com.dominion.prog2.ui.Textbox;
import com.dominion.prog2.ui.UIElement;
import com.dominion.prog2.ui.UIManager;
import com.dominion.prog2.ui.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cobra on 4/6/2017.
 */
public class ChooseName implements Module {
    private Font ui_font = new Font("Arial", Font.PLAIN, 30);
    private Textbox name;
    private Button submit;

    public ChooseName() {
        name = new Textbox(10, 200, 200, 150, 40);
        name.font = ui_font;
        UIManager.get().addElement(name);

        name = new Textbox(10, 220, 220, 150, 40);
        name.font = ui_font;
        name.depth = 1;
        UIManager.get().addElement(name);

        submit = new Button("Submit", 200, 400, 150, 40);
        submit.font = ui_font;
        UIManager.get().addElement(submit);

        submit = new Button("Submit", 220, 420, 150, 40);
        submit.font = ui_font;
        submit.depth = 1;
        UIManager.get().addElement(submit);
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
