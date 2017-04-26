package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
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
    private Label label;

    private Button leave;

    public WaitScreen(Driver d, String LobbyName) {
        this.d = d;

        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);

        label = new Label("Lobby: " + LobbyName + " | Waiting to Start");
        label.setStyle("-fx-font-size: 18pt");
        root.add(label, 0, 0);

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);

        this.leave = new Button("Leave Lobby");
        this.leave.setOnMouseClicked(a -> leaveClicked());
        buttons.add(this.leave, 1, 0);

        root.add(buttons,0,1);

        //TODO: add which cards are chosen
        //TODO: add who is in lobby


        setScene(new Scene(root, 745, 700));
    }

    public void leaveClicked()
    {
        HashMap<String, String> join_msg = new HashMap<>();
        join_msg.put("type", "leave");
        join_msg.put("name", d.name);

        String json = d.comm.getMessage(d.comm.mapToJSON(join_msg));
        HashMap<String, String> result = d.comm.JSONToMap(json).get(0);

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