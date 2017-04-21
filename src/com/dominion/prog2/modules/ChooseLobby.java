package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;


public class ChooseLobby extends Module {
    private Driver d;
    private GridPane root;

    /**
     * Creates ChooseLobby Object
     * @param d Driver
     */
    public ChooseLobby(Driver d) {
        this.d = d;

        root = new GridPane();
        setScene(new Scene(root, 400, 600));

        //TODO: Port fully
//        lobbies = new TextList(50, 100, 400, 200);
//        lobbies.stringHeight = 36;
//        lobbies.font = ui_font;
//        UIManager.get().addElement(lobbies);
//
//        msg = new Textbox(16,50, 299, 250, 40);
//        msg.font = ui_font;
//        msg.allowSpaces = true;
//        UIManager.get().addElement(msg);
//
//        send = new Button("Send", 299, 299, 151, 40);
//        send.font = ui_font;
//        UIManager.get().addElement(send);
//
//        createLobby = new Button("Create Lobby",0,400,200,50);
//        createLobby.font = ui_font;
//        UIManager.get().addElement(createLobby);
//
//        joinLobby = new Button("Join Lobby",200,400,200,50);
//        joinLobby.font = ui_font;
//        UIManager.get().addElement(joinLobby);

    }

    /**
     * Updates the Module
     * @param server_msg
     * @return Module
     */
    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {
        //TODO: Finish porting
//        if(server_msg != null && server_msg.size() > 0) {
//            for(HashMap<String, String> map : server_msg) {
//                if(map.getOrDefault("type", "none").equals("message")) {
//                    lobbies.strings.add("["+map.get("name")+"]> "+map.get("msg"));
//                    lobbies.scroll(100, 100);
//                }
//            }
//        }
//
//        if(send.wasClicked() || msg.submitted) {
//            send.setClicked(false);
//
//            if(msg.getText().length() == 0)
//                return this;
//
//            HashMap<String, String> msg_map = new HashMap<>();
//            msg_map.put("type", "broadcast");
//            msg_map.put("name", d.name);
//            msg_map.put("msg", msg.getText());
//
//            msg.clearText();
//
//            d.comm.getMessage(d.comm.mapToJSON(msg_map));
//        }
//
//
//        if(joinLobby.wasClicked()) {
//            UIManager.get().removeAll();
//            return new WaitScreen(d);
//        }
//        if(createLobby.wasClicked()) {
//            UIManager.get().removeAll();
//            return new HostWaitScreen(d);
//        }
//        return this;
    }
}
