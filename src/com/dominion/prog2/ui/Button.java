package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by cobra on 3/27/2017.
 */
public class Button extends UIElement {
    private String text;
    private BufferedImage Image;
    private boolean pressed;
    private boolean clicked=false;
    private boolean startedInside;

    public Button(String img_name, int x, int y) {
        super(0, 0, 0, 0);
        //TODO: Implement
    }

    public Button(String img_name, int x, int y, BufferedImage image) {
        super(0, 0, 0, 0);
        this.Image = image;
        //TODO: Implement
    }
    /**
     * Render the button
     */
    public void render(Graphics g) {
        //TODO: Implement
    }

    /**
     * Get whether the mouse is currently being held on the button
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Set whether the mouse is currently being held on the button
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    /**
     * Has the button been clicked
     */
    public boolean wasClicked() {
        return clicked;
    }

    /**
     * Set whether the button has been clicked
     */
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    /**
     * Handle Input
     */

    public boolean mouseDown(MouseEvent e) {
        //TODO: Handle Mouse Down
        return false;
    }

    public void mouseUp(MouseEvent e) {
        //TODO: Handle Mouse Down
    }

    public void mouseMove(MouseEvent e) {
        //TODO: Handle Mouse Move
    }
}
