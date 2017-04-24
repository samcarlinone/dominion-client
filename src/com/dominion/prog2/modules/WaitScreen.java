package com.dominion.prog2.modules;

//import com.dominion.prog2.Driver;
//import com.dominion.prog2.game.Card;
//import com.dominion.prog2.game.CardStack;
//import com.dominion.prog2.ui.*;
//import com.dominion.prog2.ui.Button;
//import com.dominion.prog2.ui.Label;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class WaitScreen implements Module
//{
//    private CardGrid chosenCards;
//    private Label headerChosen;
//
//    private Button leave;
//    private TextList usersWaiting;
//
//    private Font ui_font = new Font("Arial", Font.PLAIN, 30);
//    private Driver d;
//
//    /**
//     * Module for the use to choose the cards that will be in the game, Done only by host of lobby
//     * @param d Driver
//     */
//    public WaitScreen(Driver d)
//    {
//        this.d = d;
//
//
//        chosenCards = new CardGrid(new CardStack(),375-180,50,360,450);
//        UIManager.get().addElement(chosenCards);
//
//        headerChosen = new Label("Chosen cards",375-100,10,200,50);
//        headerChosen.font = new Font("Arial", Font.PLAIN, 20);
//        UIManager.get().addElement(headerChosen);
//
//
//        usersWaiting = new TextList(375-100,560,200,135);
//        usersWaiting.font = new Font("default", Font.PLAIN, 25);
//        usersWaiting.stringHeight = 30;
//        usersWaiting.strings.add("Users in Lobby:");
//        usersWaiting.strings.add("User 1");
//        UIManager.get().addElement(usersWaiting);
//
//
//        leave = new Button("Leave Game",375-100,510,200,40);
//        leave.font = ui_font;
//        UIManager.get().addElement(leave);
//    }
//
//    /**
//     * Updates the module
//     * @param server_msg
//     * @return Module
//     */
//    @Override
//    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
//
//        if(leave.wasClicked()) {
//            UIManager.get().removeAll();
//            return new ChooseLobby(d);
//        }
//
//
//        //TODO: Add more here, reads in users in lobby, also transfers cards that are being used in game
//
//        return this;
//    }
//
//
//
//    /**
//     * Renders everything
//     * @param g Graphics
//     */
//    @Override
//    public void render(Graphics g) {
//        //TODO: Implement
//    }
//}

import com.dominion.prog2.Driver;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class WaitScreen extends Module
{
    private Driver d;
    private GridPane root;
    private Label label;

    public WaitScreen(Driver d) {
        this.d = d;

        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);

        label = new Label("Wait for Start");
        root.add(label, 0, 0);

        setScene(new Scene(root, 745, 700));
    }

    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {

    }
}