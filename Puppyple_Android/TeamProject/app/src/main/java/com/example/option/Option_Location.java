package com.example.option;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MainActivity;
import com.example.PUPPYPLE.R;

public class Option_Location extends AppCompatActivity {

    ImageView agree_location_back,toolbar_title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_agree_location);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option_Location.this, MainActivity.class);
                startActivityForResult(intent,10);
            }
        });

        agree_location_back = findViewById(R.id.agree_location_back);
        agree_location_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.not_move_activity, R.anim.leftout_activity);

            }

        });
    }
}