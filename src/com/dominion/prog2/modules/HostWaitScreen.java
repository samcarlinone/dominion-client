package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.Card;
import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.ui.*;
import com.dominion.prog2.ui.Button;
import com.dominion.prog2.ui.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HostWaitScreen implements Module
{
    private CardGrid allCards;
    private CardGrid chosenCards;
    private Label headerAll;
    private Label headerChosen;
    private Button startGame;
    private Button leave;
    private TextList usersWaiting;

    private Font ui_font = new Font("Arial", Font.PLAIN, 30);
    private Driver d;

    /**
     * Module for the use to choose the cards that will be in the game, Done only by host of lobby
     * @param d Driver
     */
    public HostWaitScreen(Driver d)
    {
        this.d = d;


        CardStack fullList =  fillFullList();

        allCards = new CardGrid(fullList,5,50,360,450);
        allCards.condense = false;
        UIManager.get().addElement(allCards);

        chosenCards = new CardGrid(new CardStack(),375,50,360,450);
        chosenCards.condense = false;
        UIManager.get().addElement(chosenCards);

        headerAll = new Label("Choose you cards",5,10,200,50);
        headerAll.font = new Font("Arial", Font.PLAIN, 20);
        UIManager.get().addElement(headerAll);

        headerChosen = new Label("Chosen cards",375,10,200,50);
        headerChosen.font = new Font("Arial", Font.PLAIN, 20);
        UIManager.get().addElement(headerChosen);


        usersWaiting = new TextList(365-(200/2) +5,560,200,135);
        usersWaiting.font = new Font("default", Font.PLAIN, 25);
        usersWaiting.stringHeight = 30;
        usersWaiting.strings.add("Users in Lobby:");
        usersWaiting.strings.add("User 1");
        usersWaiting.strings.add("User 2");
        usersWaiting.strings.add("User 3");
        usersWaiting.strings.add("User 4");
        usersWaiting.strings.add("User 5");
        UIManager.get().addElement(usersWaiting);


        startGame = new Button("Start Game",375-200,510,200,40);
        startGame.font = ui_font;
        UIManager.get().addElement(startGame);

        leave = new Button("Leave Game",375,510,200,40);
        leave.font = ui_font;
        UIManager.get().addElement(leave);
    }

    /**
     * Updates the module
     * @param server_msg
     * @return Module
     */
    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {

        if(leave.wasClicked()) {
            UIManager.get().removeAll();
            return new ChooseLobby(d);
        }
        if(startGame.wasClicked()) {
            UIManager.get().removeAll();
            return new Game(d);
        }

        if(allCards.lastClicked != null) {
            if(chosenCards.stack.size() < 10) {
                chosenCards.stack.add(allCards.stack.remove(allCards.lastClicked));
            }
            allCards.lastClicked = null;
        }
        if(chosenCards.lastClicked != null) {
            allCards.stack.add(chosenCards.stack.remove(chosenCards.lastClicked));
            chosenCards.lastClicked = null;
        }



        //TODO: Add more here, reads in users in lobby, also transfers cards that are being used in game

        return this;
    }

    /**
     * Fills the full list of cards
     */
    private CardStack fillFullList()
    {
        CardStack full = new CardStack();

        String[] CardNames =
                {
                        "Artisan","Bandit","Bureaucrat",
                        "Cellar","Chapel", "Council Room",
                        "Curse","Festival","Gardens",
                        "Harbinger",
                        "Laboratory","Library","Market",
                        "Merchant","Militia","Mine",
                        "Moat","Moneylender","Poacher",
                        "Remodel","Sentry",
                        "Smithy","Throne Room",
                        "Vassal","Village",
                        "Witch","Workshop"
                };

        for(String name: CardNames)
            full.add(new Card(name));

        return full;
    }

    /**
     * Renders everything
     * @param g Graphics
     */
    @Override
    public void render(Graphics g) {
        //TODO: Implement
    }
}
