package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by CARLINSE1 on 4/3/2017.
 */
public class RenderCard extends UIElement {
    private String name;
    private BufferedImage img;

    public RenderCard(String name, int x, int y) {
        super(x, y, ImageCache.cardWidth, ImageCache.cardHeight);

        this.name = name;
        img = ImageCache.cardImage.get(name);
    }

    public void render(Graphics g) { g.drawImage(img, x, y, null); }
}
