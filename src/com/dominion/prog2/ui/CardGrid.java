package com.dominion.prog2.ui;

import com.dominion.prog2.game.Card;
import com.dominion.prog2.game.CardStack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


import java.util.ArrayList;

public class CardGrid
{
    private CardStack stack;
    private GridPane root;
    public int width = 400;
    public int height = 400;

    private TableView<ImageView> list;
    private ObservableList<ImageView> cardList = FXCollections.observableArrayList();
    private ArrayList<TableColumn> col;

    public CardGrid(CardStack c, int numCol)
    {
        stack = c;
        root = new GridPane();
        root.setPrefSize(width,height);

        list = new TableView<>();

        for(int i = 0; i < c.size(); i ++)
        {
            ImageView img = new ImageView();
            Card card = c.get(i);

            img.setImage(ImageCache.cardImage.get(card.getName()));

            cardList.add(img);
        }

        list.setItems(cardList);

        //Sets cols
        col = new ArrayList<>();
        for(int i = 0; i < numCol; i ++)
        {
            TableColumn column = new TableColumn("");
            column.setPrefWidth(width/numCol-1);
            col.add(column);

            list.getColumns().add(col.get(i));
        }

        //Width of the whole grid
        list.setPrefWidth(200 * numCol + 10*(numCol-1));
        list.setPrefHeight(height);
        root.add(list,0,1);
    }

    public void addCard(Card c)
    {
        stack.getAll().add(c);
    }

    public void setHeight(int height)
    {
        list.setPrefHeight(height);
        root.add(list,0,0);
    }

    public GridPane getGridPane()
    {
        return root;
    }
}