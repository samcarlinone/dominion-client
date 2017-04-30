package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.*;
import com.dominion.prog2.ui.*;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
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
    private Group stage;
    private GridPane root;
    private Player you;
    private int youIndex;
    private BackgroundImage backgroundImage;
    private int turn = 1;

    private ObservableList<String> players;
    private ArrayList<Label> playerLabels;

    private CardSelectPopup popup;
    private SelectCards selector;

    private Label turnAction;
    private Label turnCoin;
    private Label turnBuys;

    private CardGrid hand;
    private CardStack shoppe;
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

        stage = new Group();
        root = new GridPane();
        stage.getChildren().add(root);

        //Background image
        backgroundImage = new BackgroundImage(new Image("BackgroundTile/RicePaper.jpg"),null,null,null,null);
        Background b = new Background(backgroundImage);
        root.setBackground(b);

        //First Row
        TilePane first = new TilePane();
        first.setHgap(80);
        first.setPrefColumns(players.size()+1);
        first.prefWidthProperty().bind(root.widthProperty());
        Button help = new Button("Instructions");
        help.setOnMouseClicked(a -> helpClicked());
        first.getChildren().add(help);

        playerLabels = new ArrayList<>();
        for(String name: players) {
            if(!name.equals(d.name))
                playerLabels.add(new Label(name));
            else
                playerLabels.add(new Label(name + " (you)"));
        }

        for(int i = 0; i < playerLabels.size(); i ++){
            Label l = playerLabels.get(i);
            l.setStyle("-fx-font-size: 20pt; -fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #fff");
            l.setPadding(new Insets(0, 6, 0, 6));
            first.getChildren().add(l);
        }
        playerLabels.get(turn).setStyle("-fx-font-size: 20pt; -fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #339ebd");

        //Second Row
        GridPane second = new GridPane();
        //shop
        shoppe = new CardStack();
        addCardsToShop(finalShopList);

        shop = new CardGrid(shoppe,150, true);
        shop.getRootPane().setPrefWidth(1000);
        shop.getRootPane().setPrefHeight(750);
        shop.addListener((cardName -> buyCard(cardName)));
        second.add(shop.getRootPane(),0,0);

        playArea = new ImageView();
        playArea.setPreserveRatio(true);
        playArea.setFitWidth(150);
        second.add(playArea, 1, 0);

        //Third Row
        GridPane third = new GridPane();
        //Stats
        GridPane yourStats = new GridPane();
        yourStats.setStyle("-fx-background-color: #fff; -fx-border-color: black; -fx-border-width: 2px;");
        yourStats.setPadding(new Insets(0, 6, 0, 6));
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


        root.setPrefSize(1600, 1000);
        setScene(new Scene(stage, 1600, 1000));
    }

    public void addCardsToShop(CardStack finalShopList)
    {
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
            updateStats();
            discard.setImage(playArea.getImage());
            discard.setPreserveRatio(true);
            discard.setFitWidth(100);
            playArea.setImage(null);

            endPhase.setText("End Play Phase");
            endPhase.setVisible(false);
        }
    }

    public void selectCards(String msg, CardStack source, SelectCards callback, ValidateCardSelection validate) {
        selector = callback;
        popup = new CardSelectPopup(msg, source, validate, this);
        stage.getChildren().add(popup.getRootPane());
        popup.getRootPane().setPrefSize(500, 400);
    }

    public void popupSubmitted() {
        selector.selected(popup.getSelectedStack(), this);
        stage.getChildren().remove(popup.getRootPane());
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

        updateStats();
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
        boolean noProvinces = !shop.getCardStack().has("Province");
        boolean threeGone = shop.getCardStack().getNumberTypesOfCards() <= shop.maxCards-3;

        return (noProvinces || threeGone);
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
                    playerLabels.get(turn).setStyle("-fx-font-size: 20pt; -fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #fff");
                    turn ++;
                    if(turn >= players.size())
                        turn = 0;
                    playerLabels.get(turn).setStyle("-fx-font-size: 20pt; -fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #339ebd");

                    if(turn == youIndex)
                        endPhase.setVisible(true);

                    if(checkEnd())
                        d.setCurrentModule(new GameOver(players, you, d));

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
                case "disconnect":
                    HashMap<String, String> result = d.simpleCommand("leave");

                    if(result.get("type").equals("accepted")) {
                        d.setCurrentModule(new ChooseLobby(d, "Someone disconnected"));
                    } else {
                        System.exit(49);
                    }
                    break;
            }
        }
    }
    //TODO: Check to see if game ends

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

    public Player getYou() { return you; }

}