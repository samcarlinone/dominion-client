package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.*;
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
import java.util.regex.Pattern;

public class Game extends Module
{
    private Driver d;
    private GridPane root;
    private Player you;
    private int youIndex;
    private BackgroundImage backgroundImage;
    private int turn = 1;

    private ObservableList<String> players;

    private Button popUpSubmit;
    private CardGrid popUpGrid;
    private CardStack popUpStack;
    public boolean popup = false;

    private Label turnAction;
    private Label turnCoin;
    private Label turnBuys;

    private CardGrid hand;
    private CardGrid shop;
    private ImageView discard;
    private ImageView playArea;

    private Button endPhase;

    public Game(Driver d, CardStack finalShopList, ObservableList<String> userNames)
    {
        this.d = d;
        this.players = userNames;
        for(int i=0; i<userNames.size(); i++) {
            if(userNames.get(i).equals(d.name))
                youIndex = i;

        }
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
            l.setStyle("-fx-font-size: 15pt;-fx-border-color: black;");
            first.add(l,i+1,0);
        }

        Label current = new Label("Current Turn: " + players.get(turn));
        current.setStyle("-fx-border-color: black;-fx-font-size: 20pt;");
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
        shop.addListener((cardName -> buyCard(cardName)));
        second.add(shop.getRootPane(),0,0);

        playArea = new ImageView();
        playArea.setPreserveRatio(true);
        playArea.setFitWidth(150);
        second.add(playArea, 1, 0);

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
        hand.addListener((cardName -> playCard(cardName)));
        third.add(hand.getRootPane(),2,0);
        //Discard
        discard = new ImageView();
        if(you.discard.size() > 0) {
            discard.setImage(ImageCache.cardImage.get(you.discard.get(0).getName()));
            discard.setPreserveRatio(true);
            discard.setFitWidth(100);
        }
        third.add(discard,3,0);

        endPhase = new Button("End Play Phase");
        endPhase.setVisible(youIndex == turn);
        endPhase.setOnMouseClicked(a -> endPhase());
        third.add(endPhase, 4,0);

        root.add(first,0 ,0);
        root.add(second,0 ,1);
        root.add(third,0 ,2);
        setScene(new Scene(root, 1600, 1000));
    }

    public void endPhase()
    {
        if(you.actionPhase) {
            you.actionPhase = false;
            endPhase.setText("End Turn");
        } else {
            HashMap<String, String> endTurn = new HashMap<>();
            endTurn.put("type", "endTurn");
            d.broadcast(endTurn);

            you.nextTurn();
            hand.getCardStack().clear();
            hand.getCardStack().add(you.hand.getAll());

            discard.setImage(playArea.getImage());

            endPhase.setText("End Play Phase");
            endPhase.setVisible(false);
        }
    }

    public void submitPopUp()
    {
        //TODO: complete
    }

    private void playCard(String name) {
        if(turn == youIndex && you.actionPhase) {
            Card tryPlay = you.hand.get(name);

            if(!(tryPlay instanceof VictoryCard)) {
                if(tryPlay instanceof TreasureCard) {
                    playSpecificCard(tryPlay);
                } else {
                    if(you.turnAction > 0) {
                        playSpecificCard(tryPlay);
                        you.turnAction--;
                    }
                }
            }
        }
    }

    private void playSpecificCard(Card played) {
        if(played instanceof TreasureCard)
            ((TreasureCard)played).play(you, this);
        else
            ((ActionCard)played).play(you, this);

        updateStats();

        //BroadCast Action
        HashMap<String, String> buy = new HashMap<>();
        buy.put("type", "played");
        buy.put("player", players.get(turn));
        buy.put("cardName", played.getName());
        d.broadcast(buy);

        you.played.add(you.hand.remove(played));
        playArea.setImage(ImageCache.cardImage.get(played.getName()));
    }

    private void buyCard(String name) {
        if(turn == youIndex && !you.actionPhase) {
            Card wanted = shop.getCardStack().get(name);

            if(you.turnBuys > 0 && wanted.getPrice() <= you.turnMoney) {
                you.discard.add(shop.getCardStack().remove(wanted));

                you.turnMoney -= wanted.getPrice();
                you.turnBuys--;

                updateStats();

                //BroadCast Buys
                HashMap<String, String> buy = new HashMap<>();
                buy.put("type", "bought");
                buy.put("player", players.get(turn));
                buy.put("cardName", name);
                d.broadcast(buy);

                if(you.turnBuys == 0) {
                    endPhase();
                }
            }

        }
    }

    public boolean checkEnd()
    {
        //TODO: Check if the game ends
        return false;
    }

    public void updateStats()
    {
        turnAction.setText("Turn Actions: "+you.turnAction);
        turnCoin.setText("Turn Coins: "+you.turnMoney);
        turnBuys.setText("Turn Buys: "+you.turnBuys);
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

                    if(turn == youIndex)
                        endPhase.setVisible(true);

                    if(checkEnd())
                        d.setCurrentModule(new GameOver(players, null));

                    playArea.setImage(null);
                    break;
                case "bought":
                    String bought = msg.get("cardName");
                    String Buyer = msg.get("player");
                    if(!Buyer.equals(you.name))
                        shop.getCardStack().remove(bought);
                    break;
                case "played":
                    String played = msg.get("cardName");
                    String Player = msg.get("player");
                    if(!Player.equals(you.name))
                        playArea.setImage(ImageCache.cardImage.get(played));
                    break;
            }
        }
    }
    //TODO: Check to see if game ends
    //TODO: add to discard pile, and show top Card

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

}