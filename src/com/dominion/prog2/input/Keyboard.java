package com.dominion.prog2.input;

import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.UIManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener {
    private Driver d;

    public Keyboard(Driver driver) {
        this.d = driver;
        this.d.addKeyListener(this);
    }


    /**
     * Invoked when a key has been typed.
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        UIManager.get().keyPressed(e);
    }

    /**
     * Invoked when a key has been released.
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
