package com.dominion.prog2.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by cobra on 3/27/2017.
 */
public class UIManager {
    /**
     * This is a singleton that handles rendering and input management
     */
    private static UIManager instance = null;
    protected UIManager() {}

    public static UIManager get(){
        if(instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    private ArrayList<UIElement> elements;
    private UIElement focused;

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
     * Relay input events to relevant UIElements
     */
    public void mouseDown(MouseEvent e) {
        //TODO: Handle Mouse Down
    }

    public void mouseUp(MouseEvent e) {
        //TODO: Handle Mouse Down
    }

    public void mouseMove(MouseEvent e) {
        //TODO: Handle Mouse Move
    }

    public void keyDown(KeyEvent e) {
        //TODO: Handle Key Down
    }

    public void keyUp(KeyEvent e) {
        //TODO: Handle Key Up
    }
}
