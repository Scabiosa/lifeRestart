package com.example.liferestart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liferestart.entity.AgeEvent;
import com.example.liferestart.entity.Attribute;
import com.example.liferestart.entity.Event;
import com.example.liferestart.EventAdapter;
import com.example.liferestart.entity.EventToAge;
import com.example.liferestart.entity.Life;
import com.example.liferestart.R;
import com.example.liferestart.entity.Talent;
import com.example.liferestart.entity.Talents;
import com.example.liferestart.utility.UtilityClass;

import java.util.HashMap;
import java.util.List;

public class LifeExperience extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LifeExperience";
    private String ageInfo = "";
    private String eventsInfo = "";
    private UtilityClass utilityClass = new UtilityClass();
    private static final int initFlag = 1, updateText = 2, lifeEnd = 3;
    private TextView textView, appearanceText, intelligentText, strengthText, moneyText, spiritText;
    private RecyclerView recyclerView;
    private HashMap<Integer, Event> eventHashMap;
    private HashMap<Integer, AgeEvent> ageEventHashMap;
    private Button next, autoNext, endLife;
    private Life life;
    private Talents talents;
    private Attribute attribute;
    private EventAdapter eventAdapter;
    private int timeInterval = 1000;
    private boolean lifeIsEndTag;
    private LinearLayout layout;
    private Toolbar toolbar;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch(msg.what){
                case initFlag:
                    textView.setText("app 初始化成功");
                    textView.setVisibility(View.INVISIBLE);
                    layout.removeAllViews();

                    recyclerView.setVisibility(View.VISIBLE);
                    life = new Life(eventHashMap, ageEventHashMap, talents);
                    //test.setText(talentListToString(life.randomTalent()));
                    //List<Integer> talentsList = new ArrayList<>();
                    //talentsList.add(1114);
                    //talentsList.add(1065);
                    //talentsList.add(1128);
                    //talentsList.add(1048);
                    Log.d(TAG, "attribute: "+attribute.toString());
                    life.restartLife(attribute);
                    appearanceText.setText(""+life.getProperty().getAttribute().getAppearance());
                    moneyText.setText(""+life.getProperty().getAttribute().getMoney());
                    spiritText.setText(""+life.getProperty().getAttribute().getSpirit());
                    strengthText.setText(""+life.getProperty().getAttribute().getStrength());
                    intelligentText.setText(""+life.getProperty().getAttribute().getIntelligent());

//                    ConditionHandler conditionHandler = new ConditionHandler();
//                    Property property = new Property(ageEventHashMap);
//                    List<Integer> eventsList = new ArrayList<Integer>();
//                    eventsList.add(10009);
//                    List<Integer> talentList = new ArrayList<Integer>();
//                    talentList.add(1011);
//
//                    property.restartProperty(new Attribute(1, 6, 9, 8, 6, 9, 3, talentList, eventsList));
//                    boolean flag = conditionHandler.checkConditions(conditionHandler.parseCondition("(EVT?[10009])&(INT>7)&(MNY>3)"), property);
//                    Log.d(TAG, String.valueOf(flag));

                    break;
                case updateText:
                    if(!life.isLifeEnd()){
                        List<EventToAge> eventList = life.next();
                        eventAdapter.addEventToAgeList(eventList);
                        appearanceText.setText(""+life.getProperty().getAttribute().getAppearance());
                        moneyText.setText(""+life.getProperty().getAttribute().getMoney());
                        spiritText.setText(""+life.getProperty().getAttribute().getSpirit());
                        strengthText.setText(""+life.getProperty().getAttribute().getStrength());
                        intelligentText.setText(""+life.getProperty().getAttribute().getIntelligent());
                    }
                    break;
                case lifeEnd:
                    next.setText("再次重开");
                    autoNext.setText("人生总结");
                    lifeIsEndTag = true;
                    break;
                default:
                    break;
            }
        }
    };

//    private ParcelStr parcelStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_experience);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        talents = (Talents) intent.getSerializableExtra("talents");
        attribute = (Attribute) intent.getSerializableExtra("attribute");
        lifeIsEndTag = false;

        textView = findViewById(R.id.textView);
        appearanceText = findViewById(R.id.appearance);
        intelligentText = findViewById(R.id.intelligent);
        moneyText = findViewById(R.id.money);
        spiritText = findViewById(R.id.spirit);
        strengthText = findViewById(R.id.strength);
        next = findViewById(R.id.next);
        autoNext = findViewById(R.id.autoNext);
        endLife = findViewById(R.id.endLife);
        endLife.setOnClickListener(this);
        autoNext.setOnClickListener(this);
        next.setOnClickListener(this);
        layout = (LinearLayout) findViewById(R.id.loadLayout);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ageInfo = utilityClass.getinfo(utilityClass.getAgeUrl());
                eventsInfo = utilityClass.getinfo(utilityClass.getEventsUrl());
//                talentsInfo = utilityClass.getinfo(utilityClass.getTalentsUrl());
                eventHashMap = utilityClass.handleEvent(eventsInfo);
//                talentHashMap = utilityClass.handleTalent(talentsInfo);
                ageEventHashMap = utilityClass.handleAgeEvent(ageInfo);
                Message message = new Message();
                message.what = initFlag;
                handler.sendMessage(message);
            }
        }).start();
//        parcelStr = intent.getParcelableExtra("parcelStr");
//        ageInfo = parcelStr.getAgeInfo();
//        eventsInfo = parcelStr.getEventsInfo();
//        talentsInfo = parcelStr.getTalentsInfo();
//        Log.d(TAG, ageInfo);
//        Log.d(TAG, eventsInfo);
//        Log.d(TAG, talentsInfo);
        //eventList = new ArrayList<Event>();
//        init();


        //初始化recyclerView
        recyclerView = findViewById(R.id.lifeExperience);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        eventAdapter = new EventAdapter();
        //recyclerView.addItemDecoration(new RecyclerViewDecoration(LifeExperience.this));
        recyclerView.setAdapter(eventAdapter);
    }

    public String talentListToString(List<Talent> talentList){
        int len = talentList.size();
        String ans = "";
        for(int i=0;i<len;i++){
            ans = ans+talentList.get(i).getName()+", ";
        }
        return ans;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                if(!life.isLifeEnd()){
                    List<EventToAge> eventList = life.next();
                    eventAdapter.addEventToAgeList(eventList);
                    appearanceText.setText(""+life.getProperty().getAttribute().getAppearance());
                    moneyText.setText(""+life.getProperty().getAttribute().getMoney());
                    spiritText.setText(""+life.getProperty().getAttribute().getSpirit());
                    strengthText.setText(""+life.getProperty().getAttribute().getStrength());
                    intelligentText.setText(""+life.getProperty().getAttribute().getIntelligent());
                }
                else{
                    Message message = new Message();
                    message.what = lifeEnd;
                    handler.sendMessage(message);
                }
                if(lifeIsEndTag){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.autoNext:
                if(lifeIsEndTag){
                    Intent intent = new Intent(this, LifeSummary.class);
                    intent.putExtra("attribute", life.getProperty().getAttribute());
                    intent.putExtra("talents", talents);
                    startActivity(intent);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!life.isLifeEnd()){
                            Message message = new Message();
                            message.what = updateText;
                            handler.sendMessage(message);
                            try {
                                Thread.sleep(timeInterval);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Message message = new Message();
                        message.what = lifeEnd;
                        handler.sendMessage(message);

                    }
                }).start();
                break;
            case R.id.endLife:
                while(!life.isLifeEnd()){
                    List<EventToAge> eventList = life.next();
                    eventAdapter.addEventToAgeList(eventList);
                    appearanceText.setText(""+life.getProperty().getAttribute().getAppearance());
                    moneyText.setText(""+life.getProperty().getAttribute().getMoney());
                    spiritText.setText(""+life.getProperty().getAttribute().getSpirit());
                    strengthText.setText(""+life.getProperty().getAttribute().getStrength());
                    intelligentText.setText(""+life.getProperty().getAttribute().getIntelligent());
                }
                Message message = new Message();
                message.what = lifeEnd;
                handler.sendMessage(message);
                break;
            default:
                break;
        }
    }
}