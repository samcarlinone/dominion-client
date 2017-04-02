package com.dominion.prog2.game;


import com.dominion.prog2.Driver;
import com.dominion.prog2.ui.Button;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game
{
    public HashMap<String, BufferedImage> cardImage;
    private ArrayList<Button> buttonList;
    private Driver d;
    private Window window;
    private int CardHeight,CardWidth;

    public Game(Driver d)
    {
        this.d = d;
        this.window = d.getWindow();
        readImages();
        buttonList = new ArrayList<Button>();
    }

    public void readImages()
    {
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

    public void render(Graphics g)
    {
        //g.drawImage(cardImage.get("ImageName"), x, y, d);
        //ROW 1
        g.drawImage(cardImage.get("Copper"), 5,5, d);
        g.drawImage(cardImage.get("Silver"), CardWidth + 5*2,5, d);
        g.drawImage(cardImage.get("Gold"), CardWidth*2 +5*3,5, d);
        g.drawImage(cardImage.get("Estate"), CardWidth*3 +5*4,5, d);
        g.drawImage(cardImage.get("Duchy"), CardWidth*4 +5*5,5, d);
        g.drawImage(cardImage.get("Province"), CardWidth*5 +5*6,5, d);

        //ROW 2
        //g.drawImage(cardImage.get("Card_back"), 5,5*2 + CardHeight, d);
        g.drawImage(cardImage.get("Militia"), CardWidth + 5*2,5*2 +CardHeight, d);
        g.drawImage(cardImage.get("Workshop"), CardWidth*2 +5*3,5*2 +CardHeight, d);
        g.drawImage(cardImage.get("Curse"), CardWidth*3 +5*4,5*2 +CardHeight, d);
        g.drawImage(cardImage.get("Harbringer"), CardWidth*4 +5*5,5*2 +CardHeight, d);
        g.drawImage(cardImage.get("Trash"), CardWidth*5 +5*6,5*2 +CardHeight, d);

        //ROW 3
        g.drawImage(cardImage.get("Bandit"), 5,5*3 + CardHeight*2, d);
        g.drawImage(cardImage.get("Chapel"), CardWidth + 5*2,5*3 +CardHeight*2, d);
        g.drawImage(cardImage.get("Moat"), CardWidth*2 +5*3,5*3 +CardHeight*2, d);
        g.drawImage(cardImage.get("Vassal"), CardWidth*3 +5*4,5*3 +CardHeight*2, d);
        g.drawImage(cardImage.get("Sentry"), CardWidth*4 +5*5,5*3 +CardHeight*2, d);
        g.drawImage(cardImage.get("Village"), CardWidth*5 +5*6,5*3 +CardHeight*2, d);
    }
}
