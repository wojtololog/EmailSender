package com.intern.model;

import java.util.ArrayList;

/**
 * It holds lists with all parsed recipients types (TO, CC, BCC)
 */
public class Recipients {
    /**
     * It holds all "TO" recipients
     */
    private ArrayList<String> normalSenders; //DO
    /**
     * It holds all "CC" recipients
     */
    private ArrayList<String> carbonCopy; //DW
    /**
     * It holds all "BCC" recipients
     */
    private ArrayList<String> blindCarbonCopy; //UDW

    /**
     * Create new instances of all lists
     */
    public Recipients() {
        this.normalSenders = new ArrayList<>();
        this.carbonCopy = new ArrayList<>();
        this.blindCarbonCopy = new ArrayList<>();
    }

    /**
     * Add new element to "TO" list
     * @param name name of adding recipient
     */
    public void addToNormalSenders(String name) {
        this.normalSenders.add(name);
    }

    /**
     * Add new element to "CC" list
     * @param name name of adding recipient
     */
    public void addToCarbonCoby(String name) {
        this.carbonCopy.add(name);
    }

    /**
     * Add new element to "BCC" list
     * @param name name of adding recipient
     */
    public void addToBlindCarbonCoby(String name) {
        this.blindCarbonCopy.add(name);
    }

    /**
     *
     * @return It returns list of "TO" recipients
     */
    public ArrayList<String> getNormalSenders() {
        return normalSenders;
    }

    /**
     *
     * @return It returns list of "CC" recipients
     */
    public ArrayList<String> getCarbonCopy() {
        return carbonCopy;
    }

    /**
     *
     * @return It returns list of "BCC" recipients
     */
    public ArrayList<String> getBlindCarbonCopy() {
        return blindCarbonCopy;
    }
}
