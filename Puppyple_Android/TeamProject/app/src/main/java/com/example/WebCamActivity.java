package com.example;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PUPPYPLE.R;

public class WebCamActivity extends AppCompatActivity {

    private static final String TAG = "WebCamActivity";

    ImageView ivWebView_iot_cam_back;
    TextView tv_webView;
    WebView webView_iot_cam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_iot_cam);

        //1. id로 서로 연결시켜준다
        ivWebView_iot_cam_back = findViewById(R.id.ivWebView_iot_cam_back);
        tv_webView = findViewById(R.id.tv_webView);
        webView_iot_cam = findViewById(R.id.webView_iot_cam);

        //2.자바스크립트가 사용이 가능도록 아래 코드 추가 필요
        webView_iot_cam.getSettings().setJavaScriptEnabled(true);

        //3. 보여줄 홈페이지 URL을 넣은뒤 웹뷰를 실행하는 코드를 작성
        webView_iot_cam.loadUrl("http://211.223.59.27:8000/");

        //4. 웹뷰에서 크롬이 실행이 가능하도록 아래 코드 추가
        webView_iot_cam.setWebChromeClient(new WebChromeClient());

        webView_iot_cam.setWebViewClient(new WebViewClientClass());

        //7. 뒤로가기
        ivWebView_iot_cam_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });


    }//onCreate();;

    //5. 웹뷰에서 홈페이지 띄웠을때 새창이 아닌 기존창에서 실행이 되도록 아래 코드 추가
    private class WebViewClientClass extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.d(TAG, "shouldOverrideUrlLoading: " + "CheckURL");
            view.loadUrl(url);
            return true;
        }
    }

    //6.뒤로가기 이벤트
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//뒤로가기 버튼 이벤트
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView_iot_cam.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            webView_iot_cam.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}
