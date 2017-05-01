package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.InputFilters;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.HashMap;

public class ChooseName extends Module {
    private Driver d;
    private TextField name;
    private Label result;

    /**
     * Constructor for ChooseName
     *      Parent Class: Module
     * This is the screen where the users choose their username
     * Their username has to be unique, and if there is someone already online with the requested username:
     *      an error will popup and they have to choose a unique username before continuing.
     * TextField name is the area where the user types in their name
     */
    public ChooseName(Driver d) {
        this.d = d;

        GridPane root = new GridPane();
        root.setPrefSize(400, 600);
        root.setAlignment(Pos.CENTER);

        ImageView title = new ImageView();
        title.setImage(new Image("Misc/Logo.jpg"));
        title.setFitWidth(400);
        title.setPreserveRatio(true);
        root.getChildren().add(title);

        name = new TextField();
        name.addEventFilter(KeyEvent.KEY_TYPED, InputFilters.nameFilter());
        name.addEventFilter(KeyEvent.KEY_TYPED, InputFilters.lengthFilter(12));
        name.setPromptText("Nickname");
        GridPane.setHalignment(name,HPos.CENTER);
        name.setMaxWidth(140);
        name.setFocusTraversable(false);
        root.add(name, 0, 1);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(a -> submitClicked());
        GridPane.setHalignment(submit, HPos.CENTER);
        root.add(submit, 0, 2);

        result = new Label("Name Taken");
        result.setVisible(false);
        result.setStyle("-fx-text-fill: #F00");
        GridPane.setHalignment(result, HPos.CENTER);
        root.add(result, 0, 3);

        Label created = new Label("Created by:");
        Label Author1 = new Label("Sam Carlin");
        Label Author2 = new Label("Josh Risinger");
        created.setStyle("-fx-font-size: 12pt");
        Author1.setStyle("-fx-font-size: 15pt");
        Author2.setStyle("-fx-font-size: 15pt");
        GridPane.setHalignment(created, HPos.CENTER);
        GridPane.setHalignment(Author1, HPos.CENTER);
        GridPane.setHalignment(Author2, HPos.CENTER);

        root.add(created,0,4);
        root.add(Author1,0,5);
        root.add(Author2,0,6);

        setScene(new Scene(root, 500, 700));
    }

    /**
     * Submits the requested username to the server
     * if it is not unique, a error will pop up and they have to choose another name to continue
     * if the name is unique:
     *      changes module in Driver to ChooseLobby(passes Driver)
     */
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
            d.setCurrentModule(new ChooseLobby(d, null));
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
