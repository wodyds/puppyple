package com.example.join;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PUPPYPLE.R;

public class SubActivityLocation extends AppCompatActivity {

    ImageView post_back;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_agree_location);

        textView = findViewById(R.id.tvSubLocationContent);
        textView.setMovementMethod(new ScrollingMovementMethod());

        // 뒤로가기 버튼 클릭 이벤트
        post_back = findViewById(R.id.post_back);
        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });

        //main으로 돌아가기
        findViewById(R.id.btnSubJoinReturn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivityLocation.this, Join_Activity.class);
                //startActivity(intent);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}