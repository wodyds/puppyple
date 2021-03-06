package com.example.option;

import static com.example.Common.CommonMethod.ipConfig;
import static com.example.Common.CommonMethod.loginDTO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.BoardDelete;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Faq_Detail extends AppCompatActivity {

    ImageView option_faq_back;
    TextView Notice_title_tv,Notice_writer_tv,Notice_content_tv, Notice_flag_tv, Notice_writedate_tv, Notice_readCnt_tv;
    Button btn_modify,btn_delete;
    String id = "",state = "",member_id = "",admin = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_detail);
        BoardDTO dto = (BoardDTO) getIntent().getSerializableExtra("dto");
        id = String.valueOf(dto.getId());
        member_id = getIntent().getExtras().getString("member_id");

        Notice_flag_tv = findViewById(R.id.Notice_flag_tv);
        Notice_writedate_tv = findViewById(R.id.Notice_writedate_tv);
        Notice_readCnt_tv = findViewById(R.id.Notice_readCnt_tv);
        Notice_title_tv = findViewById(R.id.Notice_title_tv);
        Notice_writer_tv = findViewById(R.id.Notice_writer_tv);
        Notice_content_tv = findViewById(R.id.Notice_content_tv);
        btn_modify = findViewById(R.id.btn_modify);
        btn_delete = findViewById(R.id.btn_delete);


        // ???????????? ?????? ?????? ?????????
        option_faq_back = findViewById(R.id.option_faq_back);
        option_faq_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //??????????????? ?????????
            }
        });


        // ?????????????????? ????????? ?????????, ????????? ?????????
        if(loginDTO != null){
            member_id = loginDTO.getMember_id();
            admin = loginDTO.getMember_admin();
        }else{
            member_id = "";
            admin = "";
        }

        // ???????????? ?????? ???????????? ????????? ????????????
        if(admin.equals("Y")){
            btn_modify.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
        }else{
            btn_modify.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
        }

        // ????????? ??????
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Faq_Detail.this, Faq_Modify.class);
                intent.putExtra("dto",dto);
                startActivity(intent);
                finish();
            }
        });


        // ????????? ??????
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Faq_Detail.this);
                builder.setTitle("????????? ??????");
                builder.setMessage("???????????? ??????????????? ????????? ??? ????????????.\n?????? ?????????????????????????");
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BoardDelete boardDelete = new BoardDelete(id);
                        try {
                            state = boardDelete.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (state.equals("1")) {
                            Toast.makeText(Faq_Detail.this,
                                    "?????? ??????", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Faq_Detail.this,
                                    "????????? ?????? ??????????????????. ?????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        InitData();
    }

    private void InitData(){
        LoadBoard loadBoard = new LoadBoard();
        loadBoard.execute(id);
    }



    class LoadBoard extends AsyncTask<String,Void,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = null;
                jsonArray = new JSONArray(s);

                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // DB??? ??????????????? ????????? ????????? ??? ?????? TextView??? ????????? ??????
                    String board_title = jsonObject.optString("board_title");
                    String writer = jsonObject.optString("member_nickname");
                    String board_content = jsonObject.optString("board_content");
                    String board_writedate = jsonObject.optString("board_writedate");
                    String board_readCnt = jsonObject.optString("board_readcnt");
                    String flag = jsonObject.optString("flag");

                    if (flag.equals("B")) {
                        flag = "???????????????";
                    } else if (flag.equals("I")) {
                        flag = "????????????";
                    } else if (flag.equals("G")) {
                        flag = "?????????";
                    } else if (flag.equals("T")) {
                        flag = "????????????";
                    } else if (flag.equals("N")) {
                        flag = "????????????";
                    } else if (flag.equals("F")) {
                        flag = "FAQ";
                    } else if (flag.equals("E")) {
                        flag = "?????????";
                    } else if (flag.equals("M")) {
                        flag = "1:1 ??????";
                    }

                    Notice_title_tv.setText(board_title);
                    Notice_writer_tv.setText(writer);
                    Notice_content_tv.setText(board_content);
                    Notice_flag_tv.setText(flag);
                    Notice_writedate_tv.setText(board_writedate);
                    Notice_readCnt_tv.setText(board_readCnt);

                    if(member_id.equals(writer)||member_id.equals("admin")){
                        btn_modify.setVisibility(View.VISIBLE);
                        btn_delete.setVisibility(View.VISIBLE);
                    }else{
                        btn_modify.setVisibility(View.GONE);
                        btn_delete.setVisibility(View.GONE);
                    }

                }

            }catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            String id = strings[0];

            String postURL = ipConfig + "/bteam/and_Board_detail";

            URL url;
            String response = "";

            try {
                url = new URL(postURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("id",id);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int responseCode = conn.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while((line=br.readLine())!=null){
                        response += line;
                    }
                }else{
                    response = "";
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }


    }
}
