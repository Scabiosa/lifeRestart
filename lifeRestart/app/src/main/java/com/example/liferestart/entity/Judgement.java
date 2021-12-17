package com.example.liferestart.entity;

public class Judgement {
    private int grade, value;
    private String judge;

    public Judgement(int grade, int value, String judge) {
        this.grade = grade;
        this.value = value;
        this.judge = judge;
    }

    @Override
    public String toString() {
        return "Judgement{" +
                "grade=" + grade +
                ", value=" + value +
                ", judge='" + judge + '\'' +
                '}';
    }
    public String getEvaluate(){
        return ""+value+" "+judge;
    }

    public int getGrade() {
        return grade;
    }

    public int getValue() {
        return value;
    }

    public String getJudge() {
        return judge;
    }
}
