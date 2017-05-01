package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.modules.Game;

/**
 * Interface for when the user selects Cards
 */
public interface SelectCards {
    void selected(CardStack stack, Game g);
}
