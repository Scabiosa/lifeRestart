package com.example.liferestart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liferestart.R;
import com.example.liferestart.activity.DrawTenCard;

public class DrawCard extends AppCompatActivity implements View.OnClickListener {

    private Button drawCard;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_card);
        drawCard = findViewById(R.id.drawCard);
        drawCard.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.drawCard:
                Intent intent = new Intent(this, DrawTenCard.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}