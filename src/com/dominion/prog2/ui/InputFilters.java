package com.dominion.prog2.ui;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Created by cobra on 4/20/2017.
 */
public class InputFilters {
    /**
     * Returns an appropriate name filter for input fields
     */
    public static EventHandler<KeyEvent> nameFilter() {
        EventHandler<KeyEvent> aux = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (!e.getCharacter().matches("[a-zA-Z0-9]")) {
                    e.consume();
                }
            }
        };
        return aux;
    }

    /**
     * Returns a new length filter for input fields
     * @param maxLength the maximum length (inclusive)
     */
    public static EventHandler<KeyEvent> lengthFilter(int maxLength) {
        return new FilterLength(maxLength);
    }

    public static class FilterLength implements EventHandler<KeyEvent> {
        private int maxLen;

        public FilterLength (int maxLen) {
            this.maxLen = maxLen;
        }

        public void handle(KeyEvent e) {
            TextField tx = (TextField) e.getSource();

            if(tx.getCharacters().length() == maxLen) {
                e.consume();
            }
        }
    }
}
