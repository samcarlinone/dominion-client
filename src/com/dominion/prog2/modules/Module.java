package com.dominion.prog2.modules;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cobra on 4/6/2017.
 */
public interface Module {
    public void render(Graphics g);

    public Module tick(ArrayList<HashMap<String, String>> server_msg);
}
