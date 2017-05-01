package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.modules.Game;

/**
 * Interface for when the cardSelection needs Validated from a PopUp
 */
public interface ValidateCardSelection {
    boolean validate(CardStack stack, Game g);
}
