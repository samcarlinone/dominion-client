package com.dominion.prog2.ui;

import javafx.scene.image.Image;


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
