package com.dominion.prog2.modules;

import com.dominion.prog2.Driver;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cobra on 4/8/2017.
 */
public class LobbyList implements Module{
    private Driver d;

    public LobbyList(Driver d) {
        this.d = d;
    }

    @Override
    public Module tick(ArrayList<HashMap<String, String>> server_msg) {
        return this;
    }

    @Override
    public void render(Graphics g) {
        //TODO: Implement
    }
}
