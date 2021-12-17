package com.example.liferestart.entity;

import com.example.liferestart.utility.ConditionHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Talents implements Serializable {
    private HashMap<Integer, Talent> talentHashMap;
    private final double percentageGrade3 = 0.1, percentageGrade2 = 0.2, percentageGrade1 = 0.333;

    public Talents(HashMap<Integer, Talent> talentHashMap) {
        this.talentHashMap = talentHashMap;
    }

    public Talent talentTakeEffect(int talentId, Property property){
        Talent talent = talentHashMap.get(talentId);
        ConditionHandler conditionHandler = new ConditionHandler();
        if(talent.getCondition()!=null && !conditionHandler.checkConditions(talent.getCondition(), property)){
            return null;
        }
        return talent;
    }

    public HashMap<Integer, Talent> getTalentHashMap() {
        return talentHashMap;
    }

    @Override
    public String toString() {
        return "Talents{" +
                "percentageGrade3=" + percentageGrade3 +
                ", percentageGrade2=" + percentageGrade2 +
                ", percentageGrade1=" + percentageGrade1 +
                '}';
    }

    public List<Talent> talentRandom(int listSize){
        List<Talent> talentList = new ArrayList<Talent>();
        HashMap<Integer, List<Talent>> talentClassedByGrade = new HashMap<Integer, List<Talent>>();
        for(int i=0;i<4;i++)
            talentClassedByGrade.put(i, new ArrayList<Talent>());
        Iterator iterator = talentHashMap.keySet().iterator();
        while (iterator.hasNext()){
            int key = (int) iterator.next();
            Talent value = talentHashMap.get(key);
            talentClassedByGrade.get(value.getGrade()).add(value);
        }
        for(int i=0;i<listSize;i++){
            int grade;
            double gradeRandom = Math.random();
            if(gradeRandom<=percentageGrade3)
                grade = 3;
            else if(gradeRandom<=percentageGrade2)
                grade = 2;
            else if(gradeRandom<=percentageGrade1)
                grade = 1;
            else
                grade = 0;
            int len = talentClassedByGrade.get(grade).size();
            int talentRandom = (int) Math.floor(len*Math.random())%len;
            talentList.add(talentClassedByGrade.get(grade).get(talentRandom));
            talentClassedByGrade.get(grade).remove(talentRandom);
        }
        return talentList;
    }
}
