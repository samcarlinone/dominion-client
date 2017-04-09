package com.dominion.prog2.ui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by cobra on 4/9/2017.
 */
public class TextList extends UIElement {
    public Color backgroundColor = Color.WHITE;
    public Color hoveredColor = new Color(0xBDBDBD);
    public Color pressedColor = new Color(0x717171);
    public Color borderColor = Color.BLACK;
    public int borderWidth = 1;
    public int paddingWidth = 1;
    public Color color = Color.BLACK;
    public Font font = new Font("default", Font.PLAIN, 12);
    public int stringHeight=12;

    public ArrayList<String> strings = new ArrayList<>();
    public String clicked=null;

    private String hovered;
    private String pressed;

    private int scrollTop=0;

    public TextList(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Graphics g) {
        normalizeScroll();

        Graphics2D g2 = (Graphics2D) g;

        if(backgroundColor.getAlpha() > 0) {
            //Draw Border
            g2.setColor(borderColor);
            g2.fillRect(x, y, width, height);
        }

        //Draw Background
        g2.setColor(backgroundColor);
        g2.fillRect(x + borderWidth, y + borderWidth, width - borderWidth * 2, height - borderWidth * 2);

        //Draw Text
        g2.setColor(color);
        g2.setFont(font);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        FontMetrics font = g2.getFontMetrics();

        int tX = x + borderWidth + paddingWidth;
        int tY = y + borderWidth + paddingWidth - scrollTop;

        for(int i=0; i<strings.size(); i++) {
            g2.fillRect(tX, tY+(i*stringHeight), width-(borderWidth+paddingWidth)*2, 1);
            g2.drawString(strings.get(i), tX, tY+(i*stringHeight)+font.getAscent());
            g2.fillRect(tX, tY+((i+1)*stringHeight), width-(borderWidth+paddingWidth)*2, 1);
        }

        //Draw Scroll Bar
        double sizePercent = (height-(borderWidth+paddingWidth)*2) / (double)(stringHeight*strings.size());
        double sizeStart = (scrollTop-(borderWidth+paddingWidth)) / (double)(stringHeight*strings.size());

        if(sizePercent > 1)
            sizePercent = 1;

        g2.setColor(color);
        g2.fillRect(x+width-8, y, 8, height);
        g2.setColor(backgroundColor);
        g2.fillRect(x+width-7, y+(borderWidth+paddingWidth)+(int)(height*sizeStart), 6, -(borderWidth+paddingWidth)*2+(int)(height*sizePercent));
    }

    private void normalizeScroll() {
        if(scrollTop > stringHeight*strings.size()-(height-(borderWidth)*2))
            scrollTop = stringHeight*strings.size()-(height-(borderWidth)*2);

        if(scrollTop < paddingWidth+borderWidth)
            scrollTop = paddingWidth+borderWidth;
    }

    public void scroll(int scrollTicks, int scrollAmount) {
        scrollTop += scrollTicks * scrollAmount * stringHeight/10;
    }

    //TODO: Allow selection
}
