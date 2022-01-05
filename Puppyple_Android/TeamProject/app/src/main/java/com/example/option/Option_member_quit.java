package com.example.option;

import static com.example.Common.CommonMethod.loginDTO;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.MemberDelete;
import com.example.MainActivity;
import com.example.PUPPYPLE.R;

import java.util.concurrent.ExecutionException;

public class Option_member_quit extends AppCompatActivity {

    ImageView btnJoinReturn;
    Button btnJoinQuit;
    Dialog dialog;
    TextView dialog_member_quit_yes,dialog_member_quit_no ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_member_quit);

        dialog = new Dialog(Option_member_quit.this);       // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.dialog_member_quit);             // xml 레이아웃 파일과 연결
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btnJoinReturn = findViewById(R.id.btnJoinReturn);
        btnJoinReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        btnJoinQuit = findViewById(R.id.btnJoinQuit);
        btnJoinQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage();
            }
        });
    }

        public void showMessage() {
            dialog.show(); // 다이얼로그 띄우기

            /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

            // 위젯 연결 방식은 각자 취향대로~
            // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
            // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
            // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

            // 아니오 버튼
            dialog_member_quit_no = dialog.findViewById(R.id.dialog_member_quit_no);
            dialog_member_quit_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 원하는 기능 구현
                    finish(); // 다이얼로그 닫기
                    overridePendingTransition(0, 0); //애니메이션 없애기

                }
            });

            // 예 버튼
            //20211124 회원탈퇴기능 구현 from JP
            dialog_member_quit_yes = dialog.findViewById(R.id.dialog_member_quit_yes);
            dialog_member_quit_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (loginDTO != null) {
                        String member_id = loginDTO.getMember_id();

                        MemberDelete memberDelete = new MemberDelete(member_id);

                        //서버로 데이터를 보낸다
                        try {
                            memberDelete.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(Option_member_quit.this,
                                "로그인해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //로그인조회된 정보 초기화
                    loginDTO = null;

                    Intent intent = new Intent(Option_member_quit.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
