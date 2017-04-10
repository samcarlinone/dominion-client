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

public class WaitScreen implements Module
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
    private boolean host;

    /**
     * Module for the use to choose the cards that will be in the game
     * @param d Driver
     * @param host boolean
     */
    public WaitScreen(Driver d, boolean host)
    {
        this.d = d;
        this.host = host;


        if(host)
        {
            CardStack fullList =  fillFullList();

            allCards = new CardGrid(fullList,5,50,240,450);
            UIManager.get().addElement(allCards);

            chosenCards = new CardGrid(new CardStack(),255,50,240,450);
            UIManager.get().addElement(chosenCards);

            startGame = new Button("Start Game",5,510,200,40);
            startGame.font = ui_font;
            UIManager.get().addElement(startGame);

            headerAll = new Label("Choose you cards",5,10,200,50);
            headerAll.font = new Font("Arial", Font.PLAIN, 20);
            UIManager.get().addElement(headerAll);

            headerChosen = new Label("Chosen cards",255,10,200,50);
            headerChosen.font = new Font("Arial", Font.PLAIN, 20);
            UIManager.get().addElement(headerChosen);

        }

        usersWaiting = new TextList(245-(200/2) +5,560,200,135);
        usersWaiting.font = new Font("default", Font.PLAIN, 25);
        usersWaiting.stringHeight = 30;
        usersWaiting.strings.add("Users in Lobby:");
        usersWaiting.strings.add("User 1");
        UIManager.get().addElement(usersWaiting);


        leave = new Button("Leave Game",255,510,200,40);
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
