package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.Textbox;
import com.dominion.prog2.ui.Label;
import com.dominion.prog2.ui.UIManager;
import com.dominion.prog2.ui.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ChooseName implements Module {
    private Font ui_font = new Font("Arial", Font.PLAIN, 30);
    private Label name_label;
    private Label error_label;
    private Textbox name;
    private Button submit;

    private Driver d;

    /**
     * Module for the use to choose name, will check to make sure not already taken
     * @param d Driver
     */
    public ChooseName(Driver d) {
        this.d = d;

        name_label = new Label("Username", 100, 205, 150, 40);
        name_label.font = ui_font;
        name_label.borderWidth = 0;
        name_label.depth = 1;
        UIManager.get().addElement(name_label);

        name = new Textbox(10, 100, 200, 300, 40);
        name.font = ui_font;
        UIManager.get().addElement(name);

        submit = new Button("Submit", 100, 239, 300, 40);
        submit.font = ui_font;
        UIManager.get().addElement(submit);

        error_label = new Label("That name is unavailable", 50, 278, 400, 40);
        error_label.color = Color.WHITE;
        error_label.font = ui_font;
        UIManager.get().addElement(error_label);
    }

    /**
     * updates the module
     * @param server_msg
     * @return the next module(if the user puts in valid name)
     */
    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
        if(name.getText().length() > 0) {
            name_label.color = new Color(0, 0, 0, 0);
        } else {
            name_label.color = Color.LIGHT_GRAY;
        }

        if(submit.wasClicked() || name.submitted) {
            submit.setClicked(false);
            name.submitted = false;

            if(name.getText().length() == 0)
                return this;

            HashMap<String, String> name_msg = new HashMap<>();
            name_msg.put("type", "connect");
            name_msg.put("name", name.getText());

            String json = d.comm.getMessage(d.comm.mapToJSON(name_msg));

            if(json.contains("invalid")) {
                error_label.color = Color.RED;
            } else {
                d.name = name.getText();
                UIManager.get().removeAll();
                return new LobbyList(d);
            }
        }

        return this;
    }

    /**
     * Renders everything
     * @param g graphics
     */
    @Override
    public void render(Graphics g) {
        //TODO: Implement
    }
}
