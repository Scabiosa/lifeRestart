package com.example.liferestart.entity;

import com.example.liferestart.entity.Event;

public class EventToAge {
    private Event event;
    private int age;

    public Event getEvent() {
        return event;
    }

    public int getAge() {
        return age;
    }

    public EventToAge(Event event, int age) {
        this.event = event;
        this.age = age;
    }
}
