package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.modules.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CardSelectPopup {
    private CardStack source;
    private CardStack selected;

    private GridPane root;
    private CardGrid sourceGrid;
    private CardGrid selectedGrid;

    private ValidateCardSelection validator;

    private Game game;

    /**
     * Constructor for a popUp within Game
     * Message: tells the user what to do
     * Source: where the cards for CardGrid comes from
     * Validator: conditions that need to be met
     * Game
     */
    public CardSelectPopup(String message, CardStack source, ValidateCardSelection validator, Game game) {
        this.validator = validator;
        this.game = game;

        this.source = source;
        selected = new CardStack();

        root = new GridPane();
        root.setStyle("-fx-background-color: #ffffff; -fx-border-width: 6px; -fx-border-color: #8c000a;");
        root.getColumnConstraints().add(new ColumnConstraints());
        root.getColumnConstraints().get(0).setPercentWidth(50);

        Label msg = new Label(message);
        msg.setStyle("-fx-font-size: 18pt");
        root.add(msg, 0, 0);

        sourceGrid = new CardGrid(source, 150, true);
        sourceGrid.addListener(cardName -> CardGrid.move(cardName, sourceGrid, selectedGrid));
        selectedGrid = new CardGrid(selected, 150, true);
        selectedGrid.addListener(cardName -> CardGrid.move(cardName, selectedGrid, sourceGrid));

        root.add(sourceGrid.getRootPane(), 0, 1);
        root.add(selectedGrid.getRootPane(), 1, 1);

        Button choose = new Button("Choose");
        choose.setStyle("-fx-font-size: 20pt");
        root.add(choose, 0, 2);
        choose.setOnMouseClicked((e) -> chooseClicked());
    }

    /**
     * Method called when choose button clicked
     * makes sure the conditions are met before moving on
     */
    private void chooseClicked() {
        if(validator.validate(selected, game)) {
            game.popupSubmitted();
        }
    }
    public CardStack getSelectedStack() { return selected; }

    public Pane getRootPane()
    {
        return root;
    }
}
