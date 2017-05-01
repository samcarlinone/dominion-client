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
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.HashMap;

public class ChooseLobby extends Module {
    private Driver d;

    private TableView<LobbyData> list;
    private ObservableList<LobbyData> lobby_list;

    private TextField game_name;
    private Label userReason;

    /**
     * Constructor for ChooseLobby
     *      Parent Class: Module
     * This is the screen where the users create or join a lobby
     * The reason is if the user is coming from somewhere other than ChooseName
     * List: Table of the Lobbies that the user is allowed to connect to
     *      contains: Lobby name, Host name, How many players are in the Lobby (max 4)
     * lobby_List: The actual data on the lobbies that users are able to connect to
     * Game_name: If a user is hosting a lobby, this is where the user would put in the lobby Name
     */
    public ChooseLobby(Driver d, String reason) {
        this.d = d;

        GridPane root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);

        //List of Lobbies
        list = new TableView<>();
        lobby_list = FXCollections.observableArrayList();
        list.setItems(lobby_list);

        TableColumn<LobbyData,String> name = new TableColumn<>("Name");
        name.setPrefWidth(400/3);
        name.setCellValueFactory(new PropertyValueFactory("name"));
        name.setSortable(false);
        TableColumn<LobbyData,String> host = new TableColumn<>("Host");
        host.setPrefWidth(400/3);
        host.setCellValueFactory(new PropertyValueFactory("host"));
        host.setSortable(false);
        TableColumn<LobbyData,String> size = new TableColumn<>("Players");
        size.setPrefWidth(400/3);
        size.setCellValueFactory(new PropertyValueFactory("size"));
        size.setSortable(false);
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
        Button hostButton = new Button("Host a Lobby");
        hostButton.setOnMouseClicked(a -> hostClicked());
        buttons.add(hostButton, 1, 0);

        //Button for Joining Lobby
        Button join = new Button("Join a Lobby");
        join.setOnMouseClicked(a -> joinClicked());
        buttons.add(join, 2, 0);

        if(reason != null) {
            userReason = new Label(reason);
            userReason.setStyle("-fx-text-fill: #F00");
            GridPane.setHalignment(userReason, HPos.CENTER);
            root.add(userReason, 0, 2);
        }

        setScene(new Scene(root, 745, 700));
    }

    /**
     * This is called by the Host a Lobby Button
     * Makes sure there is a name within Game_name;
     * Sends out a message to all the people that there is a new lobby, also sends out the information on the lobby
     * Changes Module in Driver to HostWaitScreen(passes Driver, and the Lobby name)
     */
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

    /**
     * This is called by the Join a Lobby Button
     * Makes sure the user selected a lobby from the list
     * Sends out message that you are joining the lobby
     * if you can join the Lobby:
     *      changes module in Driver to WaitScreen(passes Driver, Lobby name, and the players who are in the lobby already)
     */
    private void joinClicked() {
        LobbyData selected = list.getSelectionModel().getSelectedItem();

        if(selected == null)
            return;

        HashMap<String, String> result = d.simpleCommand("join", "room_name", selected.getName(), "room_host", selected.getHost());

        if(result.get("type").equals("accepted")) {
            d.setCurrentModule(new WaitScreen(d, selected.getName(), result.get("players")));
        }
    }

    /**
     * This method parses out the different messages that are passed to the client
     *      Adds Lobbies if there is a message that adds a lobby
     * @param server_msg the array list of hashmaps representing new messages from the server
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
