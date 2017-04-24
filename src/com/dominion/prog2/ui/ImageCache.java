package com.dominion.prog2.ui;

import com.dominion.prog2.Driver;
import javafx.scene.image.Image;
import java.util.HashMap;

public class ImageCache {
    public static HashMap<String, Image> cardImage;
    public static int cardHeight, cardWidth;

    /**
     * Puts images into a HashMap String, BufferedImage
     * @param d Driver
     */
    public static void readImages(Driver d) {
        cardImage = new HashMap<>();

        cardHeight = 100;
        cardWidth = ((cardHeight *125)/200);
        System.out.println("w: "+ cardWidth +"||h: " + cardHeight);

        String[] CardNames =
                {
                        "Artisan","Bandit","Bureaucrat",
                        "Card_back","Cellar","Chapel",
                        "Copper","Council Room","Curse",
                        "Duchy","Estate","Festival",
                        "Gardens","Gold","Harbinger",
                        "Laboratory","Library","Market",
                        "Merchant","Militia","Mine",
                        "Moat","Moneylender","Poacher",
                        "Province","Remodel","Sentry",
                        "Silver","Smithy","Throne Room",
                        "Trash","Vassal","Village",
                        "Witch","Workshop"
                };


            //Puts bufferedImages into HashMap
            for(int i = 0; i < CardNames.length; i ++)
            {
                String n = CardNames[i];
                String url = "Cards/"+n+".jpg";
                cardImage.put(n, new Image(url));
            }

    }
}
