//package com.example.option;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Vibrator;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Switch;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.PUPPYPLE.R;
//
//public class Alert_Setting extends AppCompatActivity {
//
//    ImageView option_alert_back;
//    Button alert_cancel;
//    Switch alert_switch;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.option_alert);
//
//
//        option_alert_back = findViewById(R.id.option_alert_back);
//        option_alert_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//                overridePendingTransition(0, 0); //애니메이션 없애기
//            }
//        });
//
//        alert_cancel = findViewById(R.id.alert_cancel);
//        alert_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onBackPressed();
//                overridePendingTransition(0, 0); //애니메이션 없애기
//
//            }
//        });
//
//        // 댓글 알림 - 진동으로 울리기
//        findViewById(R.id.alert_comment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//
//                    vibrator.vibrate(200);
//
//            }
//        });
//
//        // 좋아요 알림 - 진동으로 울리기
//        findViewById(R.id.alert_like).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//
//                vibrator.vibrate(200);
//
//            }
//        });
//    }
//}
