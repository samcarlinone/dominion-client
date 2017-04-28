package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.InputFilters;
import com.dominion.prog2.ui.LobbyData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;


public class ChooseLobby extends Module {
    private Driver d;
    private GridPane root;

    private TableView<LobbyData> list;
    private ObservableList<LobbyData> lobby_list;

    private Button host;
    private TextField game_name;
    private Button join;
    private Label roomShutdown;

    /**
     * Creates ChooseLobby Object
     * @param d Driver
     */
    public ChooseLobby(Driver d, boolean wasRoomShutdown) {
        this.d = d;

        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);

        //List of Lobbies
        list = new TableView<>();
        lobby_list = FXCollections.observableArrayList();
        list.setItems(lobby_list);

        TableColumn<LobbyData,String> name = new TableColumn<>("Name");
        name.setPrefWidth(400/3);
        name.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<LobbyData,String> host = new TableColumn<>("Host");
        host.setPrefWidth(400/3);
        host.setCellValueFactory(new PropertyValueFactory("host"));
        TableColumn<LobbyData,String> size = new TableColumn<>("Players");
        size.setPrefWidth(400/3);
        size.setCellValueFactory(new PropertyValueFactory("size"));
        list.getColumns().setAll(name, host, size);

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
        this.host = new Button("Host a Lobby");
        this.host.setOnMouseClicked(a -> hostClicked());
        buttons.add(this.host, 1, 0);

        //Button for Joining Lobby
        join = new Button("Join a Lobby");
        join.setOnMouseClicked(a -> joinClicked());
        buttons.add(join, 2, 0);

        roomShutdown = new Label("Room was Shutdown");
        roomShutdown.setVisible(wasRoomShutdown);
        roomShutdown.setStyle("-fx-text-fill: #F00");
        GridPane.setHalignment(roomShutdown, HPos.CENTER);
        root.add(roomShutdown, 0, 2);

        setScene(new Scene(root, 745, 700));
    }

    private void hostClicked() {
        if(game_name.getText().length() == 0)
            return;

        HashMap<String, String> create_msg = new HashMap<>();
        create_msg.put("type", "create");
        create_msg.put("name", d.name);
        create_msg.put("room_name", game_name.getText());

        String json = d.comm.getMessage(d.comm.mapToJSON(create_msg));
        HashMap<String, String> result = d.comm.JSONToMap(json).get(0);

        if(result.get("type").equals("accepted")) {
            d.setCurrentModule(new HostWaitScreen(d, game_name.getText()));
        }
    }

    private void joinClicked() {
        LobbyData selected = list.getSelectionModel().getSelectedItem();

        if(selected == null)
            return;

        HashMap<String, String> result = d.simpleCommand("join", "room_name", selected.getName(), "room_host", selected.getHost());

        if(result.get("type").equals("accepted")) {
            d.setCurrentModule(new WaitScreen(d, selected.getName()));
        }
    }

    /**
     * Updates the Module
     * @param server_msg
     * @return Module
     */
    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {
        if(server_msg.size() == 1 && server_msg.get(0).get("type").equals("room_list")) {
            LobbyData lastSelected = list.getSelectionModel().getSelectedItem();
            HashMap<String, String> gameList = server_msg.get(0);
            String[] games = gameList.get("room_list").split(",");

            lobby_list.clear();

            if(gameList.get("room_list").length() == 0)
                return;

            for(int i=0; i<games.length; i++) {
                lobby_list.add(new LobbyData(games[i]));
            }

            if(lastSelected == null)
                return;

            for(int i=0; i<lobby_list.size(); i++) {
                if(lastSelected.equals(lobby_list.get(i))) {
                    list.getSelectionModel().select(lobby_list.get(i));
                    return;
                }
            }
        }
    }
}
