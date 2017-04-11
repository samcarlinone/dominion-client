package com.dominion.prog2.ui;

import com.dominion.prog2.game.Card;
import com.dominion.prog2.game.CardStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class CardGrid extends UIElement {
    public CardStack stack;
    public Card lastClicked;
    public boolean scrollable;
    public boolean condense;
    public boolean border;
    public Color backgroundColor;

    private double scrollTop;
    private double contentHeight;
    private HashMap<String, Integer> cardCounts;
    private String[] names;
    private int columns;
    private double borderSpace;
    private int rows;

    /**
     * Creates a CardGrid Object
     * @param s CardStack
     * @param x pos
     * @param y pos
     * @param w
     * @param h
     */
    public CardGrid(CardStack s, int x, int y, int w, int h) {
        super(x, y, w, h);

        stack = s;

        scrollable = true;
        condense = true;
        border = false;
    }

    /**
     * Renders everything
     * @param g Graphics
     */
    public void render(Graphics g) {
        updateCardVars();

        Shape prevClip = g.getClip();
        g.setClip(this);
        Graphics2D g2 = (Graphics2D) g;

        if(backgroundColor == null)
            g.setColor(new Color(0));
        else
            g.setColor(backgroundColor);

        g.fillRect(x, y, width, height);

        if(stack.size() > 0) {
            g2.translate(x, y);

            for (int x = 0; x < columns; x++) {
                for (int y = 0; y < rows; y++) {
                    if (x + y * columns >= names.length) {
                        x = columns;
                        break;
                    }

                    String name = names[x + y * columns];
                    int xPos = (int) Math.round(x * (ImageCache.cardWidth + borderSpace) + borderSpace);
                    int yPos = (int) Math.round(y * (ImageCache.cardHeight + borderSpace) + borderSpace - scrollTop);
                    g2.drawImage(ImageCache.cardImage.get(name), xPos, yPos, null);

                    if(condense) {
                        yPos += ImageCache.cardHeight / 2 - 15;
                        g2.setColor(new Color(0));
                        g2.fillRect(xPos, yPos, 30, 30);
                        g2.setColor(new Color(0xFFFFFF));
                        g2.setFont(new Font("default", Font.BOLD, 25));

                        FontMetrics f = g2.getFontMetrics();
                        String text = "x" + cardCounts.get(name);
                        g2.drawString(text, xPos + 15 - f.stringWidth(text) / 2, yPos + 23);
                    }
                }
            }

            g2.translate(-x, -y);
        }

        if(border) {
            g.setColor(new Color(255, 255, 255));
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(x, y, width, height);
        }

        g.setClip(prevClip);
    }

    /**
     * Updates the values of the Card
     */
    private void updateCardVars() {
        cardCounts = stack.getCounts();

        names = new String[cardCounts.size()];
        int index=0;
        for(HashMap.Entry<String, Integer> entry : cardCounts.entrySet()) {
            names[index++] = entry.getKey();
        }

        columns = (int) Math.floor(width / ImageCache.cardWidth);
        borderSpace = (width - columns*ImageCache.cardWidth) / (double)(columns+1);
        rows = (int) Math.ceil(cardCounts.size() / (double) columns);

        contentHeight = (rows+1)*borderSpace + rows*ImageCache.cardHeight;

        if(scrollTop + height > contentHeight) {
            scrollTop = contentHeight - height;
        }

        if(scrollTop < 0)
            scrollTop = 0;
    }

    /**
     * Scrolls so as to see other cards within the grid
     * @param scrollTicks
     * @param scrollAmount
     */
    public void scroll(int scrollTicks, int scrollAmount) {
       if(scrollable) {
           scrollTop += scrollTicks * scrollAmount * 10;

           if (scrollTop < 0)
               scrollTop = 0;
       }
    }

    /**
     * Clicks on a card based off the mouse position
     * @param mX mouse X pos
     * @param mY mouse Y pos
     */
    public void click(int mX, int mY) {
        if(!this.contains(UIManager.get().getMX(), UIManager.get().getMY()))
            return;

        mX -= x;
        mY -= y;

        System.out.println(mX);

        updateCardVars();

        int column = (int)Math.round((mX-borderSpace)/(ImageCache.cardWidth+borderSpace));

        if(column == columns)
            column --;

        int row = (int)Math.round((mY+scrollTop-borderSpace)/(ImageCache.cardHeight+borderSpace));

        if(row == rows)
            row --;

        int nameNum = column+row*columns;

        if(nameNum >= names.length)
            return;

        String name = names[nameNum];

        for(int i=0; i<stack.size(); i++) {
            if(name.equals(stack.get(i).getName())) {
                lastClicked = stack.get(i);
            }
        }
    }
}
