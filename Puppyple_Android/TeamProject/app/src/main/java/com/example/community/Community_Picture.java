package com.example.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.PUPPYPLE.R;

public class Community_Picture extends AppCompatActivity {

    ImageView iv_picture,btn_close;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_detail_picture);
        iv_picture = findViewById(R.id.iv_picture);
        btn_close = findViewById(R.id.btn_close);

        // btn_close click event
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });

        Intent intent = getIntent();
        String board_filename = intent.getExtras().getString("board_filename");

        Glide.with(Community_Picture.this)
                .load(board_filename)
                .into(iv_picture);

    }
}
