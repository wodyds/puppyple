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

import com.example.ATask.AdminInquiryListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;
import com.example.community.Community_Detail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Option_inquiry_admin extends AppCompatActivity {

    ArrayList<BoardDTO> dtos;
    InquiryListViewAdapter adapter;
    ListView inquiry_Board;
    ImageView option_inquiry_back;
    FloatingActionButton new_button;
    String member_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_inquiry);
        new_button = findViewById(R.id.new_button);
        inquiry_Board = findViewById(R.id.inquiry_Board);
        member_id = loginDTO.getMember_id();
        option_inquiry_back = findViewById(R.id.option_inquiry_back);
        refresh();

        // 뒤로가기 버튼 클릭 이벤트
        option_inquiry_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        // 새글 등록 버튼 클릭 시 이벤트
        new_button.setVisibility(View.GONE);

        // 글 상세정보
        inquiry_Board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardDTO dto = (BoardDTO) adapter.getItem(position);

                Intent intent = new Intent(Option_inquiry_admin.this, Community_Detail.class);
                intent.putExtra("dto",dto);
                intent.putExtra("member_id",member_id);
                startActivityForResult(intent, 1002);
            }
        });
    }

    public void refresh(){
        dtos = new ArrayList<>();
        adapter = new InquiryListViewAdapter(Option_inquiry_admin.this, dtos);
        inquiry_Board.setAdapter(adapter);

        AdminInquiryListSelect adminInquiryListSelect = new AdminInquiryListSelect(dtos, adapter);
        try {
            adminInquiryListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1002){
            refresh();

        }

    }
}
