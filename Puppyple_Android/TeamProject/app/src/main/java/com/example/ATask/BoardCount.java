package com.example.ATask;

import static com.example.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class BoardCount extends AsyncTask<Void,Void,String> {
    int id;

    public BoardCount(int id){
        this.id = id;
    }
    String state = "";
    //반드시 선언해야 할 것들: 무조건 해야함 복,붙 하면 됨. copy & paste
    HttpClient httpClient; // 클라이언트 객체
    HttpPost httpPost;  //클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답받는 부분
    HttpEntity httpEntity; // 응답내용

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));
            builder.addTextBody("id", String.valueOf(id), ContentType.create("Multipart/related", "UTF-8"));
            //전송
            //전송 url : 우리가 수정해줘야하는 부분 anJoin 부분
            String postURL = ipConfig + "/bteam/and_update_boardCount";
            //그래도 사용 복, 붙
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); //보내고 응답 받는 부분
            httpEntity = httpResponse.getEntity(); //응답 내용을 저장
            inputStream = httpEntity.getContent(); //응답내용을 inputStream에 보냄

            //응답 처리: 문자열 형태
            BufferedReader bufferedReader = new
                    BufferedReader((new InputStreamReader(inputStream, "UTF-8")));

            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            state = stringBuilder.toString();

            inputStream.close();

        } catch (Exception e) {

            e.getMessage();

        } finally {
            if (httpEntity != null) {
                httpEntity = null;
            }
            if (httpResponse != null) {
                httpResponse = null;
            }
            if (httpPost != null) {
                httpPost = null;
            }
            if (httpClient != null) {
                httpClient = null;
            }
        }

        return state;

    }
}
