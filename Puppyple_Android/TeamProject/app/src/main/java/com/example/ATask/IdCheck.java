package com.example.ATask;

import static com.example.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

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

public class IdCheck extends AsyncTask<Void, Void, String> {

    //무조건 생성자를 만들어서 데이터를 받는다.
    String member_id;

    public IdCheck(String member_id) {
        this.member_id = member_id;
    }

    String state = "";
    //반드시 선언해야할것들: 무조건해야함. 복, 붙하면됨

    HttpClient httpClient; // 클라이언트 객체
    HttpPost httpPost;  //클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답받는 부분
    HttpEntity httpEntity; // 응답내용

    //doInBackground 하기 전에 설정 및 초기화: 여기서는 사용안함
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            //MultipartEntityBuilder 생성: 무조건 해야함 복, 붙
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //여기가 우리가 수정해야 하는 부분: 서버로 보내는 데이터
            //builder에 문자열 데이터 추가하기

            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            Log.d(String.valueOf(this), "아이디중복: " + member_id);

            //전송
            //전송 url: 우리가 수정해줘야하는 부분 anJoin 부분
            String postURL = ipConfig + "/bteam/and_idChk";
            //그래도 사용 복, 붙
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); //보내고 응답 받는 부분
            httpEntity = httpResponse.getEntity(); //응답 내용을 저장
            inputStream = httpEntity.getContent(); //응답내용을 inputStream에 보냄

            //응답 처리: 문자열 형태
            BufferedReader bufferedReader= new
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

        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }
        }

        return state;

    }
    //doInBackground 끝난후에 실행하는 부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
