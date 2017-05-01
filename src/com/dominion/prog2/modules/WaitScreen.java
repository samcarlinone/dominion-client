package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.CardInfo;
import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.ui.CardGrid;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.HashMap;

public class WaitScreen extends Module
{
    private Driver d;
    private GridPane root;
    private Label Title;

    private Label chosenTitle;
    private CardGrid chosenCards;

    private TableView<String> players;
    private ObservableList<String> playerList;

    private Button leave;

    public WaitScreen(Driver d, String LobbyName, String userNames) {
        this.d = d;

        HashMap<String, String> requestCards = new HashMap<>();
        requestCards.put("type", "getChosenCards");
        d.broadcast(requestCards);

        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);

        //Title
        Title = new Label("Lobby: " + LobbyName + " | Waiting to Start");
        Title.setStyle("-fx-font-size: 18pt");
        GridPane.setHalignment(Title, HPos.CENTER);
        root.add(Title, 0, 0);

        //Cards that are chosen for the Lobby
        GridPane cardChoosers = new GridPane();
        cardChoosers.setPrefSize(600, 500);
        cardChoosers.setAlignment(Pos.CENTER);

        chosenTitle = new Label("Chosen Cards");
        chosenTitle.setStyle("-fx-font-size: 18pt");
        cardChoosers.setHalignment(chosenTitle, HPos.CENTER);
        cardChoosers.add(chosenTitle, 1, 0);

        chosenCards = new CardGrid(new CardStack(),125, false, true);
        chosenCards.getRootPane().setPrefWidth(300);
        chosenCards.getRootPane().setPrefHeight(500);
        cardChoosers.add(chosenCards.getRootPane(),1,1);

        root.add(cardChoosers,0,1);

        //Player and Kick Section
        players = new TableView<>();
        playerList = FXCollections.observableArrayList();

        for(String name : userNames.split(",")) {
            if(!name.equals(d.name))
                playerList.add(name);
        }

        players.setItems(playerList);
        players.setMaxSize(200,200);

        TableColumn<String,String> users = new TableColumn<>("Player in Lobby");
        users.setPrefWidth(190);
        users.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });
        users.setSortable(false);

        players.getColumns().addAll(users);

        GridPane.setHalignment(players, HPos.CENTER);
        root.add(players,0,6);

        //Buttons
        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);

        this.leave = new Button("Leave Lobby");
        this.leave.setOnMouseClicked(a -> leaveClicked());
        buttons.add(this.leave, 0, 1);

        root.add(buttons,0,5);

        setScene(new Scene(root, 745, 700));
    }

    public void leaveClicked()
    {
        HashMap<String, String> result = d.simpleCommand("leave");

        if(result.get("type").equals("accepted")) {
            d.setCurrentModule(new ChooseLobby(d, null));
        } else {
            System.exit(28);
        }
    }

    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {
        for(HashMap<String, String> msg : server_msg) {
            switch (msg.get("type")) {
                case "join":
                    playerList.add(msg.get("user"));
                    break;
                case "disconnect":
                    playerList.remove(msg.get("user"));
                    break;
                case "setCardGrid":
                    String fullList = msg.get("data");
                    if(!fullList.equals("")){
                        for(String name: fullList.split(","))
                            chosenCards.getCardStack().add(CardInfo.getCard(name));
                    }
                    break;
                case "addCardGrid":
                    chosenCards.getCardStack().add(CardInfo.getCard(msg.get("data")));
                    break;
                case "removeCardGrid":
                    chosenCards.getCardStack().remove(msg.get("data"));
                    break;
                case "startGame":
                    String finalList = msg.get("data");
                    CardStack finalShop = new CardStack();
                    if(!finalList.equals("")) {
                        for(String name: finalList.split(","))
                        {
                            finalShop.add(CardInfo.getCard(name));
                        }
                    }
                    if(finalShop.size() > 0)
                        d.setCurrentModule(new Game(d, finalShop, playerList));
                    break;
            }
        }
    }
}