package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class CardGrid {
    private CardStack stack;
    private FlowPane list;
    private ScrollPane root;
    private ArrayList<CardSelected> listeners = new ArrayList<>();

    private int cardWidth;

    public CardGrid(CardStack stack, int cardWidth) {
        this.stack = stack;
        this.cardWidth = cardWidth;

        root = new ScrollPane();
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        list = new FlowPane();
        list.setPadding(new Insets(10));
        list.setHgap(10);
        list.setVgap(10);
        root.setContent(list);

        root.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            list.setPrefWrapLength(newValue.getWidth());
        });

        for(int i = 0; i < stack.size(); i ++) {
            ImageView img = new ImageView();
            img.setImage(ImageCache.cardImage.get(stack.get(i).getName()));
            img.setFitWidth(cardWidth);
            img.setPreserveRatio(true);
            img.setOnMouseClicked((event) -> handleCardClicked(event));

            list.getChildren().add(img);
        }
    }

    private void handleCardClicked(MouseEvent e) {
        if(e.getSource() instanceof ImageView) {
            Image img = ((ImageView) e.getSource()).getImage();
            String cardName = ((NamedImage)img).getName();

            for(CardSelected listener : listeners) {
                listener.process(cardName);
            }
        }
    }

    public CardStack getCardStack() { return stack; }

    public ScrollPane getRootPane()
    {
        return root;
    }

    public void addListener(CardSelected listener) { listeners.add(listener); }
}