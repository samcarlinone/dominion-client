package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.InputFilters;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseName extends Module {
    private Driver d;

    private GridPane root;
    private TextField name;
    private Button submit;
    private Label result;

    /**
     * Module for the use to choose name, will check to make sure not already taken
     * @param d Driver
     */
    public ChooseName(Driver d) {
        this.d = d;

        root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);

        name = new TextField();
        name.addEventFilter(KeyEvent.KEY_TYPED, InputFilters.nameFilter());
        name.addEventFilter(KeyEvent.KEY_TYPED, InputFilters.lengthFilter(12));
        name.setPromptText("Nickname");
        name.setFocusTraversable(false);
        root.add(name, 0, 0);

        submit = new Button("Submit");
        submit.setOnMouseClicked(a -> submitClicked());
        GridPane.setHalignment(submit, HPos.CENTER);
        root.add(submit, 0, 1);

        result = new Label("Name Taken");
        result.setVisible(false);
        result.setStyle("-fx-text-fill: #F00");
        GridPane.setHalignment(result, HPos.CENTER);
        root.add(result, 0, 2);

        setScene(new Scene(root, 500, 700));
    }

    public void submitClicked() {
        if(name.getText().length() == 0)
            return;

        HashMap<String, String> name_msg = new HashMap<>();
        name_msg.put("type", "connect");
        name_msg.put("name", name.getText());

        String json = d.comm.getMessage(d.comm.mapToJSON(name_msg));

        if(json.contains("invalid")) {
            result.setVisible(true);
        } else {
            d.name = name.getText();
            d.setCurrentModule(new ChooseLobby(d, false));
        }
    }

    /**
     * Receive a server message
     * @param server_msg
     */
    @Override
    public void serverMsg(ArrayList<HashMap<String, String>> server_msg) {
        //This screen doesn't actually listen to server messages
    }
}
