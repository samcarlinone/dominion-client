package com.dominion.prog2.modules;


import com.dominion.prog2.Driver;
import com.dominion.prog2.game.*;
import com.dominion.prog2.ui.CardGrid;
import com.dominion.prog2.ui.UIManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Module {

    private Driver d;

    private CardGrid shop;
    private CardGrid hand;

    private BufferedImage img;

    //Window Dimensions: w=1800,h=1100

    /**
     * Initiates all the different card stacks and
     * @param d Driver
    */
    public Game(Driver d) {
        this.d = d;

        try {
            img = ImageIO.read(new File("res/BackgroundTile/RicePaper.jpg"));
        }
        catch(IOException e)
        {
            System.out.println("Can not read in a Background Image");
            System.exit(1);
        }
        CardStack s = new CardStack();
        s.add(new Card("Gold"));
        s.add(new Card("Gold"));
        s.add(new Card("Copper"));
        s.add(new Card("Silver"));

        shop = new CardGrid(s,20,0,400,300);
        shop.border = true;
        shop.backgroundColor = new Color(40,84,168);
        UIManager.get().addElement(shop);


        hand = new CardGrid(d.player.hand,600,909,600,186);
        hand.border = true;
        hand.condense = false;
        hand.scrollable = false;
        hand.backgroundColor = new Color(40,84,168);
        UIManager.get().addElement(hand);
    }

    /**
     * Renders everything
     * @param g Graphics
     */
    @Override
    public void render(Graphics g){
        //Draws tiled background
        for(int x = 0; x <= 1800; x += 449)
        {
            for(int y = 0; y <= 1100; y += 394)
            {
                g.drawImage(img,x,y,d);
            }
        }

        //Nothing for now
    }

    /**
     * updates the module
     * @param server_msg
     * @return Module
     */
    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
        if(hand.getWidth() != 120*hand.stack.size())
        {
            hand.setSize(120*hand.stack.size(),168);

        }

        /*
        if(shop.lastClicked != null) {
            hand.stack.add(shop.stack.remove(shop.lastClicked));
            shop.lastClicked = null;
        }

        if(hand.lastClicked != null) {
            shop.stack.add(hand.stack.remove(hand.lastClicked));
            hand.lastClicked = null;
        }
        */
        return this;
    }

    /**
     * Custom Card Functions
     */



    /**
     * discards a CardStack
     * @param s CardStack
     */
    public void discard(CardStack s) {
        //TODO: Implement
    }

    /**
     * Trashes a CardStack
     * @param s CardStack
     */
    public void trash(CardStack s) {
        //TODO: Implement
    }

    //TODO: decide how we want to do the game logic

}
