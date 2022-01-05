package com.example.community;

import static com.example.Common.CommonMethod.ipConfig;
import static com.example.Common.CommonMethod.loginDTO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.ATask.BoardCommentListSelect;
import com.example.ATask.BoardComment_Add;
import com.example.ATask.BoardComment_Delete;
import com.example.ATask.BoardCount;
import com.example.ATask.BoardDelete;
import com.example.DTO.BoardCommentDTO;
import com.example.DTO.BoardDTO;
import com.example.Fragment1;
import com.example.MainActivity;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Community_Detail extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "main:Community_Detail";
    //LinearLayout parentLayout;
    MainActivity mActivity;
    TextView title_tv,writer_tv,content_tv,flag_tv,writedate_tv,readCnt_tv;
    ImageView content_iv, post_back, btn_refresh,toolbar_title;
    ListView comment_layout;
    EditText comment_et;
    Button reg_button,btn_modify,btn_delete;
    String id = "",state = "",member_id = "",board_filename="", pid="",writers="",old_filename="";
    SwipeRefreshLayout srl_main;
    ArrayList<BoardCommentDTO> dtos;
    BoardCommentListViewAdapter adapter;
    ScrollView scrollView;
    Fragment1 fragment1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        BoardDTO dto = (BoardDTO) getIntent().getSerializableExtra("dto");
        id = String.valueOf(dto.getId());
        // 로그인한 사람의 id
        member_id = getIntent().getExtras().getString("member_id");
        // 글 쓴 사람의 id
        writers = dto.getMember_id();
        pid = id;

        toolbar_title = findViewById(R.id.toolbar_title);
        //parentLayout = findViewById(R.id.parentLayout);
        scrollView = findViewById(R.id.scrollView);
        btn_modify = findViewById(R.id.btn_modify);
        btn_delete = findViewById(R.id.btn_delete);
        title_tv = findViewById(R.id.title_tv);
        writer_tv = findViewById(R.id.writer_tv);
        content_tv = findViewById(R.id.content_tv);
        content_iv = findViewById(R.id.content_iv);
        flag_tv = findViewById(R.id.flag_tv);
        writedate_tv = findViewById(R.id.writedate_tv);
        readCnt_tv = findViewById(R.id.readCnt_tv);
        comment_layout = findViewById(R.id.comment_layout);
        comment_et = findViewById(R.id.comment_et);
        btn_refresh = findViewById(R.id.btn_refresh);
        reg_button = findViewById(R.id.reg_button);
        post_back = findViewById(R.id.post_back);
        refresh();

        // 조회수 증가 처리
        BoardCount boardCount = new BoardCount(dto.getId());
        try {
            state = boardCount.execute().get().trim();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community_Detail.this, MainActivity.class);
                startActivityForResult(intent,10);
            }
        });


        //스크롤뷰 안에 리스트뷰

        comment_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });



        srl_main = findViewById(R.id.srl_main);
        srl_main.setOnRefreshListener(this);
        //srl_main(댓글 새로고침)

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        // 뒤로가기 버튼 클릭 이벤트
        post_back = findViewById(R.id.post_back);
        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });

        // 댓글처리등록
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO != null){
                    String flag = dto.getFlag();
                    String pid = id;
                    String comment = comment_et.getText().toString();
                    //String writedate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    BoardComment_Add commentAdd = new BoardComment_Add(flag,pid,member_id,comment);
                    try {
                        state = commentAdd.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (state.equals("1")) {
                        Toast.makeText(Community_Detail.this,
                                "댓글 등록 완료", Toast.LENGTH_SHORT).show();
                        comment_et.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);

                    } else {
                        Toast.makeText(Community_Detail.this,
                                "댓글 등록에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Community_Detail.this, "로그인하셔야 이용 가능합니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 댓글 클릭 시 삭제처리
        comment_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardCommentDTO dto1 = (BoardCommentDTO) adapter.getItem(position);
                if(member_id.equals(dto1.getMember_id())){
                    String comment_id = String.valueOf(dto1.getId());
                    String flag = dto1.getFlag();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Community_Detail.this);
                    builder.setTitle("댓글 삭제");
                    builder.setMessage("댓글을 삭제하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BoardComment_Delete boardComment_delete = new BoardComment_Delete(comment_id);
                            try {
                                state = boardComment_delete.execute().get().trim();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (state.equals("1")) {
                                Toast.makeText(Community_Detail.this,
                                        "삭제 완료", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Community_Detail.this,
                                        "삭제 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            }
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
            }
        });

        // 작성자 클릭 시 해당 작성자의 작성글 보기
        writer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Community_Detail.this);
                builder.setTitle("작성자 글 검색");
                builder.setMessage(writer_tv.getText().toString()+"의 글 보기");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String member_nickname = writer_tv.getText().toString();
                        Intent intent = new Intent(Community_Detail.this, Community_WriterDetail.class);
                        intent.putExtra("member_id", writers);
                        intent.putExtra("member_nickname", member_nickname);
                        startActivity(intent);
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



//        // 사진 크게보기
//        content_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Community_Detail.this, Community_Picture.class);
//                intent.putExtra("board_filename",board_filename);
//                startActivity(intent);
//            }
//        });

        // 게시글 수정
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community_Detail.this, Community_Modify.class);
                intent.putExtra("dto",dto);
                intent.putExtra("old_filename",old_filename);
                startActivity(intent);
                finish();
            }
        });


        // 게시글 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Community_Detail.this);
                builder.setTitle("게시글 삭제");
                builder.setMessage("게시글을 삭제하시면 복원할 수 없습니다.\n정말 삭제하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
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
                            Toast.makeText(Community_Detail.this,
                                    "삭제 완료", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Community_Detail.this,
                                    "게시글 삭제 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        overridePendingTransition(0, 0); //애니메이션 없애기
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

    @Override
    public void onRefresh() {
        dtos = new ArrayList<>();
        adapter = new BoardCommentListViewAdapter(Community_Detail.this, dtos);
        comment_layout.setAdapter(adapter);

        BoardCommentListSelect BoardCommentListSelect = new BoardCommentListSelect(pid, dtos, adapter);
        try {
            BoardCommentListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        srl_main.setRefreshing(false);
    }


    class LoadBoard extends AsyncTask<String,Void,String>{


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

                    // DB의 데이터들을 변수로 저장한 후 해당 TextView에 데이터 입력
                    String board_title = jsonObject.optString("board_title");
                    String writer = jsonObject.optString("member_nickname");
                    String board_content = jsonObject.optString("board_content");
                    String flag = jsonObject.optString("flag");
                    String board_writedate = jsonObject.optString("board_writedate");
                    String board_readcnt = String.valueOf(jsonObject.optInt("board_readcnt"));
                    board_filename = ipConfig + "/bteam/resources/upload/"+jsonObject.optString("board_filename");
                    old_filename = jsonObject.optString("board_filename");

                    if(flag.equals("B")){
                        flag = "자유게시판";
                    }else if(flag.equals("I")){
                        flag = "정보공유";
                    }else if(flag.equals("G")){
                        flag = "갤러리";
                    }else if(flag.equals("T")){
                        flag = "중고거래";
                    }else if(flag.equals("N")){
                        flag = "공지사항";
                    }else if(flag.equals("F")){
                        flag = "FAQ";
                    }else if(flag.equals("E")){
                        flag = "이벤트";
                    }else if(flag.equals("M")){
                        flag = "1:1 문의";
                    }



                    title_tv.setText(board_title);
                    writer_tv.setText(writer);
                    content_tv.setText(board_content);
                    flag_tv.setText(flag);
                    writedate_tv.setText(board_writedate);
                    readCnt_tv.setText(board_readcnt);

                    if(member_id.equals(writers)||member_id.equals("admin")){
                        btn_modify.setVisibility(View.VISIBLE);
                        btn_delete.setVisibility(View.VISIBLE);
                    }else{
                        btn_modify.setVisibility(View.GONE);
                        btn_delete.setVisibility(View.GONE);
                    }

                    if(old_filename.length() > 0) {
                        Log.d(TAG, "onTouch: 3");

                        Glide.with(Community_Detail.this)
                                .load(board_filename)
                                .into(content_iv);
                    }else {
                        Log.d(TAG, "onTouch: 4");
                        Glide.with(Community_Detail.this)
                                .load(R.drawable.no_image)
                                .into(content_iv);
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

    public void refresh(){
        dtos = new ArrayList<>();
        adapter = new BoardCommentListViewAdapter(Community_Detail.this, dtos);
        comment_layout.setAdapter(adapter);

        BoardCommentListSelect BoardCommentListSelect = new BoardCommentListSelect(pid, dtos, adapter);
        try {
            BoardCommentListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
