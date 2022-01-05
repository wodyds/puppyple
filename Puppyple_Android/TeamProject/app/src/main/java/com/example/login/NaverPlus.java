package com.example.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.LoginSelect;
import com.example.ATask.NaverJoinInsert;
import com.example.ATask.NicknameCheck;
import com.example.DTO.MemberDTO;
import com.example.MainActivity;
import com.example.PUPPYPLE.R;

import java.util.concurrent.ExecutionException;

public class NaverPlus extends AppCompatActivity {
    private static final String TAG = "main:NaverPlus";

    EditText et_kakao_add_nickname;
    EditText et_kakao_add_phone;
    Button btn_nick_chk,kakao_add_yes;
    String state;
    int nickChk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakao_add_info);
        MemberDTO dto = (MemberDTO) getIntent().getSerializableExtra("dtos");
        et_kakao_add_nickname = findViewById(R.id.et_kakao_add_nickname);
        et_kakao_add_phone = findViewById(R.id.et_kakao_add_phone);
        btn_nick_chk = findViewById(R.id.btn_nick_chk);
        et_kakao_add_nickname.setText(dto.getMember_nickname());

        //닉네임 중복검사
        nickChk = 0;
        btn_nick_chk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String member_nickname = et_kakao_add_nickname.getText().toString();

                //이제 위의 정보를 비동기 Task로 넘겨 서버에게 전달한다.
                NicknameCheck nickCheck = new NicknameCheck(member_nickname);
                try {
                    state = nickCheck.execute().get().trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (state.equals("0")) {
                    Toast.makeText(NaverPlus.this,
                            "사용가능한 닉네임입니다. 계속 진행해주세요.", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder msgIdChk = new AlertDialog.Builder(NaverPlus.this)
                            .setTitle("닉네임 중복검사")
                            .setMessage("사용가능한 닉네임입니다.\n사용하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    nickChk = 1;
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    nickChk = 0;
                                }
                            });

                    AlertDialog msgDlg = msgIdChk.create();
                    msgDlg.show();


                } else {
                    Toast.makeText(NaverPlus.this,
                            "사용 중인 닉네임입니다. 다른 닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }

        });//닉네임중복검사


        kakao_add_yes = findViewById(R.id.kakao_add_yes);
        kakao_add_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String member_id = dto.getMember_id();
                String member_pw  = dto.getMember_pw();
                String member_email = dto.getMember_email();
                String member_phone = String.valueOf(et_kakao_add_phone.getText());
                String member_nickname = String.valueOf(et_kakao_add_nickname.getText());
                String member_naver = dto.getMember_naver();

                Log.d(TAG, "닉네임은?: "+member_nickname);
                Log.d(TAG, "전화번호는?: "+member_phone);
                NaverJoinInsert joinInsert = new
                        NaverJoinInsert(member_id, member_pw, member_phone,member_email, member_nickname, member_naver);
                try {
                    state = joinInsert.execute().get().trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")) {
                    Toast.makeText(NaverPlus.this,
                            "정상적으로 네이버정보를 활용하여 회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();

                    LoginSelect loginSelect = new LoginSelect(member_id,member_pw);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(NaverPlus.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(NaverPlus.this,
                            "네이버 정보를 활용한 회원가입에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
