package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.InputFilters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;


public class ChooseLobby extends Module {
    private Driver d;
    private GridPane root;

    private ListView list;
    private ObservableList<String> lobby_list;

    private Button host;
    private TextField game_name;
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
        lobby_list = FXCollections.observableArrayList("");
        list.setItems(lobby_list);
        list.setPrefWidth(400);
        list.setPrefHeight(300);
        root.add(list,0,0);

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        root.add(buttons, 0, 1);

        //Game hosting name
        game_name = new TextField();
        game_name.addEventFilter(KeyEvent.KEY_TYPED, InputFilters.nameFilter());
        game_name.addEventFilter(KeyEvent.KEY_TYPED, InputFilters.lengthFilter(12));
        game_name.setPromptText("Lobby Name");
        buttons.add(game_name, 0, 0);

        //Button for Hosting
        host = new Button("Host a Lobby");
        host.setOnMouseClicked(a -> hostClicked());
        buttons.add(host, 1, 0);

        //Button for Joining Lobby
        join = new Button("Join a Lobby");
        join.setOnMouseClicked(a -> joinClicked());
        buttons.add(join, 2, 0);

        setScene(new Scene(root, 745, 700));
    }

    public void hostClicked() {
        if(game_name.getText().length() == 0)
            return;

        HashMap<String, String> name_msg = new HashMap<>();
        name_msg.put("type", "create");
        name_msg.put("name", d.name);
        name_msg.put("room_name", game_name.getText());

        String json = d.comm.getMessage(d.comm.mapToJSON(name_msg));

        if(json.contains("accepted")) {
            d.setCurrentModule(new HostWaitScreen(d));
        }
    }

    public void joinClicked() {
        System.out.println(list.getSelectionModel().getSelectedItem());
    }

    /**
     * Updates the Module
     * @param server_msg
     * @return Module
     */
    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {
        if(server_msg.size() == 1 && server_msg.get(0).get("type").equals("room_list")) {
            HashMap<String, String> gameList = server_msg.get(0);
            String[] games = gameList.get("room_list").split(",");

            lobby_list.clear();

            if (gameList.get("room_list").length() == 0)
                return;

            for (int i = 0; i < games.length; i++) {
                lobby_list.add(games[i]);
            }
        }
    }
}
