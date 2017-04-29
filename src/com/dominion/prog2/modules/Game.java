package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.Card;
import com.dominion.prog2.game.CardInfo;
import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.game.Player;
import com.dominion.prog2.ui.CardGrid;
import com.dominion.prog2.ui.ImageCache;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Module
{
    private Driver d;
    private GridPane root;
    private Player you;
    private BackgroundImage backgroundImage;
    private int turn = 1;

    public ObservableList<String> players;

    private Button popUpSubmit;
    private CardGrid popUpGrid;
    public CardStack popUpStack;
    public boolean popup = false;

    public Label turnAction;
    public Label turnCoin;
    public Label turnBuys;

    public CardGrid hand;
    public CardGrid shop;

    public Game(Driver d, CardStack finalShopList, ObservableList<String> userNames)
    {
        this.d = d;
        this.players = userNames;
        you = new Player(d.name);

        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER_LEFT);

        //Background image
        backgroundImage = new BackgroundImage(new Image("BackgroundTile/RicePaper.jpg"),null,null,null,null);
        Background b = new Background(backgroundImage);
        root.setBackground(b);

        //First Row
        GridPane first = new GridPane();
        Button help = new Button("Instructions");
        help.setOnMouseClicked(a -> helpClicked());
        first.add(help, 0,0);

        //TODO: align properly
        ArrayList<Label> enemies = new ArrayList<>();
        for(String name: players)
        {
            if(!name.equals(d.name))
                enemies.add(new Label(name));
        }

        //TODO: Fix styles of the LABELS
        for(int i = 0; i < enemies.size(); i ++){
            Label l = enemies.get(i);
            l.setStyle("-fx-font-size: 15pt:-fx-border-color: black;");
            first.add(l,i+1,0);
        }

        Label current = new Label("Current Turn: " + players.get(turn));
        current.setStyle("-fx-border-color: black:-fx-font-size: 20pt;");
        first.add(current, players.size()+2,0);


        //Second Row
        GridPane second = new GridPane();
            //shop
        CardStack shoppe = new CardStack();
        for(String name: CardInfo.treasureCardNames) {
            switch(name){
                case "Copper":
                    shoppe.addMultiple(name, 60);
                    break;
                case "Silver":
                    shoppe.addMultiple(name, 40);
                    break;
                case "Gold":
                    shoppe.addMultiple(name, 30);
                    break;
            }
        }
        for(String name: CardInfo.victoryCardNames) {
            switch(name)
            {
                case "Curse":
                    if(finalShopList.has("Witch"))
                        shoppe.addMultiple(name, 30);
                    break;
                case "Estate":
                    shoppe.addMultiple(name, 24);
                    break;
                case "Duchy":
                    shoppe.addMultiple(name, 12);
                    break;
                case "Province":
                    shoppe.addMultiple(name, 12);
                    break;
            }
        }
        for(Card c: finalShopList) {
            String name = c.getName();
            if(name.equals("Garden"))
                shoppe.addMultiple(name,12);
            else
                shoppe.addMultiple(name, 10);
        }

        shop = new CardGrid(shoppe,150, true);
        shop.getRootPane().setPrefWidth(1000);
        shop.getRootPane().setPrefHeight(750);
        second.add(shop.getRootPane(),0,0);



            //if there is something that pops up
        if(popup)
        {
            popUpSubmit = new Button("Submit");
            this.popUpSubmit.setOnMouseClicked(a -> submitPopUp());

            popUpGrid = new CardGrid(popUpStack, popUpStack.size());
        }


        //Third Row
        GridPane third = new GridPane();
            //Stats
        GridPane yourStats = new GridPane();
        yourStats.setAlignment(Pos.CENTER);
        turnAction = new Label("Turn Actions: "+you.turnAction);
        turnAction.setStyle("-fx-font-size: 15pt");
        turnCoin = new Label("Turn Coins: "+you.turnMoney);
        turnCoin.setStyle("-fx-font-size: 15pt");
        turnBuys = new Label("Turn Buys: "+you.turnBuys);
        turnBuys.setStyle("-fx-font-size: 15pt");
        yourStats.add(turnAction, 0,0);
        yourStats.add(turnCoin, 0,1);
        yourStats.add(turnBuys, 0,2);
        third.add(yourStats,0,0);
            //Deck
        ImageView deck = new ImageView();
        deck.setImage(ImageCache.cardImage.get("Card_back"));
        deck.setPreserveRatio(true);
        deck.setFitWidth(100);
        third.add(deck,1,0);
            //Hand
        hand = new CardGrid(you.hand,100);
        hand.getRootPane().setPrefWidth(600);
        hand.getRootPane().setMaxHeight(200);
        third.add(hand.getRootPane(),2,0);
            //Discard
        ImageView discard = new ImageView();
        if(you.discard.size() > 0) {
            discard.setImage(ImageCache.cardImage.get(you.discard.get(0).getName()));
            discard.setPreserveRatio(true);
            discard.setFitWidth(100);
        }
        third.add(discard,3,0);

        Button endTurn = new Button("End turn");
        endTurn.setOnMouseClicked(a -> endTurn());
        third.add(endTurn, 4,0);



        root.add(first,0 ,0);
        root.add(second,0 ,1);
        root.add(third,0 ,2);
        setScene(new Scene(root, 1600, 1000));
    }

    public void endTurn()
    {
        HashMap<String, String> endTurn = new HashMap<>();
        endTurn.put("type", "endTurn");

        d.broadcast(endTurn);
    }

    public void submitPopUp()
    {
        //TODO: complete
    }

    public void helpClicked()
    {
        try {
            Desktop.getDesktop().browse(new URI("http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg)
    {
        for(HashMap<String, String> msg : server_msg) {
            switch (msg.get("type")) {
                case "endTurn":
                    turn ++;
                    if(turn >= players.size())
                        turn = 0;
                    break;
            }
        }
    }
    //TODO: add whose turn
    //TODO: Check to see if game ends
}