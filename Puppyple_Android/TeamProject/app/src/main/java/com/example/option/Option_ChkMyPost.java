package com.example.option;

import static com.example.Common.CommonMethod.loginDTO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.MyBoardListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;
import com.example.community.BoardListViewAdapter;
import com.example.community.Community_Detail;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Option_ChkMyPost extends AppCompatActivity {

    ListView post_list;
    ImageView post_back;
    String member_id;
    ArrayList<BoardDTO> dtos;
    BoardListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_mypost);

        // 뒤로가기버튼
        post_back = findViewById(R.id.post_back);
        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        post_list = findViewById(R.id.post_list);
        member_id = loginDTO.getMember_id();
        refresh();

        post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                BoardDTO dto = (BoardDTO) adapter.getItem(i);

                Intent intent = new Intent(Option_ChkMyPost.this, Community_Detail.class);
                intent.putExtra("dto", dto);
                intent.putExtra("member_id", member_id);
                startActivity(intent);


            }
        });

    }
    public void refresh(){
        dtos = new ArrayList<>();
        adapter = new BoardListViewAdapter(Option_ChkMyPost.this, dtos);
        post_list.setAdapter(adapter);

        MyBoardListSelect myboardListSelect = new MyBoardListSelect(dtos, adapter,member_id);
        try {
            myboardListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}