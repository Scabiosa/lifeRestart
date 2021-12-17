package com.example.liferestart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liferestart.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Button lifeReStart;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lifeReStart = findViewById(R.id.lifeReStart);
        lifeReStart.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//        List<String> stringList = conditionHandler.parseCondition("(AGE?[20])&(CHR>8)");
//        for(String v: stringList){
//            Log.d(TAG, v);
//        }
//        int data = (int) JSON.parse("10007");
//
//        List<Integer> strList = (List<Integer>) JSON.parse("[10007,10008]");
//        for(int v: strList){
//            Log.d(TAG, ""+v);
//        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lifeReStart:
                Intent intent = new Intent(MainActivity.this, DrawCard.class);
//                intent.putExtra("ageFile", ageInfo);
//                intent.putExtra("eventsFile", eventsInfo);
//                intent.putExtra("parcelStr", new ParcelStr(ageInfo, eventsInfo, talentsInfo));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}