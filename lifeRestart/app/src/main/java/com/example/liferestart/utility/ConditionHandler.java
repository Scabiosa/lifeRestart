package com.example.liferestart.utility;

import com.alibaba.fastjson.JSON;
import com.example.liferestart.entity.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionHandler {
    private static final String TAG = "ConditionHandler";

    public boolean checkParsedConditions(List<String> conditions, Property property){
//        Log.d(TAG, ""+conditions.size());

        if(conditions.size() == 0)
            return true;
        else if(conditions.size() == 1)
            return checkParsedCondition(conditions.get(0), property);
        boolean ret = checkParsedCondition(conditions.get(0), property);
        for(int i=1;i<conditions.size();i+=2){
            //Log.d(TAG, String.valueOf(ret));
            switch (conditions.get(i)){
                case "|":
                    if(ret)
                        return true;
                    ret = checkParsedCondition(conditions.get(i+1), property);
                    break;
                case "&":
                    if(ret)
                        ret = checkParsedCondition(conditions.get(i+1), property);
                    break;
                default:
                    return false;
            }
        }

        return ret;
    }
    public boolean checkConditions(String condition, Property property){
        List<String> conditionList = parseCondition(condition);
        return checkParsedConditions(conditionList, property);
    }

    public boolean checkParsedCondition(String condition, Property property){
        //Log.d(TAG, condition);
        int len = condition.length();
        String pattern="[><\\!\\?=]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(condition);
        if(m.find() ==true) {
            int i = m.start();
            String prop = condition.substring(0, i);
            int addtion = condition.charAt(i+1) == '='? 2:1;
            String symbol = condition.substring(i, i+addtion);
            String d = condition.substring(i+addtion, len);
//            Log.d(TAG, prop+" "+symbol+" "+d);
            //int conditionData = d.charAt(0) == '['? (int) JSON.parse(d) :Integer.parseInt(d);
            if(prop.equals("EVT") || prop.equals("TLT")){
                List<Integer> propData = property.getAttribute().getPropArray(prop);
                if(d.charAt(0) == '['){
                    List<Integer> conditionData = (List<Integer>) JSON.parse(d);
                    //Log.d(TAG, listToString(propData));
                    //Log.d(TAG, listToString(conditionData));
                    switch (symbol){
                        case "?":
                            for(int val:propData){
                                if(conditionData.contains(val))
                                    return true;
                            }
                            return false;
                        case "!":
                            for(int val:propData){
                                if(conditionData.contains(val))
                                    return false;
                            }
                            return true;
                        default:
                            return false;
                    }
                }
                else{
                    int conditionData = (int) JSON.parse(d);
                    switch (symbol){
                        case "==":
                            return propData.contains(conditionData);
                        case "!=":
                            return !propData.contains(conditionData);
                        default:
                            return false;
                    }
                }
            }
            else{
                int propData = property.getAttribute().getPropInteger(prop);
                if(d.charAt(0) == '['){
                    List<Integer> conditionData = (List<Integer>) JSON.parse(d);
                    switch (symbol){
                        case "?":
                            return conditionData.contains(propData);
                        case "!":
                            return !conditionData.contains(propData);
                        default:
                            return false;
                    }
                }
                else{
                    int conditionData = (int) JSON.parse(d);
                    //Log.d(TAG, prop+": "+propData+", "+symbol+"    "+d+": "+conditionData);
                    switch (symbol){
                        case "<":
                            return propData<conditionData;
                        case ">":
                            return propData>conditionData;
                        case "<=":
                            return propData<=conditionData;
                        case ">=":
                            return propData>=conditionData;
                        case "==":
                            return propData==conditionData;
                        case "!=":
                            return propData!=conditionData;
                        default:
                            return false;
                    }
                }

            }

        }
        return false;
    }

    public List<String> parseCondition(String condition){
        condition = condition.trim();
        List<String> conditionList = new ArrayList<String>();
        int cursor = 0;
        int len = condition.length();
        for(int i=0;i<len;i++){
            switch (condition.charAt(i)){
                case ' ':
                    break;
                case '(':
                    cursor = i+1;
                    break;
                case ')':
                    conditionList.add(condition.substring(cursor, i).trim());
                    cursor = i+1;
                    break;
                case '&':
                case '|':
                    //conditionList.add(condition.substring(cursor, i).trim());
                    conditionList.add(condition.substring(i, i+1).trim());
                    cursor = i+1;
                    break;
                default:
                    break;

            }
        }
        if(cursor!=len)
            conditionList.add(condition.substring(cursor, len).trim());
        String str = "";
//        //Log.d(TAG, condition);
//        for(String value:conditionList)
//            str = str+value+", ";
//        //Log.d(TAG, str);
        return conditionList;
    }


    public <E>boolean isListEqual(List<E> list1, List<E> list2) {
        // 两个list引用相同（包括两者都为空指针的情况）
        if (list1 == list2) {
            return true;
        }

        // 两个list都为空（包括空指针、元素个数为0）
        if ((list1 == null && list2 != null && list2.size() == 0)
                || (list2 == null && list1 != null && list1.size() == 0)) {
            return true;
        }

        // 两个list元素个数不相同
        if (list1.size() != list2.size()) {
            return false;
        }

        // 两个list元素个数已经相同，再比较两者内容
        // 采用这种可以忽略list中的元素的顺序
        // 涉及到对象的比较是否相同时，确保实现了equals()方法
        if (!list1.containsAll(list2)) {
            return false;
        }

        return true;
    }

    public String listToString(List<Integer> array){
        int len = array.size();
        String str = "";
        for(int e:array){
            str = str+e+", ";
        }
        return str;
    }




}
