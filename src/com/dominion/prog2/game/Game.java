package com.dominion.prog2.game;


import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.Button;
import com.dominion.prog2.ui.ImageCache;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game
{
    private ArrayList<Button> buttonList;
    private Driver d;
    private Window window;


    public Game(Driver d)
    {
        this.d = d;
        this.window = d.getWindow();
        buttonList = new ArrayList<Button>();
    }

    public void render(Graphics g)
    {
        //g.drawImage(cardImage.get("ImageName"), x, y, d);
        //ROW 1
        g.drawImage(ImageCache.cardImage.get("Copper"), 5,5, d);
        g.drawImage(ImageCache.cardImage.get("Silver"), ImageCache.CardWidth + 5*2,5, d);
        g.drawImage(ImageCache.cardImage.get("Gold"), ImageCache.CardWidth*2 +5*3,5, d);
        g.drawImage(ImageCache.cardImage.get("Estate"), ImageCache.CardWidth*3 +5*4,5, d);
        g.drawImage(ImageCache.cardImage.get("Duchy"), ImageCache.CardWidth*4 +5*5,5, d);
        g.drawImage(ImageCache.cardImage.get("Province"), ImageCache.CardWidth*5 +5*6,5, d);

        //ROW 2
        //g.drawImage(cardImage.get("Card_back"), 5,5*2 + CardHeight, d);
        g.drawImage(ImageCache.cardImage.get("Militia"),ImageCache.CardWidth + 5*2,5*2 +ImageCache.CardHeight, d);
        g.drawImage(ImageCache.cardImage.get("Workshop"), ImageCache.CardWidth*2 +5*3,5*2 +ImageCache.CardHeight, d);
        g.drawImage(ImageCache.cardImage.get("Curse"), ImageCache.CardWidth*3 +5*4,5*2 +ImageCache.CardHeight, d);
        g.drawImage(ImageCache.cardImage.get("Harbringer"),ImageCache.CardWidth*4 +5*5,5*2 +ImageCache.CardHeight, d);
        g.drawImage(ImageCache.cardImage.get("Trash"), ImageCache.CardWidth*5 +5*6,5*2 +ImageCache.CardHeight, d);

        //ROW 3
        g.drawImage(ImageCache.cardImage.get("Bandit"), 5,5*3 + ImageCache.CardHeight*2, d);
        g.drawImage(ImageCache.cardImage.get("Chapel"), ImageCache.CardWidth + 5*2,5*3 +ImageCache.CardHeight*2, d);
        g.drawImage(ImageCache.cardImage.get("Moat"), ImageCache.CardWidth*2 +5*3,5*3 +ImageCache.CardHeight*2, d);
        g.drawImage(ImageCache.cardImage.get("Vassal"), ImageCache.CardWidth*3 +5*4,5*3 +ImageCache.CardHeight*2, d);
        g.drawImage(ImageCache.cardImage.get("Sentry"), ImageCache.CardWidth*4 +5*5,5*3 +ImageCache.CardHeight*2, d);
        g.drawImage(ImageCache.cardImage.get("Village"), ImageCache.CardWidth*5 +5*6,5*3 +ImageCache.CardHeight*2, d);
    }

    /**
     * Custom Card Functions
     */

    public void discard(CardStack s) {
        //TODO: Implement
    }

    public void trash(CardStack s) {
        //TODO: Implement
    }


}
