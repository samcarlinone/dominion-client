package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.CardInfo;
import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.ui.CardGrid;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HostWaitScreen extends Module
{
    private Driver d;
    private GridPane root;
    private Label label;

    private Button start;
    private Button leave;
    private Button clear;

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

        label = new Label("Hosting: " + LobbyName);
        root.add(label, 0, 0);

        GridPane cardChoosers = new GridPane();
        cardChoosers.setPrefSize(600, 500);
        cardChoosers.setAlignment(Pos.CENTER);

        kingdomCards = new CardGrid(new CardStack(CardInfo.kingdomCardNames),125);
        kingdomCards.getRootPane().setPrefWidth(300);
        kingdomCards.getRootPane().setPrefHeight(500);
        kingdomCards.addListener(cardName -> {
            if(chosenCards.getCardStack().size() < 10)
                CardGrid.move(cardName, kingdomCards, chosenCards);
        });
        cardChoosers.add(kingdomCards.getRootPane(),0,0);

        chosenCards = new CardGrid(new CardStack(),125);
        chosenCards.getRootPane().setPrefWidth(300);
        chosenCards.getRootPane().setPrefHeight(500);
        chosenCards.addListener(cardName -> CardGrid.move(cardName, chosenCards, kingdomCards));
        cardChoosers.add(chosenCards.getRootPane(),1,0);

        root.add(cardChoosers, 0, 1);

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

        root.add(buttons,0,2);


        GridPane presets = new GridPane();
        presets.setAlignment(Pos.CENTER);

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

        root.add(presets,0,3);

        //Add ID of those in Lobby
        //  Be able to kick them
        //Lobby doesn't close for those in lobby besides host
        //Presets

        setScene(new Scene(root, 745, 700));
    }

    public void clearChosen()
    {
        //resets all cards
        while(chosenCards.getCardStack().size()>0)
        {
            String name = chosenCards.getCardStack().get(0).getName();
            chosenCards.move(name, chosenCards, kingdomCards);
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
        }
    }

    public void startClicked()
    {
        d.setCurrentModule(new Game(d));
    }
    public void leaveClicked()
    {
        d.setCurrentModule(new ChooseLobby(d,true));
        //add close down lobby?
    }

    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {

    }
}