package com.example.option;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PUPPYPLE.R;

public class Option_pet_info extends AppCompatActivity {

    Button btnMyPagePetCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_pet_info_add);

        btnMyPagePetCancel = findViewById(R.id.btnMyPagePetCancel);
        btnMyPagePetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });
    }
}