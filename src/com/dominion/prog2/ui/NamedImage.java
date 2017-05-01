package com.dominion.prog2.ui;

import javafx.scene.image.Image;


public class NamedImage extends Image {
    private final String name;

    /**
     * Constructor for NamedImage
     *      combines a name with the url of an Image
     */
    public NamedImage(String url, String name) {
        super(url);
        this.name = name;
    }

    /**
     * Getter for the Name of the Image
     */
    public String getName() {
        return name;
    }
}
