package com.dominion.prog2.ui;

/**
 * Created by CARLINSE1 on 4/24/2017.
 */
public class LobbyData {
    private String name;
    private String host;
    private String size;

    public LobbyData(String data) {
        String[] fields = data.split(" ");

        name = fields[0];
        host = fields[1];
        size = fields[2];
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return name.hashCode()+host.hashCode()+size.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof LobbyData) {
            LobbyData o = (LobbyData)other;

            if(name.equals(o.name) && host.equals(o.host) && size.equals(o.size))
                return true;
        }

        return false;
    }
}
