package com.dominion.prog2.ui;

import java.awt.*;

/**
 * Created by cobra on 3/27/2017.
 */
public class Label extends UIElement {
    private String text;

    public Label(String text, int x, int y) {
        super(0, 0, 0, 0);
        //TODO: Implement
    }

    /**
     * Render the label
     */
    public void render(Graphics g) {
        //TODO: Implement
    }

    public void setText(String t) { this.text = text; }
    public String getText() { return this.text; }
}
