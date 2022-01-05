package com.example;

import static com.example.Common.CommonMethod.dtos;
import static com.example.Common.CommonMethod.loginDTO;
import static com.example.login.Login_Activity.mOAuthLoginInstance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.PUPPYPLE.R;
import com.example.login.Login_Activity;
import com.example.option.Option_FAQ;
import com.example.option.Option_FAQ_admin;
import com.example.option.Option_Location;
import com.example.option.Option_Notice;
import com.example.option.Option_Notice_admin;
import com.example.option.Option_Personal;
import com.example.option.Option_Service;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class Fragment5 extends Fragment {
    private static final String TAG = "main:Fragment5";
    TextView login;
    MainActivity mActivity;
    Context mContext;
    TextView Mypage;
    TextView Alert;
    TextView Notice;
    TextView FAQ;
    TextView version;
    TextView service;
    TextView personal;
    TextView location;
    String kakao="",naver="";
    private Login_Activity.SessionCallback callback;


    // 메인액티비티 위에 올리기
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) getActivity();
        mContext = context;

    }

    // 메인액티비티에서 내리기
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.option_activity_main, container, false);


        if(loginDTO != null){ // 로그인정보가 있다면 로그아웃 버튼 띄워주고 클릭 시 로그아웃
            login = rootView.findViewById(R.id.option_Login);
            login.setText("로그아웃");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 로그아웃처리 (로그인정보 삭제 후 홈화면)
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("로그아웃");
                    builder.setMessage("정말 로그아웃 하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Log.d(TAG, "onClick: "+loginDTO.getMember_naver());
                            Log.d(TAG, "onClick: "+loginDTO.getMember_kakao());
                            Log.d(TAG, "onClick: "+loginDTO.getMember_id());

                            kakao = loginDTO.getMember_kakao();
                            naver = loginDTO.getMember_naver();

                            SharedPreferences auto = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
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
                                Log.d(TAG, "kakao: "+kakao);
                                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                                    @Override
                                    public void onCompleteLogout() {
                                        loginDTO = null;
                                        dtos = null;
                                    }
                                });

                            }


                            startActivityForResult(intent,10);
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
        }else { // 로그인 정보가 없다면 로그인 버튼 띄워주고 클릭 시 로그인창 이동
            login = rootView.findViewById(R.id.option_Login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), Login_Activity.class);
                    startActivityForResult(intent,1000);
                }
            });
        }

//        if(loginDTO != null) { // 로그인정보가 있다면 마이페이지로 이동
//            Mypage = rootView.findViewById(R.id.option_Mypage);
//            Mypage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity().getApplicationContext(), Option_Mypage.class);
//                    startActivity(intent);
//
//                }
//
//            });
//        }else { // 로그인 정보가 없다면 로그인 후 이용 알림창 띄우기
//            Mypage = rootView.findViewById(R.id.option_Mypage);
//            Mypage.setVisibility(View.GONE);
//
//            Mypage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle("로그인 정보가 없습니다");
//                    builder.setMessage("해당 페이지는 로그인하여 사용할 수 있습니다.\n로그인 창으로 이동하시겠습니까?");
//                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(getActivity().getApplicationContext(), Login_Activity.class);
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
//
//        }


//        Alert = rootView.findViewById(R.id.option_Alert);
//        Alert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), Alert_Setting.class);
//                startActivity(intent);
//            }
//        });

        // 공지사항
        Notice = rootView.findViewById(R.id.option_Notice);
        if(loginDTO == null){
            Notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Option_Notice.class);
                    startActivity(intent);
                }
            });
        }else if(loginDTO.getMember_admin().equals("N")||loginDTO == null){
            Notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Option_Notice.class);
                    startActivity(intent);
                }
            });
        }else if(loginDTO.getMember_admin().equals("Y")){
            Notice.setText("공지사항");
            Notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Option_Notice_admin.class);
                    startActivity(intent);
                }
            });
        }


        FAQ = rootView.findViewById(R.id.option_Faq);
        if(loginDTO == null){
            FAQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Option_FAQ.class);
                    startActivity(intent);
                }
            });
        }else if(loginDTO.getMember_admin().equals("N")){
            FAQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Option_FAQ.class);
                    startActivity(intent);
                }
            });
        }else if(loginDTO.getMember_admin().equals("Y")){
            FAQ.setText("FAQ");
            FAQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Option_FAQ_admin.class);
                    startActivity(intent);
                }
            });
        }


/*        version = rootView.findViewById(R.id.option_Version);
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "버전정보 1.0.0", Toast.LENGTH_SHORT).show();
            }
        });*/

        service = rootView.findViewById(R.id.option_Service);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Option_Service.class);
                startActivity(intent);
            }
        });

        personal = rootView.findViewById(R.id.option_Personal);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Option_Personal.class);
                startActivity(intent);
            }
        });

        location = rootView.findViewById(R.id.option_Location);
        location.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick (View v){
                    Intent intent = new Intent(getActivity().getApplicationContext(), Option_Location.class);
                    startActivity(intent);
                }

        });
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            mActivity.fragmentChange(1);
            MainActivity.bottomNavigationView.setSelectedItemId(R.id.tab1);
        }
    }
}
