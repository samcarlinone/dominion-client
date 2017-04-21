package com.dominion.prog2.modules;

import javafx.scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cobra on 4/6/2017.
 */
public abstract class Module {
    private Scene main;

    /**
     * Feeds server messages to current module
     * @param server_msg the array list of hashmaps representing new messages from the server
     */
    public abstract void serverMsg(ArrayList<HashMap<String, String>> server_msg);

    /**
     * Returns a module's scene for driver to display
     * @return JavaFX Scene object
     */
    public Scene getScene() {
        return main;
    }

    /**
     * Only this class should be modifying it's scene
     */
    void setScene(Scene scene) {
        main = scene;
    }
}
