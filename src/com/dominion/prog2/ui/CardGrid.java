package com.dominion.prog2.ui;

import com.dominion.prog2.game.Card;
import com.dominion.prog2.game.CardInfo;
import com.dominion.prog2.game.CardStack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class CardGrid {
    private CardStack stack;
    private FlowPane list;
    private ScrollPane root;
    public int maxCards = 16;
    private ArrayList<CardSelected> listeners = new ArrayList<>();

    private int cardWidth;
    private boolean collapseSame;
    private boolean sort;

    /**
     * Constructor for CardGrid
     *      Stack: CardStack what cards should be within the Grid
     *      cardWidth: how wide the cards should be within the grid
     *      collapseSame: if true- cards with the same name will only show once
     *                              and the number of those cards will show on top of the card
     *      sort: if true- sorts the card based off price, then alphabetically
     */
    public CardGrid(CardStack stack, int cardWidth, boolean collapseSame, boolean sort) {
        this.stack = stack;
        stack.addListener(() -> stackChanged());
        this.cardWidth = cardWidth;
        this.collapseSame = collapseSame;
        this.sort = sort;

        if(stack.has("Witch"))
            maxCards = 17;

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

        stackChanged();
    }

    /**
     * Another Constructor for CardGrid
     *      collapse is set to false
     *      sort is set to false
     */
    public CardGrid(CardStack stack, int cardWidth) {
        this(stack, cardWidth, false, false);
    }

    /**
     *  Another Constructor for CardGrid
     *      sort is set to false
     */
    public CardGrid(CardStack stack, int cardWidth, boolean collapseSame) { this(stack, cardWidth, collapseSame, false); }

    /**
     *  Method is called when the card is clicked
     *  handles what to do when the cards are clicked
     *  Listeners are notified
     */
    private void handleCardClicked(MouseEvent e) {
        if(e.getSource() instanceof ImageView) {
            Image img = ((ImageView) e.getSource()).getImage();
            String cardName = ((NamedImage)img).getName();

            for(CardSelected listener : listeners) {
                listener.process(cardName);
            }
        }

        if(e.getSource() instanceof StackPane) {
            StackPane group = (StackPane)e.getSource();

            Image img = ((ImageView) group.getChildren().get(0)).getImage();
            String cardName = ((NamedImage)img).getName();

            for(CardSelected listener : listeners) {
                listener.process(cardName);
            }
        }
    }

    /**
     * This method is called when the Stack of the CardGrid is changed
     * Notifies the listeners
     * makes sure the CardGrid is drawing the cards correctly
     */
    private void stackChanged() {
        list.getChildren().clear();

        if(collapseSame) {
            HashMap<String, Integer> counts = stack.getCounts();
            ArrayList<String> names = new ArrayList<>();
            names.addAll(counts.keySet());

            names.sort(new CardInfo.NameComparator());

            for(String name : names) {
                StackPane group = new StackPane();
                group.setOnMouseClicked((event) -> handleCardClicked(event));

                ImageView img = new ImageView();
                img.setImage(ImageCache.cardImage.get(name));
                img.setFitWidth(cardWidth);
                img.setPreserveRatio(true);
                group.getChildren().add(img);

                Label number = new Label(""+counts.get(name));
                number.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-font-size: 20pt; -fx-font-weight: 900;");
                number.setPrefSize(40, 40);
                number.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                number.setAlignment(Pos.CENTER);
                StackPane.setAlignment(number, Pos.BOTTOM_RIGHT);
                group.getChildren().add(number);

                list.getChildren().add(group);
            }
        } else {
            if(sort) {
                ArrayList<Card> cards = stack.getAll();
                ArrayList<String> names = new ArrayList<>();

                for(Card c : cards) {
                    names.add(c.getName());
                }

                names.sort(new CardInfo.NameComparator());

                for (String name : names) {
                    ImageView img = new ImageView();
                    img.setImage(ImageCache.cardImage.get(name));
                    img.setFitWidth(cardWidth);
                    img.setPreserveRatio(true);
                    img.setOnMouseClicked((event) -> handleCardClicked(event));

                    list.getChildren().add(img);
                }
            } else {
                for (Card card : stack) {
                    ImageView img = new ImageView();
                    img.setImage(ImageCache.cardImage.get(card.getName()));
                    img.setFitWidth(cardWidth);
                    img.setPreserveRatio(true);
                    img.setOnMouseClicked((event) -> handleCardClicked(event));

                    list.getChildren().add(img);
                }
            }
        }
    }

    public CardStack getCardStack() { return stack; }

    public ScrollPane getRootPane()
    {
        return root;
    }

    public void addListener(CardSelected listener) { listeners.add(listener); }

    /**
     * Moves a specific card, based off a name from one CardGrid to another
     */
    static public void move(String name, CardGrid source, CardGrid target) {
        Card card = source.getCardStack().get(name);

        if(card == null)
            return;

        source.getCardStack().remove(card);
        target.getCardStack().add(card);
    }
}