package com.dominion.prog2.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UIManager {
    /**
     * This is a singleton that handles rendering and input management
     */
    private static UIManager instance = null;

    /**
     * creates a new arrayList for the elements
     */
    protected UIManager() {
        elements = new ArrayList<>();
    }

    /**
     * get the instance of UI Manager
     */
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
     * @param element
     */
    public void addElement(UIElement element) {
        elements.add(element);

        sortElements();
    }

    /**
     * Remove an UIElement, returns the element
     * @param element
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

    /**
     * Sorts the elements based off their depth
     */
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
     * @param g Graphics
     */
    public synchronized void render(Graphics g) {
        Shape oldRect = g.getClip();

        for(UIElement e : elements) {
            g.setClip(e);
            e.render(g);
        }

        g.setClip(oldRect);
    }

    /**
     * Updates all the elements
     */
    public synchronized void tick() {
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
     * @param e
     */
    public synchronized void mouseDown(MouseEvent e) {
        focusedElement = null;
        mDown = true;

        for(int i=elements.size()-1; i>=0; i--) {
            UIElement elem = elements.get(i);

            if(elem.contains(e.getPoint())) {
                if(elem instanceof Label)
                    continue;

                if (elem instanceof CardGrid) {
                    ((CardGrid) elem).click(e.getX(), e.getY());
                }

                focusedElement = elem;
                break;
            }
        }
    }

    /**
     * Mouse is unclicked
     * @param e
     */
    public synchronized void mouseUp(MouseEvent e) {
        mDown = false;

        if(focusedElement instanceof Button) {
            if(focusedElement.contains(e.getPoint()))
                ((Button) focusedElement).setClicked(true);

            focusedElement = null;
        }
    }

    /**
     * Mouse is moved
     * @param e
     */
    public synchronized void mouseMove(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        hoveredElement = null;

        for(int i=elements.size()-1; i>=0; i--) {
            UIElement elem = elements.get(i);

            if(elem.contains(e.getPoint())) {
                if(elem instanceof Label)
                    continue;

                if(focusedElement instanceof Button && elem != focusedElement)
                    break;

                hoveredElement = elem;
                break;
            }
        }
    }

    /**
     * Mouse wheel is moved
     * @param e
     */
    public synchronized void mouseWheelMoved(MouseWheelEvent e) {
        for(int i=elements.size()-1; i>=0; i--) {
            UIElement elem = elements.get(i);

            if(elem.contains(e.getPoint())) {
                if(elem instanceof Label)
                    continue;

                if (elem instanceof CardGrid) {
                    ((CardGrid) elem).scroll(e.getWheelRotation(), e.getScrollAmount());
                }

                if (elem instanceof TextList) {
                    ((TextList) elem).scroll(e.getWheelRotation(), e.getScrollAmount());
                }

                break;
            }
        }
    }

    /**
     * A key is pressed
     * @param e
     */
    public synchronized void keyPressed(KeyEvent e) {
        if(focusedElement != null && focusedElement instanceof Textbox) {
            ((Textbox) focusedElement).keyTyped(e);
        }
    }

    /**
     * Get mouse Xpos
     * @return
     */
    public int getMX() {
        return mX;
    }

    /**
     * Get mouse Ypos
     * @return
     */
    public int getMY() {
        return mY;
    }

    /**
     * get Mouse Down
     * @return
     */
    public boolean getMDown() { return mDown; }

    /**
     * Static Helper Functions
     * @param x1
     * @param x2
     * @param percent
     * @return float variable
     */
    public static float lerp(float x1, float x2, float percent) {
        return x1 + percent * (x2 - x1);
    }
}
