package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public UIElement focusedElement;
    public UIElement hoveredElement;

    private int mX;
    private int mY;
    private boolean mDown;

    private int resort=20;

    /**
     * Add a new UIElement
     */
    public void addElement(UIElement element) {
        elements.add(element);

        sortElements();
    }

    /**
     * Remove an UIElement, returns the element
     */
    public UIElement removeElement(UIElement element) {
        elements.remove(element);

        if(focusedElement == element)
            focusedElement = null;

        return element;
    }

    /**
     * Removes all UIElements
     */
    public void removeAll() {
        elements.clear();
        focusedElement = null;
    }

    private void sortElements() {
        Collections.sort(elements, new Comparator<UIElement>() {
            @Override
            public int compare(UIElement o1, UIElement o2) {
                return o1.depth-o2.depth;
            }
        });
    }

    /**
     * Render all elements
     */
    public void render(Graphics g) {
        Shape oldRect = g.getClip();

        for(UIElement e : elements) {
            g.setClip(e);
            e.render(g);
        }

        g.setClip(oldRect);
    }

    /**
     * Tick all elements
     */
    public void tick() {
        for(UIElement e : elements) {
            e.tick();
        }

        if(--resort == 0) {
            resort = 20;
            sortElements();
        }
    }

    /**
     * Relay input events to relevant UIElements
     */
    public void mouseDown(MouseEvent e) {
        focusedElement = null;
        mDown = true;

        for(int i=elements.size()-1; i>=0; i--) {
            UIElement elem = elements.get(i);

            if(elem.contains(e.getPoint())) {
                if (elem instanceof CardGrid) {
                    ((CardGrid) elem).click(e.getX(), e.getY());
                }

                focusedElement = elem;
                break;
            }
        }
    }

    public void mouseUp(MouseEvent e) {
        mDown = false;

        if(focusedElement instanceof Button) {
            if(focusedElement.contains(e.getPoint()))
                ((Button) focusedElement).setClicked(true);

            focusedElement = null;
        }
    }

    public void mouseMove(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        hoveredElement = null;

        for(int i=elements.size()-1; i>=0; i--) {
            UIElement elem = elements.get(i);

            if(elem.contains(e.getPoint())) {
                if(focusedElement instanceof Button && elem != focusedElement)
                    break;

                hoveredElement = elem;
                break;
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        for(int i=elements.size()-1; i>=0; i--) {
            UIElement elem = elements.get(i);

            if(elem.contains(e.getPoint())) {
                if (elem instanceof CardGrid) {
                    ((CardGrid) elem).scroll(e.getWheelRotation(), e.getScrollAmount());
                }

                break;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if(focusedElement != null && focusedElement instanceof Textbox) {
            ((Textbox) focusedElement).keyTyped(e);
        }
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

    public boolean getMDown() { return mDown; }

    /**
     * Static Helper Functions
     */
    public static float lerp(float x1, float x2, float percent) {
        return x1 + percent * (x2 - x1);
    }
}
