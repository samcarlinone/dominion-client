package com.dominion.prog2.ui;

import com.dominion.prog2.Driver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by cobra on 4/2/2017.
 */
public class ImageCache {
    public static HashMap<String, BufferedImage> cardImage;
    public static int CardHeight,CardWidth;

    public static void readImages(Driver d) {
        cardImage = new HashMap<>();

        CardHeight = (d.getWindow().getHeight()/4);
        CardWidth = ((CardHeight*125)/200);
        System.out.println("w: "+ CardWidth +"||h: " + CardHeight);

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
                cardImage.put(n, resize(ImageIO.read(new File("res/Cards/"+n+".jpg")), CardWidth, CardHeight));
            }
        }
        catch(IOException e)
        {
            System.out.println("Image fail!");
            System.exit(1);
        }
    }

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
