package com.dominion.prog2.game;

import java.util.Comparator;
import java.util.HashMap;

public class CardInfo {
    /**
     * This is the overall List of card names
     * Includes all CardTypes of the Cards
     */
    public static String[] allCardNames = {
            "Artisan","Bandit","Bureaucrat",
            "Card_back","Cellar","Chapel",
            "Copper","Council Room","Curse",
            "Duchy","Estate","Festival",
            "Gardens","Gold","Harbinger",
            "Laboratory","Library","Market",
            "Merchant","Militia","Mine",
            "Moat","Moneylender","Poacher",
            "Province","Remodel","Sentry",
            "Silver","Smithy","Throne Room",
            "Trash","Vassal","Village",
            "Witch","Workshop"
    };

    /**
     * This is a list of only Treasure Cards
     * Their card type is only Treasure
     */
    public static String[] treasureCardNames = {
            "Copper","Silver","Gold"
    };

    /**
     * This is a list of only Victory Cards
     * Their card type is only Victory
     */
    public static String[] victoryCardNames = {
            "Curse","Estate","Duchy","Province"
    };

    /**
     * This is a list of Kingdom Cards
     * Their card type are one of the following: Action, Reaction, Attack
     * The exception is Garden, since the host has to choose to play with the card
     */
    public static String[] kingdomCardNames = {
            "Artisan","Bandit","Bureaucrat",
            "Cellar","Chapel","Council Room",
            "Festival", "Gardens","Harbinger",
            "Laboratory","Library","Market",
            "Merchant","Militia","Mine",
            "Moat","Moneylender","Poacher",
            "Remodel","Sentry", "Smithy",
            "Throne Room", "Vassal","Village",
            "Witch","Workshop"
    };

    /**
     * This is a list of extra cards
     * These are images within the Res/Cards Folder
     *      Card Back, Trash
     */
    public static String[] extraCardNames = {
            "Card_back", "Trash"
    };

    /**
     * Card Data
     * stored in format
     * [type, price, victoryValue, addCoins, addAction, addBuy, addCard]
     * The card classes will get their information from this variable
     */
    private static HashMap<String, int[]> data;

    /**
     * Sets the data of the card into hashMap
     * Key: Name of the Card
     * Value: List of Integers
     * using the format:
     *      [type, price, victoryValue, addCoins, addAction, addBuy, addCard]
     */
    private static void populate() {
        //[type, price, victoryValue, addCoins, addAction, addBuy, addCard]

        //If data already has been made, early return
        if(data != null)
            return;

        data = new HashMap<>();

        //Base Cards
        data.put("Curse", genData(CardType.VICTORY, 0, -1, 0, 0, 0, 0));
        data.put("Estate", genData(CardType.VICTORY, 2, 1, 0, 0, 0, 0));
        data.put("Duchy", genData(CardType.VICTORY, 5, 3, 0, 0, 0, 0));
        data.put("Province", genData(CardType.VICTORY, 8, 6, 0, 0, 0, 0));
        data.put("Copper", genData(CardType.TREASURE, 0, 0, 1, 0, 0, 0));
        data.put("Silver", genData(CardType.TREASURE, 3, 0, 2, 0, 0, 0));
        data.put("Gold", genData(CardType.TREASURE, 6, 0, 3, 0, 0, 0));

        //Kingdom Cards
        data.put("Cellar", genData(CardType.ACTION, 2, 0, 0, 1, 0, 0));
        data.put("Chapel", genData(CardType.ACTION, 2, 0, 0, 0, 0, 0));
        data.put("Moat", genData(CardType.REACTION, 2, 0, 0, 0, 0, 2));
        data.put("Harbinger", genData(CardType.ACTION, 3, 0, 0, 1, 0, 1));
        data.put("Merchant", genData(CardType.ACTION, 3, 0, 0, 1, 0, 1));
        data.put("Vassal", genData(CardType.ACTION, 3, 0, 2, 0, 0, 0));
        data.put("Village", genData(CardType.ACTION, 3, 0, 0, 2, 0, 1));
        data.put("Workshop", genData(CardType.ACTION, 3, 0, 0, 0, 0,0));
        data.put("Bureaucrat", genData(CardType.ATTACK, 4, 0, 0, 0, 0, 0));
        data.put("Gardens", genData(CardType.VICTORY, 4, 0, 0, 0, 0, 0));
        data.put("Militia", genData(CardType.ATTACK, 4, 0, 2, 0, 0, 0));
        data.put("Moneylender", genData(CardType.ACTION, 4, 0, 0, 0, 0, 0));
        data.put("Poacher", genData(CardType.ACTION, 4, 0, 1, 1, 0, 1));
        data.put("Remodel", genData(CardType.ACTION, 4, 0, 0, 0, 0, 0));
        data.put("Smithy", genData(CardType.ACTION, 4, 0, 0, 0, 0, 3));
        data.put("Throne Room", genData(CardType.ACTION, 4, 0, 0, 0, 0, 0));
        data.put("Bandit", genData(CardType.ATTACK, 5, 0, 0, 0, 0, 0));
        data.put("Council Room", genData(CardType.ACTION, 5, 0, 0, 0, 1, 4));
        data.put("Festival", genData(CardType.ACTION, 5, 0, 2, 2, 1, 0));
        data.put("Laboratory", genData(CardType.ACTION, 5, 0, 0, 1, 0, 2));
        data.put("Library", genData(CardType.ACTION, 5, 0, 0, 0, 0, 0));
        data.put("Market", genData(CardType.ACTION, 5, 0, 1, 1, 1 ,1));
        data.put("Mine", genData(CardType.ACTION, 5, 0, 0, 0, 0, 0));
        data.put("Sentry", genData(CardType.ACTION, 5, 0, 0, 1, 0, 1));
        data.put("Witch", genData(CardType.ATTACK, 5, 0, 0, 0, 0, 2));
        data.put("Artisan", genData(CardType.ACTION, 6, 0, 0, 0, 0, 0));
    }

    /**
     * Generates the List of ints, which will be put into the hashmap
     * This is the value to the key (name)
     */
    private static int[] genData(CardType type, int price, int victoryValue, int addCoins, int addAction, int addBuy, int addCard) {
        return new int[] {type.ordinal(), price, victoryValue, addCoins, addAction, addBuy, addCard};
    }

    /**
     * Getter for the Value in the Hashmap with the Key = name
     */
    public static int[] getVals(String name){
        populate();
        return data.get(name);
    }

    /**
     * This Creates and Returns a card based off the name
     * Using the Value of the card, it determines which Card class to use
     */
    public static Card getCard(String name) {
        int[] vals = CardInfo.getVals(name);

        switch (CardType.values()[vals[0]]) {
            case TREASURE:
                return new TreasureCard(name);

            case VICTORY:
                return new VictoryCard(name);

            case ACTION:
                return new ActionCard(name);

            case ATTACK:
                return new AttackCard(name);

            case REACTION:
                return new ReactionCard(name);
        }

        return null;
    }

    /**
     * Class for comparing the names
     */
    public static class NameComparator implements Comparator<String>
    {
        /**
         * Compares two Strings and tells if they are the same
         */
        public int compare(String name1, String name2)
        {
            int[] vals1 = CardInfo.getVals(name1);
            int[] vals2 = CardInfo.getVals(name2);

            if(vals1[0] != vals2[0]) {
                CardType type1 = CardType.values()[vals1[0]];
                boolean isAction1 = type1 == CardType.ACTION || type1 == CardType.ATTACK || type1 == CardType.REACTION;
                CardType type2 = CardType.values()[vals2[0]];
                boolean isAction2 = type2 == CardType.ACTION || type2 == CardType.ATTACK || type2 == CardType.REACTION;

                if(!isAction1 || !isAction2)
                    return vals1[0] - vals2[0];
            }

            if(vals1[1] != vals2[1]) {
                return vals1[1] - vals2[1];
            }

            return name1.compareTo(name2);
        }
    }
}
