package com.example.ATask;

import static com.example.Common.CommonMethod.ipConfig;
import static com.example.Common.CommonMethod.loginDTO;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.DTO.PetDTO;
import com.example.petInfo.PetListViewAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class PetListViewDelete extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "main:PetListViewDelete";


    String member_id;
    String pet_id;

    PetListViewAdapter adapter;
    ArrayList<PetDTO> dtos;

    public PetListViewDelete(String member_id, String pet_id, ArrayList<PetDTO> dtos, PetListViewAdapter adapter) {
        this.member_id = member_id;
        this.pet_id = pet_id;
        this.dtos = dtos;
        this.adapter = adapter;
    }

    //반드시 선언해야할것들: 무조건해야함. 복, 붙하면됨
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
        builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
        builder.addTextBody("pet_id", pet_id, ContentType.create("Multipart/related", "UTF-8"));

        //전송
        //전송 url: 우리가 수정해줘야하는 부분 anLogin부분
        String postURL = ipConfig + "/bteam/and_petDelete";
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

            Log.d(TAG, "doInBackground: " + loginDTO.getMember_id());

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

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        //데이터가 새로 삽입되어서 화면 갱신을 해준다.
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }

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

    public PetDTO readMessage(JsonReader reader) throws IOException {
        int pet_id = 0;
        String member_id = "";
        String pet_name = "";
        String pet_breed = "";
        String pet_age = "";
        String pet_gender = "";
        String pet_nuetering = "";
        String pet_weight = "";
        String pet_filename = "";
        String pet_filepath = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("pet_id")) {
                pet_id = Integer.parseInt(reader.nextString());
            }else if (readStr.equals("member_id")) {
                member_id = reader.nextString();
            }else if (readStr.equals("pet_name")) {
                pet_name = reader.nextString();
            }else if (readStr.equals("pet_breed")) {
                pet_breed = reader.nextString();
            }else if (readStr.equals("pet_age")) {
                pet_age = reader.nextString();
            }else if (readStr.equals("pet_gender")) {
                pet_gender = reader.nextString();
            }else if (readStr.equals("pet_nuetering")) {
                pet_nuetering = reader.nextString();
            }else if (readStr.equals("pet_weight")) {
                pet_weight = reader.nextString();
            } else if (readStr.equals("pet_filename")) {
                pet_filename = ipConfig + "/bteam/resources/upload/" + reader.nextString();
            } else if (readStr.equals("pet_filepath")) {
                pet_filepath = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new PetDTO(pet_id, member_id, pet_name, pet_breed, pet_age,pet_gender, pet_nuetering, pet_weight, pet_filename, pet_filepath);
    }


}
