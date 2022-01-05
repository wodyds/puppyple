package com.example.option;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView Mypage;
    TextView Alert;
    TextView Notice;
    TextView FAQ;
    TextView Access1;
    TextView Access2;
    TextView Access3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 클릭이벤트

        // 마이페이지
        Mypage = findViewById(R.id.Mypage);
        Mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 알림설정
        Alert = findViewById(R.id.Alert);
        Alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 공지사항
        Notice = findViewById(R.id.Notice);
        Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // FAQ
        FAQ = findViewById(R.id.FAQ);
        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 서비스 이용약관
        Access1 = findViewById(R.id.Access1);
        Access1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Access1.class);
                startActivity(intent);

            }
        });

        // 개인정보 취급방침
        Access2 = findViewById(R.id.Access2);
        Access2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Access2.class);
                startActivity(intent);
            }
        });

        // 위치기반 서비스 이용약관
        Access3 = findViewById(R.id.Access3);
        Access3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Access3.class);
                startActivity(intent);
            }
        });

    }
}