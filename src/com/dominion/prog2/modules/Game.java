package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.*;
import com.dominion.prog2.ui.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

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

    public CardSelectPopup popup;
    private SelectCards selector;

    private Label turnAction;
    private Label turnCoin;
    private Label turnBuys;

    private CardGrid hand;
    public CardStack shoppe;
    public CardGrid shop;
    private ImageView discard;
    private ImageView playArea;

    private Button endPhase;

    /**
     * Constructor for Game module
     * @param d Driver access for communications
     * @param finalShopList a CardStack containing the cards chosen by the host for this game
     * @param userNames a list of names of players that were in the lobby
     */
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

        for(Label l : playerLabels){
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
        you.played.addListener(() -> {
            if(you.played.size() > 0)
                playArea.setImage(ImageCache.cardImage.get(you.played.get(you.played.size()-1).getName()));
            else
                playArea.setImage(null);
        });
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
        third.add(yourStats,2,0);
        //Deck
        ImageView deck = new ImageView();
        deck.setImage(ImageCache.cardImage.get("Card_back"));
        deck.setPreserveRatio(true);
        deck.setFitWidth(100);
        third.add(deck,0,0);
        //Hand
        hand = new CardGrid(you.hand,100);
        hand.getRootPane().setPrefWidth(600);
        hand.getRootPane().setMaxHeight(200);
        hand.addListener((cardName -> playCard(cardName)));
        third.add(hand.getRootPane(),1,0);
        //Discard
        discard = new ImageView();
        you.discard.addListener(() -> {
            if(you.discard.size() > 0)
                discard.setImage(ImageCache.cardImage.get(you.discard.get(you.discard.size()-1).getName()));
            else
                discard.setImage(null);
        });
        discard.setPreserveRatio(true);
        discard.setFitWidth(100);
        third.add(discard,3,0);

        endPhase = new Button("End Play Phase");
        endPhase.setStyle("-fx-font-size: 20pt");
        endPhase.setVisible(youIndex == turn);
        endPhase.setOnMouseClicked(a -> endPhase());
        third.add(endPhase, 4,0);

        root.add(first,0 ,0);
        root.add(second,0 ,1);
        root.add(third,0 ,2);

        root.setPrefSize(1600, 1000);
        setScene(new Scene(stage, 1600, 1000));
    }

    /**
     * Takes the cards passed in from the wait screen and populates the shop
     */
    private void addCardsToShop(CardStack finalShopList)
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

    /**
     * Called when the user is done the action phase and the buy phase
     * Called when button clicked or may be
     * triggered automatically based on various conditions
     * such as no remaining treasure, buys, etc.
     */
    private void endPhase()
    {
        if(popup == null) {
            if (you.actionPhase) {
                you.actionPhase = false;
                endPhase.setText("End Turn");
            } else {
                HashMap<String, String> endTurn = new HashMap<>();
                endTurn.put("type", "endTurn");
                d.broadcast(endTurn);

                you.nextTurn();
                updateStats();
                discard.setPreserveRatio(true);
                discard.setFitWidth(100);

                endPhase.setText("End Play Phase");
                endPhase.setVisible(false);
            }
        }
    }

    /**
     * Initializes and presents and appropriate popup when user is required to select cards
     */
    public void selectCards(String msg, CardStack source, SelectCards callback, ValidateCardSelection validate) {
        selector = callback;
        popup = new CardSelectPopup(msg, source, validate, this);
        stage.getChildren().add(popup.getRootPane());
        popup.getRootPane().setPrefSize(800, 600);
        popup.getRootPane().setLayoutX(200);
        popup.getRootPane().setLayoutY(200);
    }

    /**
     * This method is called when popup has selected a valid stack of cards
     * Creates new popups if action cards are queued (when Throne Room is used)
     */
    public void popupSubmitted() {
        CardStack stack = popup.getSelectedStack();
        stage.getChildren().remove(popup.getRootPane());
        popup = null;
        selector.selected(stack,this);

        if(you.queuedPlayCards.size() > 0 && popup == null) {
            ActionCard c = you.queuedPlayCards.get(0);
            you.queuedPlayCards.remove(c);

            if(you.played.has(c)) {
                you.hand.add(you.played.remove(c));
            }

            playSpecificCard(c);
        }
    }

    /**
     * Validates an attempted play from hand
     */
    private void playCard(String name) {
        if(turn == youIndex && you.actionPhase && popup == null) {
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

                //Ends the play phase if:
                //1. Hand is empty
                //2. Player has no TurnActions, and no Treasure
                //3. Hand only has Victory Cards
                CardStack tempHand = hand.getCardStack();
                if(tempHand.size() == 0)
                    endPhase();
                else if(you.turnAction == 0 && !tempHand.has(CardType.TREASURE))
                    endPhase();
                else if(!tempHand.has(CardType.TREASURE) && !tempHand.has(CardType.ACTION)
                        && !tempHand.has(CardType.REACTION) && !tempHand.has(CardType.ATTACK))
                    endPhase();
            }
        }

        updateStats();
    }

    /**
     * Actually plays a specified card
     * Called from playCard and various other action cards
     */
    private void playSpecificCard(Card played) {
        you.played.add(you.hand.remove(played));
        animateCardPlayed(you.name, played.getName());

        if(played instanceof TreasureCard)
            ((TreasureCard)played).play(you, this);
        else
            ((ActionCard)played).play(you, this);

        //BroadCast Action
        HashMap<String, String> play = new HashMap<>();
        play.put("type", "played");
        play.put("player", players.get(turn));
        play.put("cardName", played.getName());
        d.broadcast(play);
        
        updateStats();
    }

    /**
     * Attempts to buy a card and place it in a users discard
     * Performs all necessary validation
     */
    private void buyCard(String name) {
        if(turn == youIndex && !you.actionPhase) {
            Card wanted = shop.getCardStack().get(name);

            if(you.turnBuys > 0 && wanted.getPrice() <= you.turnMoney) {
                you.discard.add(shop.getCardStack().remove(wanted));
                animateCardFromStore(you.name, name);

                you.turnMoney -= wanted.getPrice();
                you.turnBuys--;

                updateStats();

                //BroadCast Buys
                HashMap<String, String> buy = new HashMap<>();
                buy.put("type", "bought");
                buy.put("player", players.get(turn));
                buy.put("cardName", name);
                d.broadcast(buy);

                checkEnd();

                if(you.turnBuys == 0) {
                    endPhase();
                }
            }

        }
    }

    /**
     * Only called from various action cards
     * similar to buy, but no validation required
     */
    public void gainCard(String name) {
        //Move card
        you.discard.add(shoppe.remove(shoppe.get(name)));
        animateCardFromStore(you.name, name);
        //BroadCast Gains
        HashMap<String, String> buy = new HashMap<>();
        buy.put("type", "gained");
        buy.put("player", players.get(turn));
        buy.put("cardName", name);
        d.broadcast(buy);
    }

    /**
     * Creates a card animation when a card is played by any user
     */
    public void animateCardPlayed(String playerName, String cardName) {
        if(playerName.equals(you.name)) {
            animateImage(cardName, hand.getRootPane(), playArea);
        } else {
            Label playerLabel=new Label();

            for(Label l : playerLabels) {
                if(l.getText().equals(playerName)) {
                    playerLabel = l;
                    break;
                }
            }

            animateImage(cardName, playerLabel, playArea);
        }
    }

    /**
     * Creates a card animation when a user gets a card from the store
     * Either through a purchase or gain via action card
     */
    public void animateCardFromStore(String playerName, String cardName) {
        if(playerName.equals(you.name)) {
            animateImage(cardName, shop.getRootPane(), discard);
        } else {
            Label playerLabel=new Label();

            for(Label l : playerLabels) {
                if(l.getText().equals(playerName)) {
                    playerLabel = l;
                    break;
                }
            }

            animateImage(cardName, shop.getRootPane(), playerLabel);
        }
    }

    /**
     * Animate an image of a specified card from start node's position to end node's position
     */
    public void animateImage(String cardName, Node start, Node end) {
        ImageView image = new ImageView();
        image.setImage(ImageCache.cardImage.get(cardName));
        image.setPreserveRatio(true);
        image.setFitWidth(100);
        stage.getChildren().add(image);

        Bounds begin = start.localToScene(start.getBoundsInLocal());
        image.setLayoutX(begin.getMinX());
        image.setLayoutY(begin.getMinY());
        Bounds finish = end.localToScene(end.getBoundsInLocal());

        Timeline timeline = new Timeline();
        int time = (int)(Math.random()*500) + 500;
        //Target Pos
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(time), new KeyValue(image.layoutXProperty(), finish.getMinX())));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(time), new KeyValue(image.layoutYProperty(), finish.getMinY())));
        //Hide Image (we must hide first, then wait before deleting, otherwise javafx will not properly repaint the scene)
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(time+2), event -> {
            stage.getChildren().remove(image);
            //Scene repainting hackery
            stage.setTranslateX(Math.random()*0.01);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(time+20), event -> {
            stage.getChildren().remove(image);
            stage.setTranslateX(0);
        }));
        //Begin Anim
        timeline.play();
    }

    /**
     * Checks the various game over conditions and broadcasts if game is over
     */
    private void checkEnd()
    {
        boolean noProvinces = !shop.getCardStack().has("Province");
        boolean threeGone = shop.getCardStack().getNumberTypesOfCards() <= shop.maxCards-3;

        if(noProvinces || threeGone)
        {
            HashMap<String, String> endGame = new HashMap<>();
            endGame.put("type", "endGame");
            d.broadcast(endGame);
        }
    }

    /**
     * Updates the labels displaying user stats for the turn
     */
    public void updateStats()
    {
        turnAction.setText("Turn Actions: "+you.turnAction);
        turnCoin.setText("Turn Coins: "+you.turnMoney);
        turnBuys.setText("Turn Buys: "+you.turnBuys);
    }

    /**
     * Handle messages from the server / other players
     * Basic stuff, also handles the parts of attack cards that
     * affect everyone else
     */
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

                    checkEnd();

                    playArea.setImage(null);
                    break;
                case "endGame":
                        d.setCurrentModule(new GameOver(players, you, d));
                    break;
                case "bought":
                    String bought = msg.get("cardName");
                    String buyer = msg.get("player");

                    if(!buyer.equals(you.name)) {
                        shop.getCardStack().remove(bought);
                        animateCardFromStore(buyer, bought);
                    }
                    break;
                case "gained":
                    String gained = msg.get("cardName");
                    String gainer = msg.get("player");
                    if(!gainer.equals(you.name)) {
                        shoppe.remove(gained);
                        animateCardFromStore(gainer, gained);
                    }
                    break;
                case "played":
                    String played = msg.get("cardName");
                    String player = msg.get("player");

                    if(!player.equals(you.name))
                    {
                        playArea.setImage(ImageCache.cardImage.get(played));
                        animateCardPlayed(player, played);

                        if(played.equals("Council Room"))
                            you.pickUpCards(1);

                        //Handle Attack Cards
                        if(!you.hand.has("Moat")) {
                            switch (played) {
                                case "Bureaucrat":
                                    for (int i = 0; i < you.hand.size(); i++) {
                                        Card c = you.hand.get(i);
                                        if (c instanceof VictoryCard) {
                                            you.deck.add(you.hand.remove(c));
                                            break;
                                        }
                                    }
                                    break;
                                case "Militia":
                                    if(you.hand.size() > 3) {
                                        selectCards("You must pick down to three", you.hand,
                                                ((stack, game) -> {
                                                    game.getYou().discard.add(stack.getAll());
                                                }),
                                                ((stack, game) -> game.getYou().hand.size() == 3));
                                    }
                                    break;
                                case "Witch":
                                    Card c = shoppe.remove("Curse");
                                    you.discard.add(c);
                                    break;
                                case "Bandit":
                                    CardStack top2 = new CardStack(you.deck.splice(0, 2));

                                    if(top2.has("Silver")) {
                                      top2.remove("Silver");
                                      you.discard.add(top2.get(0));
                                      break;
                                    }

                                    if(top2.has("Gold")) {
                                        top2.remove("Gold");
                                        you.discard.add(top2.get(0));
                                        break;
                                    }

                                    you.discard.add(top2.getAll());
                                    break;
                            }
                        }
                    }
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

    /**
     * Open the pdf containing the board game instructions
     */
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

    /**
     * Get the player object, used from lambdas in card actions
     */
    public Player getYou() { return you; }

    /**
     * Gets the shop, also used in card action lambdas
     */
    public CardStack getShoppe(){
        return shoppe;
    }

    /**
     * Get the driver
     */
    public Driver getDriver() {
        return d;
    }
}