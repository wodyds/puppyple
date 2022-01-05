package com.example.option;

import static com.example.Common.CommonMethod.loginDTO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.NoticeListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Option_Notice_admin extends AppCompatActivity {

    ImageView option_notice_back,option_notice_new;
    ListView notice_list;
    ArrayList<BoardDTO> dtos;
    NoticeListViewAdapter adapter;
    String member_id = "";
    String admin = "";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_notice_admin);

        // 뒤로가기
        option_notice_back = findViewById(R.id.option_notice_back);
        option_notice_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        member_id = loginDTO.getMember_id();


        // 새글등록
        option_notice_new = findViewById(R.id.option_notice_new);
        option_notice_new.setVisibility(View.VISIBLE);

        option_notice_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option_Notice_admin.this, Notice_register.class);
                intent.putExtra("member_id",member_id);
                startActivityForResult(intent, 1002);
            }
        });

        notice_list = findViewById(R.id.notice_list);
        refresh();

        notice_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                BoardDTO dto = (BoardDTO) adapter.getItem(i);

                Intent intent = new Intent(Option_Notice_admin.this, Notice_Detail.class);
                intent.putExtra("dto", dto);
                intent.putExtra("member_id", member_id);
                startActivityForResult(intent, 1002);


            }
        });




    }

    public void refresh(){
        dtos = new ArrayList<>();
        adapter = new NoticeListViewAdapter(Option_Notice_admin.this, dtos);
        notice_list.setAdapter(adapter);
        NoticeListSelect noticeListSelect = new NoticeListSelect(dtos,adapter);
        try {
            noticeListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1002){
            refresh();
        }
    }
}
