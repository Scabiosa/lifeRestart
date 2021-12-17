package com.example.liferestart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liferestart.entity.Attribute;
import com.example.liferestart.R;
import com.example.liferestart.entity.Talent;
import com.example.liferestart.entity.Talents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LifeSummary extends AppCompatActivity implements View.OnClickListener{

    private TextView ageText, appearanceText, intelligentText, spiritText, moneyText,
            sumText, strengthText;
    private TextView[] cardList;
    private Talents talents;
    private Attribute attribute;
    private Button restart;
    private List<Talent> talentList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_summary);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        talents = (Talents) intent.getSerializableExtra("talents");
        attribute = (Attribute) intent.getSerializableExtra("attribute");

        talentList = new ArrayList<Talent>();

        ageText = findViewById(R.id.age);
        appearanceText = findViewById(R.id.appearance);
        intelligentText = findViewById(R.id.intelligent);
        spiritText = findViewById(R.id.spirit);
        moneyText = findViewById(R.id.money);
        sumText = findViewById(R.id.sum);
        strengthText = findViewById(R.id.strength);
        cardList = new TextView[3];
        cardList[0] = findViewById(R.id.cardSelectedOne);
        cardList[1] = findViewById(R.id.cardSelectedTwo);
        cardList[2] = findViewById(R.id.cardSelectedThree);
        cardList[0].setOnClickListener(this);
        cardList[1].setOnClickListener(this);
        cardList[2].setOnClickListener(this);
        restart = findViewById(R.id.restart);
        restart.setOnClickListener(this);

        ageText.setText("享年: "+attribute.getAgeSummary().getEvaluate());
        setBackGroundColor(ageText, attribute.getAgeSummary().getGrade());
        appearanceText.setText("颜值: "+attribute.getAppearanceSummary().getEvaluate());
        setBackGroundColor(appearanceText, attribute.getAppearanceSummary().getGrade());
        intelligentText.setText("智力: "+attribute.getIntelligentSummary().getEvaluate());
        setBackGroundColor(intelligentText, attribute.getIntelligentSummary().getGrade());
        spiritText.setText("快乐: "+attribute.getSpiritSummary().getEvaluate());
        setBackGroundColor(spiritText, attribute.getSpiritSummary().getGrade());
        moneyText.setText("家境: "+attribute.getMoneySummary().getEvaluate());
        setBackGroundColor(moneyText, attribute.getMoneySummary().getGrade());
        sumText.setText("总评: "+attribute.getSumSummary().getEvaluate());
        setBackGroundColor(sumText, attribute.getSumSummary().getGrade());
        strengthText.setText("体力: "+attribute.getStrengthSummary().getEvaluate());
        setBackGroundColor(strengthText, attribute.getStrengthSummary().getGrade());



        for(int i=0;i<attribute.getTalents().size();i++){
            setCardList(cardList[i], talents.getTalentHashMap().get(attribute.getTalents().get(i)));
        }
    }

    public void setBackGroundColor(TextView textView, int grade){
        if(grade==3){
            textView.setBackgroundResource(R.drawable.radiobutton_background_grade3);
        }
        else if(grade==2){
            textView.setBackgroundResource(R.drawable.radiobutton_background_grade2);
        }
        else if(grade==1){
            textView.setBackgroundResource(R.drawable.radiobutton_background_grade1);
        }
        else{
            textView.setBackgroundResource(R.drawable.radiobutton_background_grade0);
        }
    }


    public void setCardList(TextView textView, Talent talent){
        textView.setText(talent.getTalentDescription());
        setBackGroundColor(textView, talent.getGrade());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.restart:
                Intent intent = new Intent(this, DrawTenCard.class);
                intent.putExtra("talentList", (Serializable) talentList);
                startActivity(intent);
                break;
            case R.id.cardSelectedOne:
                clickEvent(0);
                break;
            case R.id.cardSelectedTwo:
                clickEvent(1);
                break;
            case R.id.cardSelectedThree:
                clickEvent(2);
                break;
            default:
                break;
        }
    }

    public void clickEvent(int i){
        if(cardList[i].isSelected()){
            Iterator<Talent> iter = talentList.iterator();
            while (iter.hasNext()) {
                Talent talent = iter.next();
                if(talent.getId() == talents.getTalentHashMap().get(attribute.getTalents().get(i)).getId())
                    iter.remove();
            }
            cardList[i].setSelected(false);
        }
        else{
            int sum = 0;
            for(int j=0;j<3;j++){
                if(cardList[j].isSelected()){
                    sum++;
                }
            }
            if(sum>=1){
                Toast.makeText(this, "最多选择一个天赋", Toast.LENGTH_SHORT).show();
                return;
            }
            talentList.add(talents.getTalentHashMap().get(attribute.getTalents().get(i)));
            cardList[i].setSelected(true);
        }
    }
}