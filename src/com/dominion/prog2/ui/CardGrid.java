package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CARLINSE1 on 4/3/2017.
 */
public class CardGrid extends UIElement {
    public ArrayList<CardStack> stacks;
    public boolean scrollable;
    public boolean border;
    public Color backgroundColor;

    private double scrollTop;
    private double contentHeight;

    public CardGrid(int x, int y, int w, int h) {
        super(x, y, w, h);

        stacks = new ArrayList<>();
        scrollable = true;
        border = false;
    }

    public void render(Graphics g) {
        HashMap<String, Integer> cardCounts = new HashMap<>();

        for(CardStack s : stacks) {
            cardCounts = CardStack.mergeCounts(cardCounts, s.getCounts());
        }

        String[] names = new String[cardCounts.size()];
        int index=0;
        for(HashMap.Entry<String, Integer> entry : cardCounts.entrySet()) {
            names[index++] = entry.getKey();
        }

        int columns = (int) Math.floor(width / ImageCache.cardWidth);
        double borderSpace = (width - columns*ImageCache.cardWidth) / (double)(columns+1);
        int rows = (int) Math.ceil(cardCounts.size() / (double) columns);

        contentHeight = (rows+1)*borderSpace + rows*ImageCache.cardHeight;

        if(scrollTop + height > contentHeight) {
            scrollTop = contentHeight - height;
        }

        Shape prevClip = g.getClip();

        g.setClip(this);

        if(backgroundColor == null)
            g.setColor(new Color(0));
        else
            g.setColor(backgroundColor);

        g.fillRect(x, y, width, height);

        for(int x=0; x<columns; x++) {
            for(int y=0; y<rows; y++) {
                if(x+y*columns >= names.length) {
                    x = columns;
                    break;
                }

                String name = names[x+y*columns];
                int xPos = (int)Math.round(x*(ImageCache.cardWidth+borderSpace)+borderSpace);
                int yPos = (int)Math.round(y*(ImageCache.cardHeight+borderSpace)+borderSpace - scrollTop);
                g.drawImage(ImageCache.cardImage.get(name), xPos, yPos, null);
            }
        }

        if(border) {
            g.setColor(new Color(255, 255, 255));
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(x, y, width, height);
        }

        g.setClip(prevClip);
    }

    public void scroll(int scrollTicks) {
        if(!this.contains(UIManager.get().getMX(), UIManager.get().getMY()))
            return;

        scrollTop += scrollTicks * 10;

        if(scrollTop < 0)
            scrollTop = 0;
    }
}
