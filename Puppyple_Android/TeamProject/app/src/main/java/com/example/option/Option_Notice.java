package com.example.option;

import static com.example.Common.CommonMethod.loginDTO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ATask.NoticeListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Option_Notice extends AppCompatActivity {

    ImageView option_notice_back,option_notice_new;
    private RecyclerView notice_recycle;
    ArrayList<BoardDTO> dtos;
    String member_id = "";
    String admin = "";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_notice_main);

        // 뒤로가기
        option_notice_back = findViewById(R.id.option_notice_back);
        option_notice_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });
        if(loginDTO != null){
            member_id = loginDTO.getMember_id();
            admin = loginDTO.getMember_admin();
        }else{
            member_id = "";
            admin = "";
        }
        // 새글등록
        option_notice_new = findViewById(R.id.option_notice_new);
        if(admin.equals("Y")){
            option_notice_new.setVisibility(View.VISIBLE);
        }else{
            option_notice_new.setVisibility(View.GONE);
        }
        option_notice_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option_Notice.this, Notice_register.class);
                intent.putExtra("member_id",member_id);
                startActivityForResult(intent, 1002);
            }
        });

        notice_recycle = findViewById(R.id.notice_recycle);
        refresh();

        notice_recycle = findViewById(R.id.notice_recycle);
        notice_recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();
        ArrayList<ExpandableListAdapter.Item> itemsHeader = new ArrayList<>();
        for(int i=0; i < dtos.size(); i++){

            itemsHeader.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, dtos.get(i).getBoard_title()));
            itemsHeader.get(i).invisibleChildren = new ArrayList<>();
            itemsHeader.get(i).invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD,dtos.get(i).getBoard_content()));

            data.add(itemsHeader.get(i));
        }


        notice_recycle.setAdapter(new com.example.option.ExpandableListAdapter(data));


    }

    public void refresh(){
        dtos = new ArrayList<>();
        NoticeListSelect noticeListSelect = new NoticeListSelect(dtos);
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
