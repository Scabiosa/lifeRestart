package com.example.liferestart.entity;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.liferestart.utility.ConditionHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Life implements Serializable {

    private static final String TAG = "Life";
    private List<Integer> triggerTalents;
    private Property property;
    private HashMap<Integer, Event> eventHashMap;
    private Talents talents;


    public Life(HashMap<Integer, Event> eventHashMap, HashMap<Integer, Talent> talentHashMap, HashMap<Integer, AgeEvent> ageEventHashMap) {
        this.property = new Property(ageEventHashMap);
        this.talents = new Talents(talentHashMap);
        this.eventHashMap = eventHashMap;

    }

    public Life(HashMap<Integer, Event> eventHashMap, HashMap<Integer, AgeEvent> ageEventHashMap, Talents talents) {
        this.property = new Property(ageEventHashMap);
        this.eventHashMap = eventHashMap;
        this.talents = talents;
    }

    public List<Event> getEventList() {
        List<Integer> eventList = property.getAttribute().getEvents();
        List<Event> ans = new ArrayList<Event>();
        for(int key: eventList){
            ans.add(eventHashMap.get(key));
        }
        return ans;
    }

    public void restartLife(Attribute attribute) {
        property.restartProperty(attribute);
        triggerTalents = new ArrayList<>();
        doTalent();
    }

    public void doTalent(){
        List<Integer> talentsList = getUnfinishedTalent();
        for(int value: talentsList){
            Talent talent = talents.talentTakeEffect(value, property);
            if(talent!=null){
                triggerTalents.add(value);
                property.takeEffect(talent.getEffect());
            }
        }
        Log.d(TAG, property.getAttribute().toString());
    }

    public List<EventToAge> next(){
        property.getAttribute().change("AGE", 1);
        Log.d(TAG, ""+property.getAttribute().getPropInteger("AGE"));
        doTalent();
        AgeEvent ageEvent = property.getAgeData();
        int eventId = randomEvent(ageEvent);
        return finishEvent(eventId);
    }

    public int getAge(){
        return getProperty().getAttribute().getAge();
    }

    public List<EventToAge> finishEvent(int eventId){
        List<EventToAge> eventList = new ArrayList<EventToAge>();

        property.getAttribute().change("EVT", eventId);
        Event event = eventHashMap.get(eventId);
        eventList.add(new EventToAge(event, getAge()));
        if(event == null)
            Log.d(TAG, "event is null, eventId: "+eventId);
        else
            Log.d(TAG, "happened Event: "+event);
        property.takeEffect(event.getEffect());
        String branch = event.getBranch();
        if(branch!=null){
            JSONArray jsonBranch = JSON.parseArray(branch);
            ConditionHandler conditionHandler = new ConditionHandler();
            for(Object obj: jsonBranch){
                String conditionResult = obj.toString();
                int index = conditionResult.indexOf(":");

                if(index == -1)
                    continue;
                String condition = conditionResult.substring(0, index).trim();

                int nextEventId = Integer.parseInt(conditionResult.substring(index+1, conditionResult.length()).trim());
                //Log.d(TAG, "branch: "+conditionResult+", "+condition+", "+nextEventId);
                if(conditionHandler.checkConditions(condition, property)){
                    List<EventToAge> eventToAges = finishEvent(nextEventId);
                    for(EventToAge eventToAge:eventToAges){
                        eventList.add(eventToAge);
                    }
                }
            }
        }
        return eventList;
    }
    public List<Talent> randomTalent(int listSize){
        return talents.talentRandom(listSize);
    }

    public Talents getTalents() {
        return talents;
    }

    public Property getProperty() {
        return property;
    }

    public int randomEvent(AgeEvent ageEvent){
        int eventId = 0;
        HashMap<Integer, Double> ageEventHashMap = ageEvent.getAgeEventHashMap();
        HashMap<Integer, Double> ageEventCheckedHashMap = new HashMap<Integer, Double>();
        Iterator iterator = ageEventHashMap.keySet().iterator();

        while (iterator.hasNext()){
            int key = (int) iterator.next();
            //Log.d(TAG, "all event: "+key);
            if(checkEvent(key)){
                ageEventCheckedHashMap.put(key, ageEventHashMap.get(key));
                //Log.d(TAG, "probable event: "+key);
            }
        }
        double totalWeight = 0;
        iterator = ageEventCheckedHashMap.keySet().iterator();
        while (iterator.hasNext()){
            int key = (int) iterator.next();
            totalWeight += ageEventHashMap.get(key);
        }
        //Log.d(TAG, "totalWeight: "+totalWeight);
        double randomWeight = totalWeight*Math.random();
        iterator = ageEventCheckedHashMap.keySet().iterator();
        while (iterator.hasNext()){
            int key = (int) iterator.next();
            randomWeight -= ageEventHashMap.get(key);
            eventId = key;
            if(randomWeight<=0){
                break;
            }
        }
        return eventId;
    }

    public boolean checkEvent(int eventId){
        Event event = eventHashMap.get(eventId);
        //Log.d(TAG, event.toString());
        if(event.getNoRandom()==1){
            return false;
        }
        ConditionHandler conditionHandler = new ConditionHandler();
        if(event.getExclude()!=null && conditionHandler.checkConditions(event.getExclude(), property)){
            return false;
        }
        if(event.getInclude() != null){
            return conditionHandler.checkConditions(event.getInclude(), property);
        }
        return true;
    }

    public List<Integer> getUnfinishedTalent(){
        List<Integer> talentsList= property.getAttribute().getTalents();
        List<Integer> ans = new ArrayList<>();
        for(int value: talentsList){
            if(!triggerTalents.contains(value)){
                ans.add(value);
            }
        }
        return ans;
    }
    public boolean isLifeEnd(){
        return getProperty().getAttribute().isEnd();
    }
}
