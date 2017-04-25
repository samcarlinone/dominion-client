package com.dominion.prog2.ui;

import javafx.scene.image.Image;

/**
 * Created by CARLINSE1 on 4/25/2017.
 */
public class NamedImage extends Image {
    private final String name;

    public NamedImage(String url, String name) {
        super(url);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
