package com.dominion.prog2.ui;

import com.dominion.prog2.Driver;
import com.dominion.prog2.game.CardInfo;
import javafx.scene.image.Image;
import java.util.HashMap;

public class ImageCache {
    public static HashMap<String, Image> cardImage;

    /**
     * Puts images into a HashMap String, BufferedImage
     * @param d Driver
     */
    public static void readImages(Driver d) {
        cardImage = new HashMap<>();

        //Puts Images into HashMap
        for(String name : CardInfo.allCardNames) {
            String url = "Cards/"+name+".jpg";
            cardImage.put(name, new NamedImage(url, name));
        }
    }
}
