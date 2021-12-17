package com.example.liferestart.utility;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liferestart.entity.AgeEvent;
import com.example.liferestart.entity.Event;
import com.example.liferestart.entity.Talent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class UtilityClass {

    private static final String TAG = "utilityClass";
    private final String ageUrl = "age.json";
    private final String eventsUrl = "events.json";
    private final String talentsUrl = "talents.json";
    private final String internetUrl = "http://172.30.249.137:8080/web/";

    public String getAgeUrl() {
        return ageUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public String getTalentsUrl() {
        return talentsUrl;
    }

    public String getInternetUrl() {
        return internetUrl;
    }

    public String getinfo(String fileName) {
//        Log.d(TAG, "getNewsFromInternet");
        String url = internetUrl;
        try {
            // 得到访问地址的URL
            URL mURL = new URL(url+fileName);
            // 得到网络访问对象java.net.HttpURLConnection
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
            // 设置请求方式
            mHttpURLConnection.setRequestMethod("GET");
            // 设置链接超时时间
            mHttpURLConnection.setConnectTimeout(8000);
            // 设置读取超时时间
            mHttpURLConnection.setReadTimeout(8000);
            // 连接
            mHttpURLConnection.connect();

            int statusCode = mHttpURLConnection.getResponseCode();
//            Log.d(TAG, "get "+fileName+", statusCode: " + statusCode);
            if(statusCode == 200) {
//                Log.d(TAG, "get "+fileName+", success");
                InputStream is = mHttpURLConnection.getInputStream();
                StringBuilder sb = new StringBuilder();
                String line;

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                String str = sb.toString();
//                Log.d(TAG, fileName+": " + str);
                return str;

            } else {
                Log.d(TAG, "get "+fileName+", failed");
                Log.d(TAG, "get "+fileName+", 访问失败: " + statusCode);
            }
        } catch (Exception e) {
            Log.d(TAG, "get "+fileName+", something go wrong");
            e.printStackTrace();
        }
        return "";
    }
    public HashMap<Integer, Event> handleEvent(String eventInfo){
        JSONObject jsonEventDescription = JSON.parseObject(eventInfo);
        HashMap<Integer, Event> hashMap = new HashMap<Integer, Event>();
        for(int i=10000;i<=40084;i++){
            String eventDescription = jsonEventDescription.getString(""+i);
            if(eventDescription!=null){
                //Log.d(TAG, ""+i+": "+eventDescription);
                JSONObject jsonEvent = JSON.parseObject(eventDescription);
                int id = jsonEvent.getInteger("id");
                int noRandom = 0;
                if(jsonEvent.getInteger("NoRandom")!=null){
                    noRandom = jsonEvent.getInteger("NoRandom");
                }
                Event event = new Event(jsonEvent.getInteger("id"),
                        jsonEvent.getString("event"), jsonEvent.getString("effect"),
                        jsonEvent.getString("exclude"), jsonEvent.getString("postEvent"),
                        jsonEvent.getString("branch"), jsonEvent.getString("include"), noRandom);
                hashMap.put(id, event);
            }
        }
//        Log.d(TAG, hashMap.get(10000).toString());
        return hashMap;
    }
    public HashMap<Integer, Talent> handleTalent(String talentInfo){
        JSONObject jsonTalentDescription = JSON.parseObject(talentInfo);
        HashMap<Integer, Talent> hashMap = new HashMap<Integer, Talent>();
        for(int i=1001;i<=1146;i++){
            String talentDescription = jsonTalentDescription.getString(""+i);
            if(talentDescription!=null){
                JSONObject jsonTalent = JSON.parseObject(talentDescription);
                int id = jsonTalent.getInteger("id");
//                int noRandom = 0;
//                if(jsonEvent.getInteger("NoRandom")!=null){
//                    noRandom = jsonEvent.getInteger("NoRandom");
//                }
                Talent talent = new Talent(jsonTalent.getInteger("id"), jsonTalent.getInteger("grade"),
                        jsonTalent.getString("name"), jsonTalent.getString("description"),
                        jsonTalent.getString("exclusive"), jsonTalent.getString("effect"),
                        jsonTalent.getString("status"), jsonTalent.getString("condition"),
                        jsonTalent.getString("replacement"));
                hashMap.put(id, talent);
            }
        }
//        Log.d(TAG, hashMap.get(1146).toString());
        return hashMap;
    }

    public HashMap<Integer, AgeEvent> handleAgeEvent(String ageInfo){
        JSONObject jsonAgeEventDescription = JSON.parseObject(ageInfo);
        HashMap<Integer, AgeEvent> hashMap = new HashMap<Integer, AgeEvent>();
        for(int i=0;i<=500;i++){
            String ageEventDescription = jsonAgeEventDescription.getString(""+i);
            if(ageEventDescription!=null){
                JSONObject jsonAgeEvent = JSON.parseObject(ageEventDescription);
                int age = jsonAgeEvent.getInteger("age");
                HashMap<Integer, Double> ageEventHashMap = new HashMap<Integer, Double>();
                JSONArray jsonAgeEventStr = jsonAgeEvent.getJSONArray("event");
                for (Object obj:jsonAgeEventStr) {
                    String str = obj.toString();
                    int index = str.indexOf("*");
                    if(index == -1){
                        ageEventHashMap.put(Integer.parseInt(str), 1.0);
                    }
                    else{
                        String id = str.substring(0, index);
                        String probability = str.substring(index+1);
                        //Log.d(TAG, id+": "+probability);
                        try {
                            ageEventHashMap.put(Integer.parseInt(id), Double.parseDouble(probability));
                        }
                        catch (NumberFormatException e){
                            e.printStackTrace();
                        }

                    }
                }
                AgeEvent ageEvent = new AgeEvent(jsonAgeEvent.getInteger("age"), ageEventHashMap);
                hashMap.put(age, ageEvent);
            }
        }
//        Log.d(TAG, hashMap.get(499).toString());
        return hashMap;
    }


}
