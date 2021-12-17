package com.example.liferestart.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.liferestart.entity.AgeEvent;
import com.example.liferestart.entity.Attribute;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;


public class Property implements Serializable {

    private static final String TAG = "Property";
    private Attribute attribute;
    private HashMap<Integer, AgeEvent> ageEventHashMap;

    public Property(HashMap<Integer, AgeEvent> ageEventHashMap) {
        this.ageEventHashMap = ageEventHashMap;
    }

    public void restartProperty(Attribute attribute) {

        this.attribute = new Attribute(attribute);
    }

    public Attribute getAttribute() {
        return attribute;
    }
    public void takeEffect(String effect) {
        if (effect == null) {
            return;
        }
        JSONObject jsonEffect = JSON.parseObject(effect);
        //Log.d(TAG, effect);
        Iterator<String> it = jsonEffect.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = jsonEffect.getString(key);
            attribute.change(key, Integer.parseInt(value));
        }
        //Log.d(TAG, attribute.toString());
    }

    public AgeEvent getAgeData(){
        return ageEventHashMap.get(attribute.getPropInteger("AGE"));
    }

}
