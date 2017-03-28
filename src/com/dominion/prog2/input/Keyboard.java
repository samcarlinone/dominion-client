package com.dominion.prog2.input;

import com.dominion.prog2.Driver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by cobra on 3/27/2017.
 */
public class Keyboard implements KeyListener {
    private Driver d;

    public Keyboard(Driver driver)
    {
        this.d = driver;
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

    }

    /**
     * Invoked when a key has been released.
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
