package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.ui.CardGrid;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class WaitScreen extends Module
{
    private Driver d;
    private GridPane root;
    private Label Title;

    private Label chosenTitle;
    private CardGrid chosenCards;

    private Button leave;

    public WaitScreen(Driver d, String LobbyName) {
        this.d = d;

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

        chosenCards = new CardGrid(new CardStack(),125);
        chosenCards.getRootPane().setPrefWidth(300);
        chosenCards.getRootPane().setPrefHeight(500);
        cardChoosers.add(chosenCards.getRootPane(),1,1);

        root.add(cardChoosers,0,1);

        //Buttons
        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);

        this.leave = new Button("Leave Lobby");
        this.leave.setOnMouseClicked(a -> leaveClicked());
        buttons.add(this.leave, 0, 0);

        root.add(buttons,0,1);

        //TODO: add which cards are chosen
        //TODO: add who is in lobby


        setScene(new Scene(root, 745, 700));
    }

    public void leaveClicked()
    {
        HashMap<String, String> result = d.simpleCommand("leave");

        if(result.get("type").equals("accepted")) {
            d.setCurrentModule(new ChooseLobby(d,false));
        } else {
            System.exit(28);
        }
    }

    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {

    }
}