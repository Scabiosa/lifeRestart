package com.example.liferestart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liferestart.R;
import com.example.liferestart.entity.Talent;
import com.example.liferestart.entity.Talents;
import com.example.liferestart.utility.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DrawTenCard extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "DrawTenCard";
    private Button talentSelect;
    private String talentsInfo = "";
    private HashMap<Integer, Talent> talentHashMap;
    private static final int UPDATE_TEXT = 2;
    private Talents talents;
    private List<Talent> talentList;
    private TextView[] cardList;
    private int cardTotal = 10;
    private ArrayList<Integer> talentSelectedList;
    private Toolbar toolbar;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch(msg.what){
                case UPDATE_TEXT:
                    talents = new Talents(talentHashMap);
                    List<Talent> listTalent = talents.talentRandom(cardTotal-talentList.size());
                    for(int i=0;i<listTalent.size();i++){
                        talentList.add(listTalent.get(i));

                    }
                    for(int i=0;i<talentList.size();i++){
                        Talent talent = talentList.get(i);
                        cardList[i].setText(talent.getTalentDescription());
                        if(talent.getGrade()==3){
                            //Log.d(TAG, "3: "+talent.toString());
                            cardList[i].setBackgroundResource(R.drawable.radiobutton_background_grade3);
                            //cardList[i].invalidate();
                        }
                        else if(talent.getGrade()==2){
                            //Log.d(TAG, "2: "+talent.toString());
                            cardList[i].setBackgroundResource(R.drawable.radiobutton_background_grade2);
                            //cardList[i].invalidate();
                        }
                        else if(talent.getGrade()==1){
                            //Log.d(TAG, "1: "+talent.toString());
                            cardList[i].setBackgroundResource(R.drawable.radiobutton_background_grade1);
                            //cardList[i].invalidate();
                        }
                        else{
                            //Log.d(TAG, "0: "+talent.toString());
                            cardList[i].setBackgroundResource(R.drawable.radiobutton_background_grade0);
                            //cardList[i].invalidate();
                        }
                    }
                    talentSelectedList = new ArrayList<Integer>();
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_ten_card);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        talentList = (List<Talent>) intent.getSerializableExtra("talentList");
        if (talentList == null){
            talentList = new ArrayList<Talent>();
        }



        talentSelect = findViewById(R.id.talentSelect);
        talentSelect.setOnClickListener(this);
        cardList = new TextView[cardTotal];
        cardList[0] = findViewById(R.id.cardOne);
        cardList[1] = findViewById(R.id.cardTwo);
        cardList[2] = findViewById(R.id.cardThree);
        cardList[3] = findViewById(R.id.cardFour);
        cardList[4] = findViewById(R.id.cardFive);
        cardList[5] = findViewById(R.id.cardSix);
        cardList[6] = findViewById(R.id.cardSeven);
        cardList[7] = findViewById(R.id.cardEight);
        cardList[8] = findViewById(R.id.cardNine);
        cardList[9] = findViewById(R.id.cardTen);
        for(int i=0;i<cardTotal;i++){
            cardList[i].setOnClickListener(this);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                UtilityClass utilityClass = new UtilityClass();
                talentsInfo = utilityClass.getinfo(utilityClass.getTalentsUrl());
                talentHashMap = utilityClass.handleTalent(talentsInfo);
                Message message = new Message();
                message.what = UPDATE_TEXT;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.talentSelect:
                Intent intent = new Intent(this, ModifyAttribute.class);
                Log.d(TAG, talents.toString());
//                int additionAttr = 0;
//                for(int i=0;i<talentSelectedList.size();i++){
//                    Talent talent = talentHashMap.get(talentSelectedList.get(i));
//                    if(talent.getStatus()!=null){
//                        additionAttr += Integer.parseInt(talent.getStatus());
//                    }
//                }
//                intent.putExtra("additionAttr", additionAttr);
                intent.putExtra("talents", talents);
                intent.putIntegerArrayListExtra("talentSelectedList", talentSelectedList);
                startActivity(intent);
                break;
            case R.id.cardOne:
                clickEvent(0);
                break;
            case R.id.cardTwo:
                clickEvent(1);
                break;
            case R.id.cardThree:
                clickEvent(2);
                break;
            case R.id.cardFour:
                clickEvent(3);
                break;
            case R.id.cardFive:
                clickEvent(4);
                break;
            case R.id.cardSix:
                clickEvent(5);
                break;
            case R.id.cardSeven:
                clickEvent(6);
                break;
            case R.id.cardEight:
                clickEvent(7);
                break;
            case R.id.cardNine:
                clickEvent(8);
                break;
            case R.id.cardTen:
                clickEvent(9);
                break;
            default:
                break;
        }
    }
    public void clickEvent(int i){
        if(cardList[i].isSelected()){
            Iterator<Integer> iter = talentSelectedList.iterator();
            while (iter.hasNext()) {
                int id = iter.next();
                if(id == talentList.get(i).getId())
                    iter.remove();
            }
            cardList[i].setSelected(false);
        }
        else{
            int sum = 0;
            for(int j=0;j<cardTotal;j++){
                if(cardList[j].isSelected()){
                    sum++;
                }
            }
            if(sum>=3){
                Toast.makeText(this, "最多选择三个天赋", Toast.LENGTH_SHORT).show();
                return;
            }
            talentSelectedList.add(talentList.get(i).getId());
            cardList[i].setSelected(true);
        }
    }
}