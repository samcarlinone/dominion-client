package com.dominion.prog2.ui;


public class LobbyData {
    private String name;
    private String host;
    private String size;

    /**
     * Constructor for LobbyData
     * String data is passed in
     *      contains name of Lobby, host of Lobby, size of Lobby
     */
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

    /**
     * Generates a hashcode with name, host, size for LobbyData
     */
    @Override
    public int hashCode() {
        return name.hashCode()+host.hashCode()+size.hashCode();
    }

    /**
     * Checks if this is the same as the other LobbyData
     */
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
