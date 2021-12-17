package com.example.liferestart.entity;

import java.io.Serializable;
import java.util.HashMap;

public class AgeEvent implements Serializable {
    private int age;
    private HashMap<Integer, Double> ageEventHashMap;

    public AgeEvent(int age, HashMap<Integer, Double> ageEventHashMap) {
        this.age = age;
        this.ageEventHashMap = ageEventHashMap;
    }

    public HashMap<Integer, Double> getAgeEventHashMap() {
        return ageEventHashMap;
    }

    @Override
    public String toString() {
        String ageEventString = "";
        for(int key: ageEventHashMap.keySet()){
            ageEventString = ageEventString+key+ ":"+ageEventHashMap.get(key)+", ";
        }
        return "AgeEvent{" +
                "age=" + age +
                ", ageEventHashMap= [" + ageEventString +
                " ]}";
    }
}
