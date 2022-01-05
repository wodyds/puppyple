package com.example.option;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PUPPYPLE.R;

public class Event_Img extends AppCompatActivity {

    ImageView option_event_back;
    LinearLayout event_firstlist;
    LinearLayout event_secondlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_img);

        option_event_back = findViewById(R.id.option_event_back);
        option_event_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        event_firstlist = findViewById(R.id.event_firstlist);
        event_secondlist = findViewById(R.id.event_secondlist);

        // 이벤트 배너 클릭이벤트
        event_firstlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Event_Img.this, First_Event.class);
                startActivity(intent);
            }
        });

        // 이벤트 배너 클릭 이벤트
        event_secondlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Event_Img.this, Second_Event.class);
                startActivity(intent);
            }
        });

    }
}
