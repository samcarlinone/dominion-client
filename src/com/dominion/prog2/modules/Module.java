package com.dominion.prog2.modules;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cobra on 4/6/2017.
 */
public interface Module {
    /**
     * Renders everything
     * @param g
     */
    public void render(Graphics g);

    /**
     * Updates the module
     * @param server_msg
     * @return Module
     */
    public Module tick(ArrayList<HashMap<String, String>> server_msg);
}
