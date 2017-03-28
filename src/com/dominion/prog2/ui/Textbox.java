package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by cobra on 3/27/2017.
 */
public class Textbox extends UIElement {
    private int maxChars;
    private StringBuilder text;

    public Textbox(int maxChars, int x, int y, int width, int height) {
        super(x, y, width, height);
        //TODO: Implement
    }

    /**
     * Render the button
     */
    public void render(Graphics g) {
        //TODO: Implement
    }

    /**
     * Gets current typed text
     */
    public String getText() { return text.toString(); }

    /**
     * Call when a character is typed
     */
    public void keyTyped(char c) { }

    /**
     * Handle Input
     */
    public void mouseDown(MouseEvent e) {
        //TODO: Handle Mouse Down
    }

    public void keyDown(KeyEvent e) {
        //TODO: Handle Key Down
    }

    public void keyUp(KeyEvent e) {
        //TODO: Handle Key Up
    }
}
