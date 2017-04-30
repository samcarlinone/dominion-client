package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOver extends Module
{
    private Driver d;
    private Player p;
    private GridPane root;
    private TableView<String> players;
    private ObservableList<String> nameScores;

    public GameOver(ObservableList<String> userNames, Player p, Driver d)
    {
        this.d = d;
        this.p = p;

        if(d.name.equals(userNames.get(0))){
            HashMap<String, String> ready = new HashMap<>();
            ready.put("type", "readyScores");
            d.broadcast(ready);
        }


        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);


        Label end = new Label("Game Over");
        end.setStyle("-fx-border-color: black;-fx-font-size: 25pt;");
        GridPane.setHalignment(end, HPos.CENTER);
        root.add(end,0,0);


        players = new TableView<>();
        nameScores = FXCollections.observableArrayList();

        players.setItems(nameScores);
        players.setMaxSize(200,200);

        TableColumn<String,String> users = new TableColumn<>("User  |   Final Scores");
        users.setPrefWidth(190);
        users.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        players.getColumns().addAll(users);
        root.add(players, 0,1);

        //Buttons
        GridPane buttons = new GridPane();
        Button leave = new Button("Play again");
        leave.setOnMouseClicked(a -> playAgainClicked());
        buttons.add(leave, 0,0);

        Button quit = new Button("Quit Game");
        quit.setOnMouseClicked(a -> System.exit(0));
        buttons.add(quit, 1,0);

        GridPane.setHalignment(buttons, HPos.CENTER);
        root.add(buttons, 0,2);

        setScene(new Scene(root, 745, 700));
    }

    public void playAgainClicked()
    {
        d.setCurrentModule(new ChooseLobby(d, "Game Finished"));
    }


    public void submitScores()
    {
        HashMap<String, String> submit = new HashMap<>();
        submit.put("type", "submitScore");
        submit.put("user", p.name);
        submit.put("score", ""+p.getTotalScore());
        d.broadcast(submit);
    }

    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {
        for(HashMap<String, String> msg : server_msg) {
            switch (msg.get("type")) {
                case "readyScores":
                    submitScores();
                    break;
                case "submitScore":
                    nameScores.add(msg.get("user") + "  |   " + msg.get("score"));
                    break;
            }
        }
    }
}
