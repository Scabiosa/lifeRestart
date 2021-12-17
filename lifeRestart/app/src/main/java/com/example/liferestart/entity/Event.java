package com.example.liferestart.entity;


import java.io.Serializable;

public class Event implements Serializable {
    public Event() {
    }

    private int id, noRandom;

    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", noRandom=" + noRandom +
                ", eventName='" + eventName + '\'' +
                ", effect='" + effect + '\'' +
                ", exclude='" + exclude + '\'' +
                ", postEvent='" + postEvent + '\'' +
                ", branch='" + branch + '\'' +
                ", include='" + include + '\'' +
                '}';
    }

    private String eventName, effect, exclude, postEvent, branch, include;

    public String getEventDescription(){
        if(postEvent!=null)
            return eventName+"\n"+postEvent;
        return eventName;
    }


    public Event(int id, String eventName, String effect, String exclude, String postEvent, String branch, String include, int noRandom) {
        this.id = id;
        this.eventName = eventName;
        this.effect = effect;
        this.exclude = exclude;
        this.postEvent = postEvent;
        this.branch = branch;
        this.include = include;
        this.noRandom = noRandom;
    }

    public int getNoRandom() {
        return noRandom;
    }

    public String getExclude() {
        return exclude;
    }

    public String getBranch() {
        return branch;
    }

    public String getEffect() {
        return effect;
    }

    public String getInclude() {
        return include;
    }
}
