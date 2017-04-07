package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by cobra on 3/27/2017.
 */
public class Button extends UIElement {
    public String text;
    public Color backgroundColor = Color.WHITE;
    public Color hoveredColor = new Color(0xBDBDBD);
    public Color pressedColor = new Color(0x717171);
    public Color borderColor = Color.BLACK;
    public int borderWidth = 1;
    public int paddingWidth = 1;
    public Color color = Color.BLACK;
    public Font font = new Font("default", Font.PLAIN, 12);

    private boolean hovered;
    private boolean pressed;
    private boolean clicked=false;

    public Button(String text, int x, int y, int width, int height) {
        super(x, y, width, height);

        this.text = text;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Draw Border
        g2.setColor(borderColor);
        g2.fillRect(x, y, width, height);

        //Draw Background
        Color bgColor = backgroundColor;

        if(pressed) {
            bgColor = pressedColor;
        } else {
            if(hovered)
                bgColor = hoveredColor;
        }

        g2.setColor(bgColor);
        g2.fillRect(x+borderWidth, y+borderWidth, width-borderWidth*2, height-borderWidth*2);

        //Draw Text
        g2.setColor(color);
        g2.setFont(font);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        FontMetrics font = g2.getFontMetrics();

        int offset = font.stringWidth(text)/2;

        int tX = x + width/2;
        int tY = y + borderWidth + paddingWidth;

        g2.drawString(text, tX - offset, tY+font.getAscent());
    }

    @Override
    public void tick() {
        UIManager manager = UIManager.get();

        hovered = manager.hoveredElement == this;
        pressed = manager.focusedElement == this && hovered;
    }

    public boolean isHovered() { return hovered; }
    public boolean isPressed() {
        return pressed;
    }
    public boolean wasClicked() {
        return clicked;
    }
    public void setClicked(boolean clicked) { this.clicked = clicked; }

}
