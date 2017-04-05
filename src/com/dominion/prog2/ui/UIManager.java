package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 * Created by cobra on 3/27/2017.
 */
public class UIManager {
    /**
     * This is a singleton that handles rendering and input management
     */
    private static UIManager instance = null;
    protected UIManager() {
        elements = new ArrayList<>();
    }

    public static UIManager get(){
        if(instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    private ArrayList<UIElement> elements;
    private UIElement focused;

    private int mX;
    private int mY;

    /**
     * Add a new UIElement
     */
    public void addElement(UIElement element) {
        elements.add(element);
    }

    /**
     * Remove an UIElement, returns the element
     */
    public UIElement removeElement(UIElement element) {
        elements.remove(element);
        return element;
    }

    /**
     * Removes all UIElements
     */
    public void removeAll() {
        elements.clear();
        //TODO: Handle additional logic
    }

    /**
     * Render all elements
     */
    public void render(Graphics g) {
        for(UIElement e : elements) {
            e.render(g);
        }
    }

    /**
     * Relay input events to relevant UIElements
     */
    public void mouseDown(MouseEvent e) {
        for(int i=0; i<elements.size(); i++) {
            UIElement elem = elements.get(i);
            if(elem instanceof CardGrid) {
                ((CardGrid) elem).click(e.getX(), e.getY());
            }
        }
    }

    public void mouseUp(MouseEvent e) {
        //TODO: Handle Mouse Down
    }

    public void mouseMove(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        for(int i=0; i<elements.size(); i++) {
            UIElement elem = elements.get(i);
            if(elem instanceof CardGrid) {
                ((CardGrid) elem).scroll(e.getWheelRotation(), e.getScrollAmount());
            }
        }
    }

    public void keyDown(KeyEvent e) {
        //TODO: Handle Key Down
    }

    public void keyUp(KeyEvent e) {
        //TODO: Handle Key Up
    }

    /**
     * Getters
     */
    public int getMX() {
        return mX;
    }

    public int getMY() {
        return mY;
    }
}
