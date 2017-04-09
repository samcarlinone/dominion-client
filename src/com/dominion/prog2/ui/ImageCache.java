package com.dominion.prog2.ui;

import com.dominion.prog2.Driver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageCache {
    public static HashMap<String, BufferedImage> cardImage;
    public static int cardHeight, cardWidth;

    /**
     * Puts images into a HashMap String, BufferedImage
     * @param d Driver
     */
    public static void readImages(Driver d) {
        cardImage = new HashMap<>();

        cardHeight = (d.getWindow().getHeight()/4);
        cardWidth = ((cardHeight *125)/200);
        System.out.println("w: "+ cardWidth +"||h: " + cardHeight);

        String[] CardNames =
                {
                        "Artisan","Bandit","Bureaucrat",
                        "Card_back","Cellar","Chapel",
                        "Copper","Council room","Curse",
                        "Duchy","Estate","Festival",
                        "Gardens","Gold","Harbringer",
                        "Laboratory","Library","Market",
                        "Merchant","Militia","Mine",
                        "Moat","Moneylender","Poacher",
                        "Province","Remodel","Sentry",
                        "Silver","Smithy","Throne room",
                        "Trash","Vassal","Village",
                        "Witch","Workshop"
                };


        try {
            //Puts bufferedImages into HashMap
            for(int i = 0; i < CardNames.length; i ++)
            {
                String n = CardNames[i];
                cardImage.put(n, resize(ImageIO.read(new File("res/Cards/"+n+".jpg")), cardWidth, cardHeight));
            }
        }
        catch(IOException e)
        {
            System.out.println("Image fail!");
            System.exit(1);
        }
    }

    /**
     * Resizes the image
     * @param img
     * @param newW
     * @param newH
     * @return the resized image
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
}
