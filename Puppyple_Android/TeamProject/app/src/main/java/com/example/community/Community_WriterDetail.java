package com.example.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.MyBoardListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Community_WriterDetail extends AppCompatActivity {

    TextView post_title;
    ListView post_list;
    ImageView post_back;
    String member_id,member_nickname;
    ArrayList<BoardDTO> dtos;
    BoardListViewAdapter adapter;
    Button back_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_writersearch);
        member_id = getIntent().getExtras().getString("member_id");
        member_nickname = getIntent().getExtras().getString("member_nickname");
        post_list = findViewById(R.id.post_list);
        post_title = findViewById(R.id.post_title);


        // 뒤로가기 버튼
        post_back = findViewById(R.id.post_back);
        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });


        dtos = new ArrayList<>();
        adapter = new BoardListViewAdapter(Community_WriterDetail.this, dtos);
        post_list.setAdapter(adapter);
        MyBoardListSelect myboardListSelect = new MyBoardListSelect(dtos, adapter,member_id);

        try {
            myboardListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        post_title.setText(member_nickname + "님의 작성글");


        post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                BoardDTO dto = (BoardDTO) adapter.getItem(i);

                Intent intent = new Intent(Community_WriterDetail.this, Community_Detail.class);
                intent.putExtra("dto", dto);
                intent.putExtra("member_id", member_id);
                startActivity(intent);


            }
        });

    }
}
