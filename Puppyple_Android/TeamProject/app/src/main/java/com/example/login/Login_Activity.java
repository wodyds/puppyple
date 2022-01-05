package com.example.login;

import static com.example.Common.CommonMethod.loginDTO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.IdCheck;
import com.example.ATask.LoginSelect;
import com.example.DTO.MemberDTO;
import com.example.MainActivity;
import com.example.PUPPYPLE.R;
import com.example.join.Join_Activity;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Login_Activity extends AppCompatActivity {

    private static final String TAG = "main:Login_Activity";
    private static String OAUTH_CLIENT_ID = "J6_1iReuEAh2mHp0TiCo";
    private static String OAUTH_CLIENT_SECRET = "2xzY4AE2dp";
    private static String OAUTH_CLIENT_NAME = "PUPPYPLE";
    public static OAuthLogin mOAuthLoginInstance;
    public static Context mContext;
    public static String accessToken = "";
    long expiresAt;
    ImageView exit;
    Date date;
    TextView btn_Id_Search;
    TextView btn_Pw_Search;
    //Button btn_Naver;
    OAuthLoginButton btn_Naver;
    TextView btn_Kakao;

    private SessionCallback callback;

    Button btnLogin;
    TextView btnJoin;
    static CheckBox auto_check;
    EditText etId;
    EditText etPw;
    MemberDTO dtos;
    String state,member_id,member_pw;
    int idChk;

    public static MenuItem auto_check() {

        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        date = new Date();
        idChk = 0;
        etId = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPASSWD);
        auto_check = findViewById(R.id.auto_check);

        if(KakaoSDK.getAdapter() == null){
            KakaoSDK.init(new GlobalApplication.KaKaoSDKAdapter());
        }



        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        //뒤로가기버튼
        exit = findViewById(R.id.login_back);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        // 자동로그인 처리
        SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        member_id = auto.getString("member_id", null);
        member_pw = auto.getString("member_pw", null);

        if(member_id != null && member_pw != null){
            LoginSelect loginSelect = new LoginSelect(member_id, member_pw);
            try {
                loginSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(loginDTO != null){
                Log.d(TAG, "onClick: " + loginDTO.getMember_id());
                // 서버에서 받은 데이터를 확인해서 로그인 정보가 있으면 loginDTO에
                // 넣고 메인을 부른다 그리고 로그인 화면을 제거한다
                Intent intent = new Intent(Login_Activity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }else {
                Toast.makeText(Login_Activity.this,
                        "아이디나 비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
            }

        }else{
            //로그인
            btnLogin = findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(auto_check.isChecked()){
                        SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLoginEdit = auto.edit();
                        autoLoginEdit.putString("member_id", etId.getText().toString());
                        autoLoginEdit.putString("member_pw", etPw.getText().toString());
                        autoLoginEdit.commit();

                    }




                    //에디트텍스트에 있는 데이터를 가져온다
                    if(etId.getText().toString().length() > 0 &&
                            etPw.getText().toString().length() >0 ) {

                        String member_id = etId.getText().toString();
                        String member_pw = etPw.getText().toString();

                        //서버로 데이터를 보낸다
                        LoginSelect loginSelect = new LoginSelect(member_id, member_pw);
                        try {
                            loginSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(Login_Activity.this,
                                "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        etId.requestFocus();
                        return;
                    }

                    if(loginDTO != null){
                        Log.d(TAG, "onClick: " + loginDTO.getMember_id());
                        // 서버에서 받은 데이터를 확인해서 로그인 정보가 있으면 loginDTO에
                        // 넣고 메인을 부른다 그리고 로그인 화면을 제거한다
//                        Intent intent = new Intent(Login_Activity.this,
//                                MainActivity.class);
//                        startActivity(intent);

                        finish();
                    }else {
                        Toast.makeText(Login_Activity.this,
                                "아이디나 비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }




        // 가입
        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join_Activity.class);
                startActivity(intent);
            }
        });

        // 아이디 찾기
        btn_Id_Search = findViewById(R.id.btn_Id_Search);
        btn_Id_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Idfind.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기
        btn_Pw_Search = findViewById(R.id.btn_Pw_Search);
        btn_Pw_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Pwfind.class);
                startActivity(intent);
            }
        });
        // 네이버 로그인
        btn_Naver = findViewById(R.id.btn_Naver);
        mContext = this;
        initData();


        // 카카오 로그인
        btn_Kakao = findViewById(R.id.btn_Kakao);
        btn_Kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getCurrentSession().open(AuthType.KAKAO_TALK, Login_Activity.this);

            }
        });


    }

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext,OAUTH_CLIENT_ID,OAUTH_CLIENT_SECRET,OAUTH_CLIENT_NAME);
        btn_Naver.setOAuthLoginHandler(mOAuthLoginHandler);

    }
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if(success){
                accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                Log.d(TAG, "토큰값 : "+accessToken);
                Log.d(TAG, "리프레시토큰 : "+refreshToken);
                Log.d(TAG, "expiresAt : "+expiresAt);
                Log.d(TAG, "tokenType : "+tokenType);

                new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String header = "Bearer " + accessToken;
                            Map<String, String> requestHeaders = new HashMap<>();
                            requestHeaders.put("Authorization",header);
                            String apiURL = "https://openapi.naver.com/v1/nid/me"; // 엑세스 토큰으로 유저정보를 받아올 주소
                            String responseBody = get(apiURL,requestHeaders);

                            NaverUserInfo(responseBody);
                        }
                    }).start();

                redirectSignupActivity();


            } else{
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "error code : "+errorCode+", errorDesc : "+errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };



    protected void redirectSignupActivity(){
        if(loginDTO != null) {
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
//    // 네이버 로그인 후처리 시작 //
    private void NaverUserInfo(String msg){
        JSONParser jsonParser = new JSONParser();

        try {
            //org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(msg);
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(msg);
            String resultcode = jsonObject.get("resultcode").toString();
            String message = jsonObject.get("message").toString();
            Log.d(TAG, "resultcode : "+resultcode);
            if(resultcode.equals("00")){
                if(message.equals("success")){
                    org.json.simple.JSONObject naverJson = (org.json.simple.JSONObject) jsonObject.get("response");

                    String id = naverJson.get("id").toString();
                    String nickName = naverJson.get("nickname").toString();
                    String profileImage = naverJson.get("profile_image").toString();
                    String email = naverJson.get("email").toString();
                    String name = naverJson.get("email").toString();

                    Log.d("id 확인 ",id);
                    Log.d("nickName 확인 ",nickName);
                    Log.d("profileImage 확인 ",profileImage);
                    Log.d("email 확인 ",email);
                    Log.d("name 확인 ",name);

                    dtos = new MemberDTO(id,email,email,nickName,email);

                    // 서버내에 네이버 정보에 같은 아이디가있다면 (이미 네이버로 가입한적이 있다면) 로그인처리함

                    String member_id = dtos.getMember_id();
                    IdCheck idCheck = new IdCheck(member_id);
                    try {
                        state = idCheck.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (state.equals("0")) {// 서버내에 네이버 정보에 같은 아이디가 없음(가입한적 없음) >> 가입처리위해 추가정보 입력창으로
                        Intent intent = new Intent(Login_Activity.this, NaverPlus.class);
                        intent.putExtra("dtos",dtos);
                        startActivity(intent);
                        finish();
                    }else{ // 비밀번호 가져와서 로그인시도
                        String member_pw = dtos.getMember_pw();
                        LoginSelect loginSelect = new LoginSelect(member_id,member_pw);
                        try {
                            loginSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplicationContext(),"로그인 오류가 발생했습니다.",Toast.LENGTH_SHORT).show();
                    }
                }, 0);


            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static String get(String apiUri,Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUri);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String,String> header : requestHeaders.entrySet()){
                con.setRequestProperty(header.getKey(),header.getValue());
            }
            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                return readBody(con.getInputStream());
            }else{
                return readBody(con.getErrorStream());
            }

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally{
            con.disconnect();
        }

    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }

    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);
        try(BufferedReader lineReader = new BufferedReader(streamReader)){
            StringBuilder responseBody = new StringBuilder();
            String line;
            while((line = lineReader.readLine()) != null){
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }


    // 네이버 로그인 후처리 끝 //

    // 카카오 로그인 후처리

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            return;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class SessionCallback implements ISessionCallback {

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();
            redirectSignupActivity();

        }



        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe() {
            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                        }

                        @Override
                        public void onSuccess(MeV2Response result) {
                            Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                            String kakao_pw = String.valueOf(result.getId());
                            UserAccount kakaoAccount = result.getKakaoAccount();
                            Log.d(TAG, "onSuccess: " + kakaoAccount.getPhoneNumber());
                            if (kakaoAccount != null) {

                                // 이메일
                                String email = kakaoAccount.getEmail();

                                if (email != null) {
                                    Log.i("KAKAO_API", "email: " + email);

                                } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 이메일 획득 가능
                                    // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                                } else {
                                    // 이메일 획득 불가
                                }

                                // 프로필
                                Profile profile = kakaoAccount.getProfile();

                                if (profile != null) {
                                    Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                    Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                    Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());


                                } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 프로필 정보 획득 가능

                                } else {
                                    // 프로필 획득 불가
                                }
                                // 카카오 정보를 DTO에 담음
                                Log.d(TAG, "닉네임: "+profile.getNickname());
                                dtos = new MemberDTO(kakaoAccount.getEmail(),kakao_pw,kakaoAccount.getEmail(),profile.getNickname(),kakaoAccount.getEmail(),"N");

                                // 서버내에 카카오 정보에 같은 아이디가있다면 (이미 카카오로 가입한적이 있다면) 로그인처리함
                                String member_id = dtos.getMember_id();
                                IdCheck idCheck = new IdCheck(member_id);
                                try {
                                    state = idCheck.execute().get().trim();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (state.equals("0")) {// 서버내에 카카오 정보에 같은 아이디가 없음(가입한적 없음) >> 가입처리위해 추가정보 입력창으로
                                    Intent intent = new Intent(Login_Activity.this, KakaoPlus.class);
                                    intent.putExtra("dtos",dtos);
                                    startActivity(intent);
                                    finish();
                                }else{ // 비밀번호 가져와서 로그인시도
                                    String member_pw = dtos.getMember_pw();

                                    LoginSelect loginSelect = new LoginSelect(member_id,member_pw);
                                    try {
                                        loginSelect.execute().get();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                    });
        }

        public void redirectSignupActivity(){
            if(loginDTO != null){
                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
