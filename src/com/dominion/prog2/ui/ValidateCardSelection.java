package com.dominion.prog2.ui;

import com.dominion.prog2.game.CardStack;
import com.dominion.prog2.modules.Game;

public interface ValidateCardSelection {
    boolean validate(CardStack stack, Game g);
}
