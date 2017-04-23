package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;


public class ChooseLobby extends Module {
    private Driver d;
    private GridPane root;

    private ListView list;
    private ObservableList<String> items;

    private Button host;
    private Button join;

    /**
     * Creates ChooseLobby Object
     * @param d Driver
     */
    public ChooseLobby(Driver d) {
        this.d = d;

        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);


        //List of Lobbies
        list = new ListView<String>();
        items = FXCollections.observableArrayList (
                "Lobby 1", "Lobby 2", "Lobby 3", "Lobby 4", "Lobby 5");
        list.setItems(items);
        list.setPrefWidth(100);
        list.setPrefHeight(200);
        root.add(list,0,0);

        //Button for Hosting
        host = new Button("Host a Lobby");
        host.setOnMouseClicked(a -> hostClicked());
        root.add(host, 0, 1);

        //Button for Joining Lobby
        join = new Button("Join a Lobby");
        join.setOnMouseClicked(a -> joinClicked());
        root.add(join, 1, 1);

        setScene(new Scene(root, 0, 0));
    }

    public void hostClicked()
    {
        d.setCurrentModule(new HostWaitScreen(d));
    }

    public void joinClicked()
    {
        d.setCurrentModule(new WaitScreen(d));
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
