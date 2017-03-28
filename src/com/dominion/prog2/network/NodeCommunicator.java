package com.dominion.prog2.network;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CARLINSE1 on 3/27/2017.
 */
public class NodeCommunicator {
    private String url;

    /**
     * Communicates through a node server
     * @param url The url of the node server
     */
    public NodeCommunicator(String url) {
    }

    /**
     * Sends a get request with given data, returns result or "Error"
     * @param data JSON encoded data
     * @return JSON response or "Error"
     */
    public String getMessage(String data) {
        return "";
    }

    /**
     * Converts a simple Map to JSON
     * @param map <String, String> map
     * @return Encoded JSON
     */
    public String mapToJSON(HashMap<String, String> map) {
        return "";
    }

    /**
     * Converts JSON to an array of objects !!!Only supports objects with string values, invalid JSON will cause unexpected results!!!
     * @param data JSON formatted object or array of objects
     * @return ArrayList of HashMaps of String key and Object value
     */
    public ArrayList<HashMap<String, String>> JSONToMap(String data) {
        return new ArrayList<>();
    }

    /**
     * Parses an object into a HashMap
     * @param data
     * @param index
     * @param out
     * @return
     */
    private int parseObject(String data, int index, HashMap<String, String> out) {
        return 0;
    }

    /**
     * Parse a string out of a given string
     * @param data the overall data string
     * @param index where to begin parsing the string
     * @param out a StringBuilder that can receive the resulting string
     * @return the ending index after closing double quote
     */
    private int parseString(String data, int index, StringBuilder out) {
        return 0;
    }
}