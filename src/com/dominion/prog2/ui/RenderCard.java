package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderCard extends UIElement {
    private String name;
    private BufferedImage img;

    /**
     * Creates RenderCard object
     * @param name
     * @param x pos
     * @param y pos
     */
    public RenderCard(String name, int x, int y) {
        super(x, y, ImageCache.cardWidth, ImageCache.cardHeight);

        this.name = name;
        img = ImageCache.cardImage.get(name);
    }

    /**
     * Render's the card
     * @param g Graphics
     */
    public void render(Graphics g) { g.drawImage(img, x, y, null); }
}
