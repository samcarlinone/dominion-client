package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Textbox extends UIElement {
    public Color backgroundColor = Color.WHITE;
    public Color borderColor = Color.BLACK;
    public int borderWidth = 1;
    public int paddingWidth = 1;
    public Color color = Color.BLACK;
    public Font font = new Font("default", Font.PLAIN, 12);

    public boolean allowSpaces=false;
    public boolean submitted;

    private int maxChars;
    private int cursorPos;
    private int cursorAnim;
    private int feedbackAnim;
    private StringBuilder text;

    /**
     * Creates the Textbox Object
     * @param maxChars
     * @param x pos
     * @param y pos
     * @param width
     * @param height
     */
    public Textbox(int maxChars, int x, int y, int width, int height) {
        super(x, y, width, height);

        this.maxChars = maxChars;

        cursorPos = 0;
        cursorAnim = 120;
        text = new StringBuilder();
    }

    /**
     * Renders everything
     * @param g Graphics
     */
    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(borderColor);
        g2.fillRect(x, y, width, height);

        if(feedbackAnim == 0)
            g2.setColor(backgroundColor);
        else {
            g2.setColor(new Color(
                    (int)UIManager.lerp(255, backgroundColor.getRed(), (20 - feedbackAnim) / 20),
                    (int)UIManager.lerp(0, backgroundColor.getGreen(), (20 - feedbackAnim) / 20),
                    (int)UIManager.lerp(0, backgroundColor.getBlue(), (20 - feedbackAnim) / 20)
            ));
        }
        g2.fillRect(x+ borderWidth, y+ borderWidth, width- borderWidth *2, height- borderWidth *2);

        g2.setColor(color);
        g2.setFont(font);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        FontMetrics font = g2.getFontMetrics();
        String beforeCursor = text.substring(0, cursorPos);
        String afterCursor = text.substring(cursorPos);

        if(cursorAnim < 60) {
            beforeCursor += "|";
        } else {
            beforeCursor += " ";
        }

        int offset = font.stringWidth(beforeCursor);

        int tX = x + borderWidth + paddingWidth;
        int tY = y + borderWidth + paddingWidth;

        if(offset < width-(borderWidth*2+paddingWidth*2))
            g2.drawString(beforeCursor+afterCursor, tX, tY+font.getAscent());
        else
            g2.drawString(beforeCursor+afterCursor, tX+width-offset-font.stringWidth(" "), tY+font.getAscent());
    }

    /**
     * Ticks the position of the cursor within the textbox
     */
    @Override
    public void tick() {
        if(UIManager.get().focusedElement != this)
            cursorAnim = 120;

        if(cursorAnim-- == 0)
            cursorAnim = 120;

        if(feedbackAnim > 0)
            feedbackAnim--;
    }

    /**
     * Gets the text within the textbox
     * @return text (String)
     */
    public String getText() { return text.toString(); }

    /**
     * Clears typed text
     */
    public void clearText() {
        text.setLength(0);
        cursorPos = 0;
        submitted = false;
    }

    /**
     * Call when a character is typed
     * @param e
     */
    public void keyTyped(KeyEvent e) {
        String chr = e.getKeyChar()+"";

        if(text.length() < maxChars) {
            if(chr.matches("[\\w]") || (allowSpaces && chr.equals(" "))) {
                text.insert(cursorPos++, chr);
                return;
            }
        }

        //System.out.println(e.getKeyCode());

        switch(e.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                //Ignore Shift
                break;

            case KeyEvent.VK_ENTER:
                submitted = true;
                break;

            case KeyEvent.VK_DELETE:
                if(cursorPos < text.length())
                    text.deleteCharAt(cursorPos);
                break;

            case KeyEvent.VK_BACK_SPACE:
                if(cursorPos > 0)
                    text.deleteCharAt(--cursorPos);
                break;

            case KeyEvent.VK_LEFT:
                if(cursorPos > 0)
                    cursorPos--;
                break;

            case KeyEvent.VK_RIGHT:
                if(cursorPos < text.length())
                    cursorPos++;
                break;

            default:
                feedbackAnim = 20;
                break;
        }
    }
}
