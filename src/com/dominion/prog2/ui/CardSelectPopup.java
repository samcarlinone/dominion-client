package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.modules.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    public CardSelectPopup(String message, CardStack source, ValidateCardSelection validator, Game game) {
        this.validator = validator;
        this.game = game;

        this.source = source;
        selected = new CardStack();

        root = new GridPane();
        root.setStyle("-fx-background-color: #fff; -fx-border-width: 6px; -fx-border-color: #000;");

        Label msg = new Label(message);
        msg.setStyle("-fx-font-size: 20px");
        root.add(msg, 0, 0);

        sourceGrid = new CardGrid(source, 150);
        sourceGrid.addListener(cardName -> CardGrid.move(cardName, sourceGrid, selectedGrid));
        selectedGrid = new CardGrid(selected, 150);
        selectedGrid.addListener(cardName -> CardGrid.move(cardName, selectedGrid, sourceGrid));

        root.add(sourceGrid.getRootPane(), 0, 1);
        root.add(selectedGrid.getRootPane(), 1, 1);

        Button choose = new Button("Choose");
        root.add(choose, 0, 2);
        choose.setOnMouseClicked((e) -> chooseClicked());
    }

    private void chooseClicked() {
        if(validator.validate(selected)) {
            game.popupSubmitted();
        }
    }

    public CardStack getSourceStack() { return source; }
    public CardStack getSelectedStack() { return selected; }

    public Pane getRootPane()
    {
        return root;
    }
}
