package com.dominion.prog2.game;

public class Player
{
    public CardStack deck;
    public CardStack hand;
    public CardStack discard;
    public String name;

    //Todo: figure out what methods we want to include

    /**
     * Creates a new Player
     * @param name
     */
    public Player(String name)
    {
        this.name = name;
    }
}
