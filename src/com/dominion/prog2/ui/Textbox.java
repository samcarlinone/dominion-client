package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by cobra on 3/27/2017.
 */
public class Textbox extends UIElement {
    public int maxChars=100;
    public Color backgroundColor = new Color(0xFFFFFF);
    public Color color = new Color(0);

    private int cursorPos;
    private int cursorAnim;
    private StringBuilder text;

    public Textbox(int maxChars, int x, int y, int width, int height) {
        super(x, y, width, height);

        cursorPos = 0;
        cursorAnim = 120;
        text = new StringBuilder();
    }

    /**
     * Render the button
     */
    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(backgroundColor);
        g2.fillRect(x, y, width, height);

        g2.setColor(color);

        FontMetrics font = g2.getFontMetrics();
        String beforeCursor = text.substring(0, cursorPos);
        String afterCursor = text.substring(cursorPos);

        if(cursorAnim < 60) {
            beforeCursor += "|";
        } else {
            beforeCursor += " ";
        }

        int offset = font.stringWidth(beforeCursor);

        if(offset < width)
            g2.drawString(beforeCursor+afterCursor, x, y+font.getAscent());
        else
            g2.drawString(beforeCursor+afterCursor, x+width-offset-font.stringWidth(" "), y+font.getAscent());
    }

    @Override
    public void tick() {
        if(UIManager.get().focusedTextbox != this)
            cursorAnim = 120;

        if(cursorAnim-- == 0)
            cursorAnim = 120;
    }

    /**
     * Gets current typed text
     */
    public String getText() { return text.toString(); }

    /**
     * Call when a character is typed
     */
    public void keyTyped(KeyEvent e) {
        String chr = e.getKeyChar()+"";

        if(chr.matches("[\\w]") && text.length() < maxChars) {
            text.insert(cursorPos++, chr);
            return;
        }

        if(e.getKeyCode() == 127 && cursorPos < text.length())
            text.deleteCharAt(cursorPos);

        if(e.getKeyCode() == 8 && cursorPos > 0)
            text.deleteCharAt(--cursorPos);

        if(e.getKeyCode() == KeyEvent.VK_LEFT && cursorPos > 0)
            cursorPos--;

        if(e.getKeyCode() == KeyEvent.VK_RIGHT && cursorPos < text.length())
            cursorPos++;
    }
}
