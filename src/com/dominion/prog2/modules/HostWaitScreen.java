package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.Card;
import com.dominion.prog2.game.CardInfo;
import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.ui.CardGrid;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HostWaitScreen extends Module
{
    private Driver d;
    private GridPane root;
    private Label Title;

    private Button start;
    private Button leave;
    private Button clear;

    private Button kick;
    private TableView<String> players;
    private ObservableList<String> playerList;

    private Label kingdomTitle;
    private Label chosenTitle;
    private CardGrid kingdomCards;
    private CardGrid chosenCards;

    private Button presetFirst;
    private Button presetSize;
    private Button presetDeck;
    private Button presetSleight;
    private Button presetImprovement;
    private Button presetSilver;


    public HostWaitScreen(Driver d, String LobbyName) {
        this.d = d;

        root = new GridPane();
        root.setPrefSize(600, 600);
        root.setAlignment(Pos.CENTER);

        Title = new Label("Hosting: " + LobbyName);
        Title.setStyle("-fx-font-size: 24pt");
        GridPane.setHalignment(Title, HPos.CENTER);
        root.add(Title, 0, 0);

        Separator rule = new Separator();
        rule.setPadding(new Insets(10, 10, 10, 10));
        GridPane.setValignment(rule, VPos.BOTTOM);
        root.add(rule, 0, 1);

        //Presets
        GridPane presets = new GridPane();
        presets.setAlignment(Pos.CENTER);

        Label presetsLabel = new Label("Presets: ");
        presetsLabel.setStyle("-fx-font-size: 16pt");
        presets.add(presetsLabel, 0, 0);
        this.presetFirst = new Button("First Game");
        this.presetFirst.setOnMouseClicked(a -> presetCards("first"));
        presets.add(this.presetFirst, 1, 0);
        this.presetSize = new Button("Size Distortion");
        this.presetSize.setOnMouseClicked(a -> presetCards("size"));
        presets.add(this.presetSize, 2, 0);
        this.presetDeck = new Button("Deck Top");
        this.presetDeck.setOnMouseClicked(a -> presetCards("deck"));
        presets.add(this.presetDeck, 3, 0);
        this.presetSleight = new Button("Sleight of Hand");
        this.presetSleight.setOnMouseClicked(a -> presetCards("hand"));
        presets.add(this.presetSleight, 4, 0);
        this.presetImprovement = new Button("Improvements");
        this.presetImprovement.setOnMouseClicked(a -> presetCards("improv"));
        presets.add(this.presetImprovement, 5, 0);
        this.presetSilver = new Button("Silver & Gold");
        this.presetSilver.setOnMouseClicked(a -> presetCards("sg"));
        presets.add(this.presetSilver, 6, 0);

        root.add(presets,0,2);

        GridPane cardChoosers = new GridPane();
        cardChoosers.setPrefSize(600, 500);
        cardChoosers.setAlignment(Pos.CENTER);

        kingdomTitle = new Label("Kingdom Cards");
        kingdomTitle.setStyle("-fx-font-size: 18pt");
        cardChoosers.setHalignment(kingdomTitle, HPos.CENTER);
        cardChoosers.add(kingdomTitle, 0, 0);
        chosenTitle = new Label("Chosen Cards");
        chosenTitle.setStyle("-fx-font-size: 18pt");
        cardChoosers.setHalignment(chosenTitle, HPos.CENTER);
        cardChoosers.add(chosenTitle, 1, 0);

        kingdomCards = new CardGrid(new CardStack(CardInfo.kingdomCardNames),125);
        kingdomCards.getRootPane().setPrefWidth(300);
        kingdomCards.getRootPane().setPrefHeight(500);
        kingdomCards.addListener(cardName -> {
            if(chosenCards.getCardStack().size() < 10)
                CardGrid.move(cardName, kingdomCards, chosenCards);

            HashMap<String, String> add = new HashMap<>();
            add.put("type", "addCardGrid");
            add.put("data", ""+cardName);

            d.broadcast(add);
        });
        cardChoosers.add(kingdomCards.getRootPane(),0,1);

        chosenCards = new CardGrid(new CardStack(),125);
        chosenCards.getRootPane().setPrefWidth(300);
        chosenCards.getRootPane().setPrefHeight(500);
        chosenCards.addListener(cardName -> {
            CardGrid.move(cardName, chosenCards, kingdomCards);

            HashMap<String, String> remove = new HashMap<>();
            remove.put("type", "removeCardGrid");
            remove.put("data", ""+cardName);

            d.broadcast(remove);
        });
        cardChoosers.add(chosenCards.getRootPane(),1,1);

        root.add(cardChoosers, 0, 3);

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);

        this.start = new Button("Start Game");
        this.start.setOnMouseClicked(a -> startClicked());
        buttons.add(this.start, 1, 0);

        this.leave = new Button("Close Lobby");
        this.leave.setOnMouseClicked(a -> leaveClicked());
        buttons.add(this.leave, 2, 0);

        this.clear = new Button("Clear Chosen");
        this.clear.setOnMouseClicked(a -> clearChosen());
        buttons.add(this.clear, 3, 0);

        root.add(buttons,0,4);

        //Player and Kick Section
        players = new TableView<>();
        players.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        playerList = FXCollections.observableArrayList();
        players.setItems(playerList);
        players.setMaxSize(200,200);

        TableColumn<String,String> users = new TableColumn<>("Player in Lobby");
        users.setPrefWidth(400/3);
        users.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        players.getColumns().addAll(users);

        GridPane.setHalignment(players, HPos.CENTER);
        root.add(players,0,5);

        this.kick = new Button("Kick Player(s)");
        this.kick.setOnMouseClicked(a -> kickPlayers());
        GridPane.setHalignment(kick, HPos.CENTER);
        root.add(this.kick,0,6);

        setScene(new Scene(root, 745, 700));
    }

    public void kickPlayers()
    {
        ObservableList<String> selected = players.getSelectionModel().getSelectedItems();

        for(String name : selected) {
            if(name != null) {
                System.out.println(name);
                d.simpleCommand("kick", "player_name", name);
            }
        }
    }

    public void clearChosen()
    {
        //resets all cards
        while(chosenCards.getCardStack().size() > 0)
        {
            String name = chosenCards.getCardStack().get(0).getName();
            chosenCards.move(name, chosenCards, kingdomCards);

            HashMap<String, String> remove = new HashMap<>();
            remove.put("type", "removeCardGrid");
            remove.put("data", ""+name);

            d.broadcast(remove);
        }
    }

    public void presetCards(String n)
    {
        List<String> preset = new ArrayList<>();
        clearChosen();

        switch(n)
        {
            case "first":
                preset = new ArrayList<>(Arrays.asList("Cellar",
                        "Market","Merchant","Militia","Mine","Moat",
                        "Remodel","Smithy","Village","Workshop"));
                break;
            case "size":
                preset = new ArrayList<>(Arrays.asList("Artisan",
                        "Bandit","Bureaucrat","Chapel","Festival",
                        "Gardens","Sentry","Throne Room","Witch","Workshop"));
                break;
            case "deck":
                preset = new ArrayList<>(Arrays.asList("Artisan",
                        "Bureaucrat","Council Room","Festival","Harbinger",
                        "Laboratory","Moneylender","Sentry","Vassal","Village"));
                break;
            case "hand":
                preset = new ArrayList<>(Arrays.asList("Cellar",
                        "Council Room","Festival","Gardens","Library",
                        "Harbinger","Militia","Poacher","Smithy","Throne Room"));
                break;
            case "improv":
                preset = new ArrayList<>(Arrays.asList("Artisan",
                        "Cellar","Market","Merchant","Mine","Moat",
                        "Moneylender","Poacher","Remodel","Witch"));
                break;
            case "sg":
                preset = new ArrayList<>(Arrays.asList("Bandit",
                        "Bureaucrat","Chapel","Harbinger","Laboratory","Merchant",
                        "Mine","Moneylender","Throne Room","Vassal"));
                break;
        }
        for(String name: preset)
        {
            CardGrid.move(name, kingdomCards, chosenCards);
            HashMap<String, String> add = new HashMap<>();
            add.put("type", "addCardGrid");
            add.put("data", ""+name);

            d.broadcast(add);
        }
    }

    public void startClicked()
    {
        if(chosenCards.getCardStack().size() == 10) {
            HashMap<String, String> result = d.simpleCommand("begin");

            if(result.get("type").equals("accepted")) {
                d.setCurrentModule(new ChooseLobby(d, null));
            } else {
                System.exit(32);
            }

            d.setCurrentModule(new Game(d));
        }
    }
    public void leaveClicked()
    {
        HashMap<String, String> result = d.simpleCommand("leave");

        if(result.get("type").equals("accepted")) {
            d.setCurrentModule(new ChooseLobby(d, null));
        } else {
            System.exit(27);
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
                case "getChosenCards":
                    HashMap<String, String> list = new HashMap<>();
                    list.put("type", "setCardGrid");
                    String cardList = "";
                    for(Card C: chosenCards.getCardStack())
                        cardList += C.getName() + ",";
                    list.put("data", cardList);
                    d.broadcast(list);
                    break;

            }
        }
    }
}