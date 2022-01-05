package com.example;

import static com.example.Common.CommonMethod.loginDTO;
import static com.example.login.Login_Activity.mOAuthLoginInstance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.LoginSelect;
import com.example.ATask.PetListSelect;
import com.example.DTO.PetDTO;
import com.example.PUPPYPLE.R;
import com.example.login.Login_Activity;
import com.example.petInfo.PetListViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    //20211126
    private static final String TAG = "main: MainActivity";
    //20211126
    ArrayList<PetDTO> dtos;
    //20211126
    PetListViewAdapter adapter;
    ImageView toolbar_title;
    ImageButton menubar;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;
    String auto_member_id;
    String kakao="",naver="";
    static BottomNavigationView bottomNavigationView;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar_title = findViewById(R.id.toolbar_title);
        bottomNavigationView = findViewById(R.id.bottom_navi);

        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment1 = new Fragment1();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contain, fragment1).commit();
                bottomNavigationView.setSelectedItemId(R.id.tab1);
            }
        });

        //중요: dtos 넘겨줄때 반드시 생성해서 넘겨준다.
        dtos = new ArrayList<>();



        //11/26/2021 11:52 AM 주석처리
//        Log.d(TAG, "onCreate: member_id => " + loginDTO.getMember_id());

//        if (loginDTO != null) {
//            String member_id = loginDTO.getMember_id();
//            PetListSelect petListSelect = new PetListSelect(member_id, dtos, adapter);
//            try {
//                petListSelect.execute().get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (dtos.size() > 0) {
//
//                Log.d(TAG, "onCreate: " +  dtos.size());
//
//                CommonMethod.dtos = dtos;
//
//            }
//
//        }

        // 자동로그인 처리
        SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        auto_member_id = auto.getString("member_id", null);
        String member_pw = auto.getString("member_pw", null);

        if(auto_member_id != null && member_pw != null){
            LoginSelect loginSelect = new LoginSelect(auto_member_id, member_pw);
            try {
                loginSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }

        // 첫 실행 시 로딩화면?

        SharedPreferences loading = getSharedPreferences("loading", Activity.MODE_PRIVATE);
        SharedPreferences.Editor loadingEdit = loading.edit();

        boolean loadingBoolean = loading.getBoolean("isfirst",true);
        if(loadingBoolean){
            Intent intent = new Intent(this, Loading_activity.class);
            startActivity(intent);
            loadingEdit.putBoolean("isfirst",false);
            loadingEdit.commit();
        }


        // 뒤로가기 버튼 두번클릭시 앱 종료
        backPressCloseHandler = new BackPressCloseHandler(this);

        getHashKey();

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();

        //처음 화면을 초기화
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment1).commit();



//        if (loginDTO == null) {
//            Intent intent = new Intent(this, Loading_activity.class);
//            startActivity(intent);
//        }

        //toolbar 적용 : theme에 NoActionBar로 변경
//        toolbar = findViewById(R.id.toolbar);/p-
//        setSupportActionBar(toolbar);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.tab1:
                        fragment1 = new Fragment1();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment1).commit();
                        fragment1.introduce.setSelected(true);
                        break;
                    case R.id.tab2:
                        fragment2 = new Fragment2();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment2).commit();
                        break;
                    case R.id.tab3:
                        fragment3 = new Fragment3();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment3).commit();
                        break;
                    case R.id.tab4:
                        fragment4 = new Fragment4();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment4).commit();
                        break;
                    case R.id.tab5:
                        fragment5 = new Fragment5();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment5).commit();
                        break;
                }

                return true;
            }
        });

        // 메뉴바
         menubar = findViewById(R.id.menubar);
         menubar.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), NaviActivity.class);
                        startActivityForResult(intent,200);
                        overridePendingTransition(R.anim.leftin_activity, R.anim.not_move_activity);

            }

                });




    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed(); }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


    //20211126 그것에 대한 onNewIntent를 해줘서, 다시 받아온다.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String member_id = loginDTO.getMember_id();
        PetListSelect petListSelect = new PetListSelect(member_id, dtos, adapter);
        try {
            petListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //20211126
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult1: " + requestCode);

        if(requestCode == 1001){
            Log.d(TAG, "onActivityResult2: " + loginDTO.getMember_id());

            String member_id = loginDTO.getMember_id();
            PetListSelect petListSelect = new PetListSelect(member_id, dtos, adapter);
            try {
                petListSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(requestCode == 10){
            SharedPreferences loading = getSharedPreferences("loading", Activity.MODE_PRIVATE);
            SharedPreferences.Editor loadingEdit = loading.edit();
            loadingEdit.putBoolean("isfirst",false);
            loadingEdit.commit();
        }
        if(requestCode == 200){
            fragment1.onRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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

//        SharedPreferences loading = getSharedPreferences("loading", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor loadingEdit = loading.edit();
//        loadingEdit.clear();
//        loadingEdit.commit();

    }


    public void fragmentChange(int index){
        if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.contain, fragment1).commit();
            fragment1.introduce.setSelected(true);
        }else if(index == 2){
            getSupportFragmentManager().beginTransaction().replace(R.id.contain, fragment2).commit();
        }else if(index == 3){
            getSupportFragmentManager().beginTransaction().replace(R.id.contain, fragment3).commit();
        }else if(index == 4){
            getSupportFragmentManager().beginTransaction().replace(R.id.contain, fragment4).commit();
        }else if(index == 5){
            getSupportFragmentManager().beginTransaction().replace(R.id.contain, fragment5).commit();
        }
    }


}
