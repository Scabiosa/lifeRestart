package com.example.liferestart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

enum attr{
    AGE,
    CHR, //颜值
    INT, //智力
    STR, //体质
    MNY, //家境
    SPR, //快乐
    TLT, //天赋
    LIF, //寿命
    EVT, //事件

}

public class Attribute implements Serializable {
    private static int attributeTotal = 20;
    private static int maxAttribute = 10;
    private int age, appearance, intelligent, strength, spirit, lifeAge, money;
    private List<Integer> talents;
    private List<Integer> events;
    private int attributeDisplayTotal = 4;
    private int additionAttr;

    public void setAdditionAttr(int additionAttr) {
        this.additionAttr = additionAttr;
    }

    public Attribute(int age, int appearance, int intelligent, int strength, int money, int spirit, int lifeAge, List<Integer> talents) {
        this.age = age;
        this.appearance = appearance;
        this.intelligent = intelligent;
        this.strength = strength;
        this.money = money;
        this.spirit = spirit;
        this.lifeAge = lifeAge;
        this.talents = talents;
        this.events = new ArrayList<Integer>();
        additionAttr = 0;
    }

    public Attribute(List<Integer> talents) {
        this.age = -1;
        this.appearance = 5;
        this.intelligent = 5;
        this.strength = 5;
        this.money = 5;
        this.spirit = 5;
        this.lifeAge = 1;
        this.talents = talents;
        this.events = new ArrayList<Integer>();
        additionAttr = 0;
    }

    public Attribute(Attribute attribute1) {
        this.age = attribute1.age;
        this.appearance = attribute1.appearance;
        this.intelligent = attribute1.intelligent;
        this.strength = attribute1.strength;
        this.money = attribute1.money;
        this.spirit = attribute1.spirit;
        this.lifeAge = attribute1.lifeAge;
        this.talents = attribute1.talents;
        this.events = new ArrayList<Integer>();
        additionAttr = 0;
    }

    public Attribute(int age, int appearance, int intelligent, int strength, int spirit, int lifeAge, int money, List<Integer> talents, List<Integer> events) {
        this.age = age;
        this.appearance = appearance;
        this.intelligent = intelligent;
        this.strength = strength;
        this.spirit = spirit;
        this.lifeAge = lifeAge;
        this.money = money;
        this.talents = talents;
        this.events = events;
        additionAttr = 0;
    }

    public List<Integer> getTalents() {
        return talents;
    }

    public int getAttributeAll() {
        return attributeTotal+additionAttr;
    }

    public int getPropInteger(String prop){
        switch (prop){
            case "AGE":
                return age;
            case "CHR":
                return appearance;
            case "INT":
                return intelligent;
            case "STR":
                return strength;
            case "MNY":
                return money;
            case "SPR":
                return spirit;
            case "LIF":
                return lifeAge;
            default:
                return 0;
        }
    }
    public List<Integer> getPropArray(String prop){
        switch (prop){
            case "EVT":
                return events;
            case "TLT":
                return talents;
            default:
                return new ArrayList<Integer>();
        }
    }

    public int getAppearance() {
        return appearance;
    }

    public int getIntelligent() {
        return intelligent;
    }

    public int getStrength() {
        return strength;
    }

    public int getSpirit() {
        return spirit;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "age=" + age +
                ", appearance=" + appearance +
                ", intelligent=" + intelligent +
                ", strength=" + strength +
                ", spirit=" + spirit +
                ", lifeAge=" + lifeAge +
                ", money=" + money +
                ", talents=" + talents +
                ", events=" + events +
                '}';
    }

    public void change(String key, int value){
        switch (key){
            case "AGE":
                age = age+value;
                break;
            case "CHR":
                appearance = appearance+value;
                break;
            case "INT":
                intelligent = intelligent+value;
                break;
            case "STR":
                strength = strength+value;
                break;
            case "MNY":
                money = money+value;
                break;
            case "SPR":
                spirit = spirit+value;
                break;
            case "LIF":
                lifeAge = lifeAge+value;
                break;
            case "RDM":
                for(int i=0;i<value;i++){
                    int randomNumber = (int) (Math.random()*5);
                    switch (randomNumber){
                        case 0:
                            appearance = appearance+1;
                            break;
                        case 1:
                            intelligent = intelligent+1;
                            break;
                        case 2:
                            strength = strength+1;
                            break;
                        case 3:
                            money = money+1;
                            break;
                        case 4:
                            spirit = spirit+1;
                            break;
                        default:
                            break;
                    }
                }
                break;
            case "EVT":
                if(value == -1)
                    return;
                if(!includeEvent(value)){
                    events.add(value);
                }
                break;
            case "TLT":
                if(value == -1)
                    return;
                if(!includeTalent(value)){
                    talents.add(value);
                }
                break;
            default:
                return;
        }
    }
    public boolean includeEvent(int eventId){
        for(int key:events){
            if(key == eventId)
                return true;
        }
        return false;
    }

    public int getAge() {
        return age;
    }

    public List<Integer> getEvents() {
        return events;
    }

    public boolean includeTalent(int talentId){
        for(int key:talents){
            if(key == talentId)
                return true;
        }
        return false;
    }
    public boolean isEnd(){
        if(lifeAge <= 0){
            return true;
        }
        return false;
    }
    public List<Integer> randomAttribute(){
        do{
            List<Integer> attributeList = new ArrayList<Integer>();
            int sum = 0;
            for(int i=0;i<attributeDisplayTotal-1;i++){
                int randomData = (int) Math.floor(Math.random()*maxAttribute);
                attributeList.add(randomData);
                sum += randomData;
            }
            if(attributeTotal+additionAttr-sum>=0 && attributeTotal+additionAttr-sum<=maxAttribute){
                attributeList.add(attributeTotal+additionAttr-sum);
                return attributeList;
            }
        }while(true);
    }

    public void setAppearance(int appearance) {
        this.appearance = appearance;
    }

    public void setIntelligent(int intelligent) {
        this.intelligent = intelligent;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<String> getTalentsDescription(HashMap<Integer, Talent> talentHashMap){
        int len = talents.size();
        List<String> stringList = new ArrayList<String>();
        for(int i=0;i<len;i++){
            stringList.add(talentHashMap.get(talents.get(i)).getTalentDescription());
        }
        return stringList;
    }

    public Judgement getAppearanceSummary(){
        if(appearance>=11){
            return new Judgement(3, appearance, "逆天");
        }
        else if(appearance>=9){
            return new Judgement(2, appearance, "罕见");
        }
        else if(appearance>=7){
            return new Judgement(1, appearance, "优秀");
        }
        else if(appearance>=4){
            return new Judgement(0, appearance, "普通");
        }
        else if(appearance>=2){
            return new Judgement(0, appearance, "不佳");
        }
        else if(appearance>=1){
            return new Judgement(0, appearance, "折磨");
        }
        else{
            return new Judgement(0, appearance, "地狱");
        }
    }
    public Judgement getMoneySummary(){
        if(money>=11){
            return new Judgement(3, money, "逆天");
        }
        else if(money>=9){
            return new Judgement(2, money, "罕见");
        }
        else if(money>=7){
            return new Judgement(1, money, "优秀");
        }
        else if(money>=4){
            return new Judgement(0, money, "普通");
        }
        else if(money>=2){
            return new Judgement(0, money, "不佳");
        }
        else if(money>=1){
            return new Judgement(0, money, "折磨");
        }
        else{
            return new Judgement(0, money, "地狱");
        }
    }
    public Judgement getSpiritSummary(){
        if(spirit>=11){
            return new Judgement(3, spirit, "天命");
        }
        else if(spirit>=9){
            return new Judgement(2, spirit, "极乐");
        }
        else if(spirit>=7){
            return new Judgement(1, spirit, "幸福");
        }
        else if(spirit>=4){
            return new Judgement(0, spirit, "普通");
        }
        else if(spirit>=2){
            return new Judgement(0, spirit, "不幸");
        }
        else if(spirit>=1){
            return new Judgement(0, spirit, "折磨");
        }
        else{
            return new Judgement(0, spirit, "地狱");
        }
    }
    public Judgement getIntelligentSummary(){
        if(intelligent>=501){
            return new Judgement(3, intelligent, "仙魂");
        }
        else if(intelligent>=131){
            return new Judgement(3, intelligent, "元神");
        }
        else if(intelligent>=21){
            return new Judgement(3, intelligent, "识海");
        }
        else if(intelligent>=11){
            return new Judgement(3, intelligent, "逆天");
        }
        else if(intelligent>=9){
            return new Judgement(2, intelligent, "罕见");
        }
        else if(intelligent>=7){
            return new Judgement(1, intelligent, "优秀");
        }
        else if(intelligent>=4){
            return new Judgement(0, intelligent, "普通");
        }
        else if(intelligent>=2){
            return new Judgement(0, intelligent, "不佳");
        }
        else if(intelligent>=1){
            return new Judgement(0, intelligent, "折磨");
        }
        else{
            return new Judgement(0, intelligent, "地狱");
        }
    }
    public Judgement getStrengthSummary(){
        if(strength>=2001){
            return new Judgement(3, strength, "仙体");
        }
        else if(strength>=1001){
            return new Judgement(3, strength, "元婴");
        }
        else if(strength>=401){
            return new Judgement(3, strength, "金丹");
        }
        else if(strength>=101){
            return new Judgement(3, strength, "筑基");
        }
        else if(strength>=21){
            return new Judgement(3, strength, "凝气");
        }
        else if(strength>=11){
            return new Judgement(3, strength, "逆天");
        }
        else if(strength>=9){
            return new Judgement(2, strength, "罕见");
        }
        else if(strength>=7){
            return new Judgement(1, strength, "优秀");
        }
        else if(strength>=4){
            return new Judgement(0, strength, "普通");
        }
        else if(strength>=2){
            return new Judgement(0, strength, "不佳");
        }
        else if(strength>=1){
            return new Judgement(0, strength, "折磨");
        }
        else{
            return new Judgement(0, strength, "地狱");
        }
    }
    public Judgement getAgeSummary(){
        if(age>=500){
            return new Judgement(3, age, "仙寿");
        }
        else if(age>=100){
            return new Judgement(3, age, "修仙");
        }
        else if(age>=95){
            return new Judgement(3, age, "不老");
        }
        else if(age>=90){
            return new Judgement(2, age, "南山");
        }
        else if(age>=80){
            return new Judgement(2, age, "杖朝");
        }
        else if(age>=70){
            return new Judgement(1, age, "古稀");
        }
        else if(age>=60){
            return new Judgement(1, age, "花甲");
        }
        else if(age>=40){
            return new Judgement(0, age, "中年");
        }
        else if(age>=18){
            return new Judgement(0, age, "盛年");
        }
        else if(age>=10){
            return new Judgement(0, age, "少年");
        }
        else if(age>=1){
            return new Judgement(0, age, "早夭");
        }
        else{
            return new Judgement(0, age, "胎死腹中");
        }
    }

    public Judgement getSumSummary(){
        int sum = spirit*2+age/2;
        if(sum>=120){
            return new Judgement(3, sum, "传说");
        }
        else if(sum>=110){
            return new Judgement(3, sum, "逆天");
        }
        else if(sum>=100){
            return new Judgement(2, sum, "罕见");
        }
        else if(sum>=80){
            return new Judgement(1, sum, "优秀");
        }
        else if(sum>=60){
            return new Judgement(0, sum, "普通");
        }
        else if(sum>=50){
            return new Judgement(0, sum, "不佳");
        }
        else if(sum>=41){
            return new Judgement(0, sum, "折磨");
        }
        else{
            return new Judgement(0, sum, "地狱");
        }
    }



}
