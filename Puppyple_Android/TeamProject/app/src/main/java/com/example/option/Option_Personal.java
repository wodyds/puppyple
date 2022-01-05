package com.example.option;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MainActivity;
import com.example.PUPPYPLE.R;

public class Option_Personal extends AppCompatActivity {

    ImageView agree_personal_back,toolbar_title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_agree_personal);


        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option_Personal.this, MainActivity.class);
                startActivityForResult(intent,10);
            }
        });

        agree_personal_back = findViewById(R.id.agree_personal_back);
        agree_personal_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });
    }
}