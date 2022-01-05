package com.example.ATask;

import static com.example.Common.CommonMethod.ipConfig;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.DTO.BoardDTO;
import com.example.community.BoardListViewAdapter;
import com.example.option.NoticeListViewAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class NoticeListSelect extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "main:PetListSelect";

    //서버와 연결을 위해서는 AsyncTask를 만들어야하는데
    //그럼 뭐를 받아야하나에 대해서 생각 필요
    //생성자를 만들어서 데이터를 받는다.

    Context context;
    //필요없을수도 있지만, 일단은 lv로 해놓는다.
    //필요하면 받을 예정

    ArrayList<BoardDTO> dtos;
    NoticeListViewAdapter adapter;
    String member_id;


    //11/22/2021
    public NoticeListSelect(String member_id) {
        this.member_id = member_id;
    }

    public NoticeListSelect(ArrayList<BoardDTO> dtos/*, NoticeListViewAdapter adapter*/) {
        this.dtos = dtos;
//        this.adapter = adapter;
    }

    public NoticeListSelect(ArrayList<BoardDTO> dtos, NoticeListViewAdapter adapter) {
        this.dtos = dtos;
        this.adapter = adapter;
    }


    //반드시 선언해야 할 것들: 무조건 해야함 복,붙 하면 됨. copy & paste
    HttpClient httpClient; // 클라이언트 객체
    HttpPost httpPost;  //클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답받는 부분
    HttpEntity httpEntity; // 응답내용

    //doInBackground 하기 전에 설정 및 초기화: 여기서는 사용안함
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        if(dtos.size() > 0){
//            dtos.clear();
//        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //MultipartEntityBuilder 생성: 무조건 해야함 복, 붙
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //여기가 우리가 수정해야 하는 부분: 서버로 보내는 데이터
            //builder에 문자열 데이터 추가하기

            //전송
            //전송 url: 우리가 수정해줘야하는 부분 anLogin부분
            String postURL = ipConfig + "/bteam/and_BoardList_Notice";
            //그래도 사용 복, 붙
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); //보내고 응답 받는 부분
            httpEntity = httpResponse.getEntity(); //응답 내용을 저장
            inputStream = httpEntity.getContent(); //응답내용을 inputStream에 보냄

            //데이터가 ArrayList<DTO> 형태일때
            //아래와 같은 방식으로 받아야함

            readJsonStream(inputStream);

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

        return null;
    }//doInBackgroud()

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

//        if(adapter != null){
//            adapter.notifyDataSetChanged();
//        }


    }


    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                dtos.add(readMessage(reader));
            }
            reader.endArray();

            Log.d(TAG, "readJsonStream: size => " + dtos.size());

        }catch(Exception e){
            e.getMessage();
        } finally {
            reader.close();
        }
    }

    public BoardDTO readMessage(JsonReader reader) throws IOException {
        int id = 0;
        String flag = "";
        String member_id = "";
        String board_title = "";
        String board_content = "";
        String board_writedate = "";
        int board_readcnt = 0;
        String board_filename = "";
        String board_filepath = "";
        String trade_category = "";
        String trade_price = "";
        String member_nickname = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("id")) {
                id = Integer.parseInt(reader.nextString());
            }else if (readStr.equals("board_readcnt")) {
                board_readcnt = Integer.parseInt(reader.nextString());
            }else if (readStr.equals("flag")) {
                flag = reader.nextString();
            }else if (readStr.equals("member_id")) {
                member_id = reader.nextString();
            }else if (readStr.equals("board_title")) {
                board_title = reader.nextString();
            }else if (readStr.equals("board_content")) {
                board_content = reader.nextString();
            }else if (readStr.equals("board_writedate")) {
                board_writedate = reader.nextString();
            }else if (readStr.equals("board_filename")) {
                board_filename = ipConfig + "/bteam/resources/" + reader.nextString();
            } else if (readStr.equals("board_filepath")) {
                board_filepath = reader.nextString();
            } else if (readStr.equals("trade_category")) {
                trade_category = reader.nextString();
            } else if (readStr.equals("trade_price")){
                trade_price = reader.nextString();
            } else if (readStr.equals("member_nickname")){
                member_nickname = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new BoardDTO(id, board_readcnt,flag, member_id, board_title, board_content,board_writedate,  board_filename, board_filepath, trade_category,trade_price,member_nickname);
    }
}
