package com.dominion.prog2.game;


import com.dominion.prog2.Driver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Game
{
    public HashMap<String, BufferedImage> cardImage;
    private Driver d;

    public Game(Driver d)
    {
        this.d = d;
        readImages();
    }

    public void readImages()
    {
        cardImage = new HashMap<>();

        int h = (d.getWindow().getHeight()/4);
        int w = ((h*125)/200);

        String[] names =
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
            for(int i = 0; i < names.length; i ++)
            {
                String n = names[i];
                cardImage.put(n, resize(ImageIO.read(new File("res/Cards/"+n+".jpg")), w, h));
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

    public void render(Graphics g)
    {
        g.drawImage(cardImage.get("Copper"), d.getWindow().getWidth()/2 - 164/2 - 164 -10,0, d);

        /*
		getSpecificImage("copper");
		g.drawImage(img, window.getWidth()/2 - 164/2 - 164 -10,0, this);
		getSpecificImage("silver");
		g.drawImage(img, window.getWidth()/2 - 164/2,0, this);
		getSpecificImage("gold");
		g.drawImage(img, window.getWidth()/2 - 164/2 + 164 + 10,0, this);
		getSpecificImage("estate");
		g.drawImage(img, window.getWidth()/2 - 164/2 - 164 - 10,243 +10, this);
		getSpecificImage("duchy");
		g.drawImage(img, window.getWidth()/2 - 164/2,243 + 10, this);
		getSpecificImage("province");
		g.drawImage(img, window.getWidth()/2 - 164/2 + 164 + 10,243 +10, this);

		getSpecificImage("trash");
		g.drawImage(img, window.getWidth()/2 - 164/2 + 164*5 + 10*5,243/2 +10, this);
		getSpecificImage("back");
		g.drawImage(img, window.getWidth()/2 - 164/2 - 164*5 - 10*5,243/2 +10, this);
		*/
    }
}
