package com.intern.model;

import java.util.ArrayList;

public class Recipients {
    private ArrayList<String> normalSenders; //DO
    private ArrayList<String> carbonCopy; //DW
    private ArrayList<String> blindCarbonCopy; //UDW

    public Recipients() {
        this.normalSenders = new ArrayList<>();
        this.carbonCopy = new ArrayList<>();
        this.blindCarbonCopy = new ArrayList<>();
    }

    public void addToNormalSenders(String name) {
        this.normalSenders.add(name);
    }

    public void addToCarbonCoby(String name) {
        this.carbonCopy.add(name);
    }

    public void addToBlindCarbonCoby(String name) {
        this.blindCarbonCopy.add(name);
    }

    public ArrayList<String> getNormalSenders() {
        return normalSenders;
    }

    public ArrayList<String> getCarbonCopy() {
        return carbonCopy;
    }

    public ArrayList<String> getBlindCarbonCopy() {
        return blindCarbonCopy;
    }
}
