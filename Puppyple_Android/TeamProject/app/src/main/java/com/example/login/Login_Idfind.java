package com.example.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.IdSelect;
import com.example.DTO.MemberDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Login_Idfind extends AppCompatActivity {
        ImageView idfind_back;
        TextView textView;
        Button Button,emailCodeButton;
        EditText emailText,emailCodeText;
        MainHandler mainHandler;
        ArrayList<MemberDTO> dtos;
        String GmailCode;


        static int value;
        int mailSend = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_idfind);

            idfind_back = findViewById(R.id.idfind_back);
            idfind_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    overridePendingTransition(0, 0); //애니메이션 없애기

                }
            });

            textView = findViewById(R.id.textView);
            Button = findViewById(R.id.Button);
            // 이메일 입력하는 에디트 텍스트
            emailText = findViewById(R.id.emailText);

            // 인증번호 받는 부분은 숨긴다.
            emailCodeText = findViewById(R.id.emailCodeText);
            emailCodeText.setVisibility(View.GONE);
            emailCodeButton = findViewById(R.id.emailCodeButton);
            emailCodeButton.setVisibility(View.GONE);

            // 이메일 인증
            // 인증 제한시간이 경과되면 인증코드를 없애버림
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MailTread mailTread = new MailTread();
                    mailTread.start();

                    if(mailSend == 0){
                        value = 180;
                        //쓰레드 객체 생성
                        BackgrounThread backgroundThread = new BackgrounThread();
                        //쓰레드 스타트
                        backgroundThread.start();
                        mailSend += 1;
                    }else{
                        value = 180;
                    }

                    // 이메일이 전송되면 보이게 한다.
                    emailCodeText.setVisibility(View.VISIBLE);
                    emailCodeButton.setVisibility(View.VISIBLE);

                    // 핸들러 객체 생성
                    mainHandler = new MainHandler();
                }
            });

            // 인증버튼
            // 만약 인증번호가 같으면 인증성공
            emailCodeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setVisibility(View.INVISIBLE);
                    if(emailCodeText.getText().toString().equals(GmailCode)){

                        Toast.makeText(getApplicationContext(), "인증성공", Toast.LENGTH_SHORT).show();
                        dtos = new ArrayList<>();
                        String member_email = emailText.getText().toString();
                        IdSelect idSelect = new IdSelect(member_email,dtos);
                        try {
                            idSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login_Idfind.this);
                        builder.setTitle("아이디 찾기 결과");
                        if(dtos.size() != 0){
                            builder.setMessage("아이디 : "+dtos.get(0).getMember_id());
                        }
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }else{
                        Toast.makeText(getApplicationContext(), "인증번호를 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        // 메일 발송하는 쓰레드
        class MailTread extends Thread{
            public void run(){
                GMailSender gMailSender = new GMailSender("wodyds@gmail.com","mild7072%%");
                GmailCode = gMailSender.getEmailCode();
                try {
                    // 제목, 본문내용, 받는사람
                    gMailSender.sendMail("퍼피플 아이디 찾기","퍼피플 아이디 찾기입니다 \n인증번호 입력란에 "+GmailCode+" 를 입력해주세요. \n인증번호는 영소문자,숫자 조합입니다.",emailText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 카운트 쓰레드
        class BackgrounThread extends Thread{
            public void run(){
                while(true){
                    value -= 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message message = mainHandler.obtainMessage();
                    // 번들에 담아서 메인 핸들러에 전달
                    Bundle bundle = new Bundle();
                    bundle.putInt("value",value);
                    message.setData(bundle);
                }
            }
        }

        // 쓰레드로부터 메세지를 받아 처리하는 핸들러
        
        class MainHandler extends Handler{
            @Override
            public void handleMessage(Message message){
                super.handleMessage(message);
                int min, sec;

                Bundle bundle = message.getData();
                int value = bundle.getInt("value");

                min = value/60;
                sec = value % 60;
                //초가 10보다 작으면 앞에 0이 더 붙어서 나오도록한다.
                if(sec<10){
                    //텍스트뷰에 시간초가 카운팅
                    emailCodeText.setHint("0"+min+" : 0"+sec);
                }else {
                    emailCodeText.setHint("0"+min+" : "+sec);
                }
            }
        }
        
    }


