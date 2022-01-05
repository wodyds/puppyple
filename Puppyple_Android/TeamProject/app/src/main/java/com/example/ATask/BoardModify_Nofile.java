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

public class BoardModify_Nofile extends AsyncTask<Void,Void,String> {
    int id;
    String flag, member_id, board_title, board_content, board_filepath;

    public BoardModify_Nofile(int id,String flag, String member_id, String board_title, String board_content) {
        this.id = id;
        this.flag = flag;
        this.member_id = member_id;
        this.board_title = board_title;
        this.board_content = board_content;
    }

    String state = "";
    //반드시 선언해야 할 것들: 무조건 해야함 복,붙 하면 됨. copy & paste
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
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            builder.addTextBody("id", String.valueOf(id),ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("flag", flag, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("board_title", board_title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("board_content", board_content, ContentType.create("Multipart/related", "UTF-8"));


            //전송
            //전송 url : 우리가 수정해줘야하는 부분 anJoin 부분
            String postURL = ipConfig + "/bteam/and_board_Modify_Nofile";
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
