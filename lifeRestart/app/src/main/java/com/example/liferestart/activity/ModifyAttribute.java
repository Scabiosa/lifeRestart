package com.example.liferestart.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liferestart.entity.Attribute;
import com.example.liferestart.utility.MyTextWatcher;
import com.example.liferestart.R;
import com.example.liferestart.entity.Talent;
import com.example.liferestart.entity.Talents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModifyAttribute extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ModifyAttribute";
    private Button startLife, randomAttribute;
    private Talents talents;
    private ArrayList<Integer> talentSelectedList;
    private HashMap<Integer, Talent> talentHashMap;
    private TextView[] talentTextViewList;
    private int talentDisplayTotal = 3;
    private int attributeDisplayTotal = 4;
    private Attribute attribute;
    private EditText[] attributeValueList;
    private int additionAttr;
    private TextView attributeAll;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_attribute);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        startLife = findViewById(R.id.startLife);
        randomAttribute = findViewById(R.id.randomAttribute);
        randomAttribute.setOnClickListener(this);
        startLife.setOnClickListener(this);
        Intent intent = getIntent();
        talents = (Talents) intent.getSerializableExtra("talents");
        talentSelectedList = intent.getIntegerArrayListExtra("talentSelectedList");
        additionAttr = 0;
        if(talents==null)
            Log.d(TAG, "talents is null");
        else
            Log.d(TAG, talents.toString());
        for(int i=0;i<talentSelectedList.size();i++){
            Log.d(TAG, ""+talentSelectedList.get(i));
        }
        talentHashMap = talents.getTalentHashMap();
        talentTextViewList = new TextView[talentDisplayTotal];
        talentTextViewList[0] = findViewById(R.id.cardSelectedOne);
        talentTextViewList[1] = findViewById(R.id.cardSelectedTwo);
        talentTextViewList[2] = findViewById(R.id.cardSelectedThree);
        attributeAll = findViewById(R.id.attributeAll);
        talents = new Talents(talentHashMap);
        for(int i=0;i<talentSelectedList.size();i++){
            Talent talent = talentHashMap.get(talentSelectedList.get(i));
            if(i<talentDisplayTotal){
                setCardList(talentTextViewList[i], talent);
            }
            if(talent.getStatus()!=null){
                additionAttr += Integer.parseInt(talent.getStatus());
            }
        }
        attribute = new Attribute(talentSelectedList);
        attribute.setAdditionAttr(additionAttr);
        attributeValueList = new EditText[attributeDisplayTotal];
        attributeValueList[0] = findViewById(R.id.appearanceValue);
        attributeValueList[1] = findViewById(R.id.intelligentValue);
        attributeValueList[2] = findViewById(R.id.strengthValue);
        attributeValueList[3] = findViewById(R.id.moneyValue);
        attributeAll.setText(""+attribute.getAttributeAll());

        attributeValueList[0].addTextChangedListener(new MyTextWatcher(attribute.getAttributeAll(),
                attributeValueList[1], attributeValueList[2], attributeValueList[3], attributeAll));
        attributeValueList[1].addTextChangedListener(new MyTextWatcher(attribute.getAttributeAll(),
                attributeValueList[0], attributeValueList[2], attributeValueList[3], attributeAll));
        attributeValueList[2].addTextChangedListener(new MyTextWatcher(attribute.getAttributeAll(),
                attributeValueList[1], attributeValueList[0], attributeValueList[3], attributeAll));
        attributeValueList[3].addTextChangedListener(new MyTextWatcher(attribute.getAttributeAll(),
                attributeValueList[1], attributeValueList[2], attributeValueList[0], attributeAll));
    }

    public void setCardList(TextView textView, Talent talent){
        textView.setText(talent.getTalentDescription());
        setBackGroundColor(textView, talent.getGrade());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startLife:
                if(getData(attributeAll)<0){
                    new AlertDialog.Builder(this).setTitle("警告").setMessage("没有可分配的点数了")
                            .setPositiveButton("确定", null).show();
                }
                else if(getData(attributeAll)>0){
                    new AlertDialog.Builder(this).setTitle("提示")
                            .setMessage("你还有"+getData(attributeAll)+"属性点没分配").setPositiveButton("确定", null).show();
                }
                else if(getData(attributeValueList[0])>10 || getData(attributeValueList[1])>10||getData(attributeValueList[2])>10 || getData(attributeValueList[3])>10){
                    new AlertDialog.Builder(this).setTitle("警告").setMessage("属性值不能超过10")
                            .setPositiveButton("确定", null).show();
                }
                else{
                    attribute.setAppearance(Integer.parseInt(attributeValueList[0].getText().toString()));
                    attribute.setIntelligent(Integer.parseInt(attributeValueList[1].getText().toString()));
                    attribute.setStrength(Integer.parseInt(attributeValueList[2].getText().toString()));
                    attribute.setMoney(Integer.parseInt(attributeValueList[3].getText().toString()));
                    Intent intent = new Intent(this, LifeExperience.class);
                    intent.putExtra("attribute", attribute);
                    intent.putExtra("talents", talents);
                    startActivity(intent);
                }
                break;
            case R.id.randomAttribute:
                List<Integer> attributeList = attribute.randomAttribute();
                int leftAttr = attribute.getAttributeAll();
                for(int i=0;i<attributeDisplayTotal;i++){
                    Log.d(TAG, ""+attributeList.get(i));
                    attributeValueList[i].setText(""+attributeList.get(i));
                    leftAttr = leftAttr-attributeList.get(i);
                }
                attributeAll.setText(""+leftAttr);
                break;
            default:
                break;
        }
    }

    public int getData(TextView textView){
        int data;
        try{
            data = Integer.parseInt(textView.getText().toString());
        }catch (NumberFormatException e){
            data = 0;
        }
        return data;
    }
    public int getData(EditText editText){
        int data;
        try{
            data = Integer.parseInt(editText.getText().toString());
        }catch (NumberFormatException e){
            data = 0;
        }
        return data;
    }


}