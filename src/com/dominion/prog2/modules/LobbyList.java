package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.TextList;
import com.dominion.prog2.ui.Textbox;
import com.dominion.prog2.ui.UIManager;
import com.dominion.prog2.ui.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cobra on 4/8/2017.
 */
public class LobbyList implements Module{
    private Driver d;

    private Font ui_font = new Font("Arial", Font.PLAIN, 30);
    private TextList lobbies;
    private Textbox msg;
    private Button send;

    public LobbyList(Driver d) {
        this.d = d;

        lobbies = new TextList(50, 100, 400, 200);
        lobbies.stringHeight = 36;
        lobbies.font = ui_font;
        UIManager.get().addElement(lobbies);

        msg = new Textbox(16,50, 299, 250, 40);
        msg.font = ui_font;
        msg.allowSpaces = true;
        UIManager.get().addElement(msg);

        send = new Button("Send", 299, 299, 151, 40);
        send.font = ui_font;
        UIManager.get().addElement(send);
    }

    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
        if(server_msg != null && server_msg.size() > 0) {
            for(HashMap<String, String> map : server_msg) {
                if(map.getOrDefault("type", "none").equals("message")) {
                    lobbies.strings.add("["+map.get("name")+"]> "+map.get("msg"));
                    lobbies.scroll(100, 100);
                }
            }
        }

        if(send.wasClicked() || msg.submitted) {
            send.setClicked(false);

            if(msg.getText().length() == 0)
                return this;

            HashMap<String, String> msg_map = new HashMap<>();
            msg_map.put("type", "broadcast");
            msg_map.put("name", d.name);
            msg_map.put("msg", msg.getText());

            msg.clearText();

            d.comm.getMessage(d.comm.mapToJSON(msg_map));
        }

        return this;
    }

    @Override
    public void render(Graphics g) {
        //TODO: Implement
    }
}
