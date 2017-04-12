package com.dominion.prog2.ui;

import java.awt.*;


public abstract class UIElement extends Rectangle {
    public int depth=0;

    /**
     * Creates UIElement Object
     * @param x pos
     * @param y pos
     * @param width
     * @param height
     */
    public UIElement(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Render this element
     * @param g2 Graphics
     */
    public abstract void render(Graphics2D g2);

    /**
     * Called 60 times a second
     * Updates the element
     */
    public void tick(){}
}
