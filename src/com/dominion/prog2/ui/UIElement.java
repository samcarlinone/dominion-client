package com.dominion.prog2.ui;

import java.awt.*;

/**
 * Created by cobra on 3/27/2017.
 */
public abstract class UIElement extends Rectangle {
    public UIElement(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Render this element, pass in graphics object
     */
    public abstract void render(Graphics g);
}
