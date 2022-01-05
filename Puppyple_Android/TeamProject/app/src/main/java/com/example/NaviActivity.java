package com.example;

import static com.example.Common.CommonMethod.dtos;
import static com.example.Common.CommonMethod.loginDTO;
import static com.example.login.Login_Activity.mOAuthLoginInstance;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.PUPPYPLE.R;
import com.example.login.Login_Activity;
import com.example.option.Event_Img;
import com.example.option.Option_FAQ;
import com.example.option.Option_FAQ_admin;
import com.example.option.Option_Mypage;
import com.example.option.Option_Notice;
import com.example.option.Option_Notice_admin;
import com.example.option.Option_inquiry;
import com.example.option.Option_inquiry_admin;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class NaviActivity extends AppCompatActivity {

    ImageButton navi_back;
    TextView navi_name,navi_cu_center, navi_mypage, navi_alert, navi_notice, navi_faq, navi_event, navi_login, navi_inquiry;
    ImageView navi_Img;
    String kakao="",naver="";
    MainActivity mActivity;
    private Login_Activity.SessionCallback callback;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        //20211127 로그인시 사진보이도록 메인화면과 같이 변경_JP

        navi_Img = findViewById(R.id.navi_Img);

        if (loginDTO == null) {
            navi_Img.setImageResource(R.drawable.user_basic2);
        } else if (loginDTO != null && dtos == null) {
            navi_Img.setImageResource(R.drawable.user_basic2);
        } else if (loginDTO != null && dtos.size() > 0) {
            String pet_filename = dtos.get(0).getPet_filename();
            Glide.with(NaviActivity.this)
                    .load(pet_filename)
                    .circleCrop()
                    .into(navi_Img);
        }
        //20211127/////////////////////////////////////////////////////

        navi_name = findViewById(R.id.navi_name);
        navi_Img = findViewById(R.id.navi_Img);
        // 로그인한 정보가 있다면 로그인 정보를 받아옴
        if (loginDTO != null) {
            navi_name.setText(loginDTO.getMember_nickname() + "님 반갑습니다!");
            //navi_Img.setImageResource(); -- 펫사진 DB에서 가져오기

        } else {
            navi_name.setText("로그인 정보가 없습니다");
        }

        // back
        navi_back = findViewById(R.id.navi_back);
        navi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        if (loginDTO != null) { // 로그인정보가 있다면 로그아웃 버튼 띄워주고 클릭 시 로그아웃
            navi_login = findViewById(R.id.navi_login);
            navi_login.setText("로그아웃");
            navi_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 로그아웃처리 (로그인정보 삭제 후 홈화면)
                    AlertDialog.Builder builder = new AlertDialog.Builder(NaviActivity.this);
                    builder.setTitle("로그아웃");
                    builder.setMessage("정말 로그아웃 하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(NaviActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            kakao = loginDTO.getMember_kakao();
                            naver = loginDTO.getMember_naver();

                            SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor autoLoginEdit = auto.edit();
                            autoLoginEdit.clear();
                            autoLoginEdit.commit();

                            // 스레드로 돌려야 한다. 안 그러면 로그아웃 처리가 안되고 false를 반환한다.
                            if(!naver.equals("")){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOAuthLoginInstance.logoutAndDeleteToken(Login_Activity.mContext);
                                        loginDTO = null;
                                        dtos = null;
                                    }
                                }).start();
                            }else{
                                loginDTO = null;
                                dtos = null;
                            }
                            if(kakao.equals("")){
                                loginDTO = null;
                                dtos = null;
                            }else{

                                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                                    @Override
                                    public void onCompleteLogout() {
                                        loginDTO = null;
                                        dtos = null;
                                    }
                                });

                            }


                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        } else { // 로그인 정보가 없다면 로그인 버튼 띄워주고 클릭 시 로그인창 이동
            navi_login = findViewById(R.id.navi_login);
            navi_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Login_Activity.class);
                    startActivityForResult(intent,1000);
                }
            });
        }

        if (loginDTO != null) { // 로그인정보가 있다면 마이페이지로 이동
            navi_mypage = findViewById(R.id.navi_mypage);
            navi_mypage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_Mypage.class);
                    startActivity(intent);

                }

            });
        } else { // 로그인 정보가 없다면 로그인 후 이용 알림창 띄우기
            navi_mypage = findViewById(R.id.navi_mypage);
            navi_mypage.setVisibility(View.GONE);
//            navi_mypage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(NaviActivity.this);
//                    builder.setTitle("로그인 정보가 없습니다");
//                    builder.setMessage("해당 페이지는 로그인하여 사용할 수 있습니다.\n로그인 창으로 이동하시겠습니까?");
//                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(NaviActivity.this, Login_Activity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                }
//            });

        }
//        // 알림설정
//        navi_alert = findViewById(R.id.navi_alert);
//        navi_alert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NaviActivity.this, Alert_Setting.class);
//                startActivity(intent);
//
//            }
//
//        });

        // 공지사항
        navi_notice = findViewById(R.id.navi_notice);
        if (loginDTO == null) {
            navi_notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_Notice.class);
                    startActivity(intent);
                }
            });
        } else if (loginDTO.getMember_admin().equals("N") || loginDTO == null) {
            navi_notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_Notice.class);
                    startActivity(intent);
                }
            });
        } else if (loginDTO.getMember_admin().equals("Y")) {
            navi_notice.setText("공지사항");
            navi_notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_Notice_admin.class);
                    startActivity(intent);
                }
            });
        }


        // FAQ
        navi_faq = findViewById(R.id.navi_faq);
        if (loginDTO == null) {
            navi_faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_FAQ.class);
                    startActivity(intent);
                }
            });
        } else if (loginDTO.getMember_admin().equals("N")) {
            navi_faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_FAQ.class);
                    startActivity(intent);
                }
            });
        } else if (loginDTO.getMember_admin().equals("Y")) {
            navi_faq.setText("FAQ");
            navi_faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_FAQ_admin.class);
                    startActivity(intent);
                }
            });
        }

        // 이벤트
        navi_event = findViewById(R.id.navi_event);
        navi_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NaviActivity.this, Event_Img.class);
                startActivity(intent);
            }
        });
//        if(loginDTO == null){
//            navi_event.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(NaviActivity.this, Option_Event.class);
//                    startActivity(intent);
//                }
//            });
//        }else if(loginDTO.getMember_admin().equals("N")){
//            navi_event.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(NaviActivity.this, Option_Event.class);
//                    startActivity(intent);
//                }
//            });
//        }else if(loginDTO.getMember_admin().equals("Y")){
//            navi_event.setText("이벤트");
//            navi_event.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(NaviActivity.this, Option_Event_admin.class);
//                    startActivity(intent);
//                }
//            });
//        }

        // 1:1 문의

        navi_inquiry = findViewById(R.id.navi_inquiry);
        // 관리자면 1:1 목록, 사용자면 1:1 문의
        if(loginDTO == null){
            navi_inquiry.setVisibility(View.GONE);

//            navi_inquiry.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(NaviActivity.this);
//                    builder.setTitle("로그인 정보가 없습니다");
//                    builder.setMessage("해당 페이지는 로그인하여 사용할 수 있습니다.\n로그인 창으로 이동하시겠습니까?");
//                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(NaviActivity.this, Login_Activity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                }
//            });
        }else if(loginDTO.getMember_admin().equals("N")){
            navi_inquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_inquiry.class);
                    startActivity(intent);
                }
            });
        }else if(loginDTO.getMember_admin().equals("Y")){
            navi_inquiry.setText("1:1문의");
            navi_inquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NaviActivity.this, Option_inquiry_admin.class);
                    startActivity(intent);
                }
            });
        }

        navi_cu_center = findViewById(R.id.navi_cu_center);
        navi_cu_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:0623627797"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            finish();
        }
    }

}