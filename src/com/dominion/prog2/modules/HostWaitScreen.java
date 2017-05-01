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

    private TableView<String> players;
    private ObservableList<String> playerList;

    private CardGrid kingdomCards;
    private CardGrid chosenCards;

    /**
     * Constructor for HostWaitScreen
     *      Parent Class: Module
     * This is the screen where the Host chooses the cards to be played within the Lobby's game
     * There are presets for the host to choose if they want.
     * the Host can kick Players. also the host starts the game
     *
     * Players is the table with the users in the lobby
     * PlayerList is the players that are within the Player table
     *
     * KingdomCards is the cardGrid with all the different cards that the host can add for the game
     *      when clicked, the cards switch over to chosenCards
     * chosenCards is the cardGrid with all the chosenCards for the game
     *      when clicked, the cards switch over to KingdomCards.  There is a max limit of 10 cards
     */
    public HostWaitScreen(Driver d, String LobbyName) {
        this.d = d;

        root = new GridPane();
        root.setPrefSize(600, 600);
        root.setAlignment(Pos.CENTER);

        Label Title = new Label("Hosting: " + LobbyName);
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
        Button presetFirst = new Button("First Game");
        presetFirst.setOnMouseClicked(a -> presetCards("first"));
        presets.add(presetFirst, 1, 0);
        Button presetSize = new Button("Size Distortion");
        presetSize.setOnMouseClicked(a -> presetCards("size"));
        presets.add(presetSize, 2, 0);
        Button presetDeck = new Button("Deck Top");
        presetDeck.setOnMouseClicked(a -> presetCards("deck"));
        presets.add(presetDeck, 3, 0);
        Button presetSleight = new Button("Sleight of Hand");
        presetSleight.setOnMouseClicked(a -> presetCards("hand"));
        presets.add(presetSleight, 4, 0);
        Button presetImprovement = new Button("Improvements");
        presetImprovement.setOnMouseClicked(a -> presetCards("improv"));
        presets.add(presetImprovement, 5, 0);
        Button presetSilver = new Button("Silver & Gold");
        presetSilver.setOnMouseClicked(a -> presetCards("sg"));
        presets.add(presetSilver, 6, 0);

        root.add(presets,0,2);

        GridPane cardChoosers = new GridPane();
        cardChoosers.setPrefSize(600, 500);
        cardChoosers.setAlignment(Pos.CENTER);

        Label kingdomTitle = new Label("Kingdom Cards");
        kingdomTitle.setStyle("-fx-font-size: 18pt");
        cardChoosers.setHalignment(kingdomTitle, HPos.CENTER);
        cardChoosers.add(kingdomTitle, 0, 0);
        Label chosenTitle = new Label("Chosen Cards");
        chosenTitle.setStyle("-fx-font-size: 18pt");
        cardChoosers.setHalignment(chosenTitle, HPos.CENTER);
        cardChoosers.add(chosenTitle, 1, 0);

        kingdomCards = new CardGrid(new CardStack(CardInfo.kingdomCardNames),125, false, true);
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

        chosenCards = new CardGrid(new CardStack(),125, false, true);
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

        Button start = new Button("Start Game");
        start.setOnMouseClicked(a -> startClicked());
        buttons.add(start,1,0);

        Button leave = new Button("Close Lobby");
        leave.setOnMouseClicked(a -> leaveClicked());
        buttons.add(leave,2,0);

        Button clear = new Button("Clear Chosen");
        clear.setOnMouseClicked(a -> clearChosen());
        buttons.add(clear,3,0);

        root.add(buttons,0,4);

        //Player and Kick Section
        players = new TableView<>();
        players.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        playerList = FXCollections.observableArrayList();
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
        root.add(players,0,5);

        Button kick = new Button("Kick Player(s)");
        kick.setOnMouseClicked(a -> kickPlayers());
        GridPane.setHalignment(kick, HPos.CENTER);
        root.add(kick,0,6);

        setScene(new Scene(root, 745, 700));
    }

    /**
     * The host selects players from the table and presses the kick player button
     *      sends a kick message out with the player(s) name
     */
    public void kickPlayers()
    {
        ObservableList<String> selected = players.getSelectionModel().getSelectedItems();

        for(String name : selected) {
            if(name != null) {
                d.simpleCommand("kick", "player_name", name);
            }
        }
    }

    /**
     * This clears the chosenCard CardGrid and moves them back over to KingdomCards
     *      There is a clear button, but also the presets clear the chosenCards before adding the preset cards
     * Sends a message to others in the lobby of which cards were removed
     */
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

    /**
     * Adds a group of preset cards to the ChosenCard from KingdomCards
     * The method takes in a string, which correlates to specific presets
     * sends a message to others in lobby of which cards were added
     */
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

    /**
     * This method is called from the Start button.
     * sends a message out to others in the lobby to start the game
     *
     * changes module in Driver to Game(passes Driver, chosenCard Cardstack, list of players in lobby)
     *
     * The Host must have chosen 10 cards to be in use within the game, and there must be atleast one
     *      other person in the lobby for the Host to start the game
     */
    public void startClicked()
    {
        if(chosenCards.getCardStack().size() == 10 && playerList.size() > 1) {
            HashMap<String, String> result = d.simpleCommand("begin");

            HashMap<String, String> finalCards = new HashMap<>();
            String cards = "";
            for(int i = 0; i < chosenCards.getCardStack().size(); i ++) {
                cards += chosenCards.getCardStack().get(i).getName() + ",";
            }
            finalCards.put("type", "startGame");
            finalCards.put("data", cards);
            d.broadcast(finalCards);

            if(result.get("type").equals("accepted")) {
                d.setCurrentModule(new Game(d, chosenCards.getCardStack(), playerList));
            } else {
                System.exit(32);
            }
        }
    }

    /**
     * This closes the Lobby
     * Sends out a message to the people in lobby that the lobby is closing
     * changes module in Driver to ChooseLobby(passes Driver, null for reason)
     */
    public void leaveClicked()
    {
        HashMap<String, String> result = d.simpleCommand("leave");

        if(result.get("type").equals("accepted")) {
            d.setCurrentModule(new ChooseLobby(d, null));
        } else {
            System.exit(27);
        }
    }

    /**
     * This method parses out the different messages that are passed to the client
     *      Receives the "join" and adds the user to the list of players in lobby
     *      Receives the "disconnect" and remove people from list of players in lobby
     *      Receives the "getChosenCards", which is sent from people in lobby when they first join
     *          this gets them an up to-date copy of what cards the Host has chosen so far
     *          Broadcasts the up to-date copy of ChosenCards
     * @param server_msg the array list of hashmaps representing new messages from the server
     */
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