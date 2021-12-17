package com.example.liferestart.entity;

import java.io.Serializable;

public class Talent implements Serializable {
    private int id, grade;
    private String name, description, exclusive, effect, status, condition, replacement;

    public int getId() {
        return id;
    }

    public Talent(int id, int grade, String name, String description, String exclusive, String effect, String status, String condition, String replacement) {
        this.id = id;
        this.grade = grade;
        this.name = name;
        this.description = description;
        this.exclusive = exclusive;
        this.effect = effect;
        this.status = status;
        this.condition = condition;
        this.replacement = replacement;
    }

    @Override
    public String toString() {
        return "Talent{" +
                "id=" + id +
                ", grade=" + grade +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", exclusive='" + exclusive + '\'' +
                ", effect='" + effect + '\'' +
                ", status='" + status + '\'' +
                ", condition='" + condition + '\'' +
                ", replacement='" + replacement + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

    public int getGrade() {
        return grade;
    }


    public String getCondition() {
        return condition;
    }
    public String getTalentDescription(){
        return name+"("+description+")";
    }
}
