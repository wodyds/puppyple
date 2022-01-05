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

import com.example.ATask.EventListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Option_Event_admin extends AppCompatActivity {

    ImageView option_event_back,option_event_new;
    ListView event_list;
    String member_id;
    ArrayList<BoardDTO> dtos;
    NoticeListViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_event_admin);

        if(loginDTO != null){
            member_id = loginDTO.getMember_id();
        }else {
            member_id = "";
        }
        option_event_new = findViewById(R.id.option_event_new);
        option_event_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option_Event_admin.this, Inquiry_Register.class);
                intent.putExtra("member_id",member_id);
                startActivityForResult(intent, 1002);
            }
        });

        option_event_back = findViewById(R.id.option_event_back);
        option_event_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

        event_list = findViewById(R.id.event_list);
        refresh();

        event_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                BoardDTO dto = (BoardDTO) adapter.getItem(i);

                Intent intent = new Intent(Option_Event_admin.this, Event_Detail.class);
                intent.putExtra("dto", dto);
                intent.putExtra("member_id", member_id);
                startActivityForResult(intent, 1002);


            }
        });

    }

    public void refresh(){
        dtos = new ArrayList<>();
        adapter = new NoticeListViewAdapter(Option_Event_admin.this, dtos);
        event_list.setAdapter(adapter);

        EventListSelect eventListSelect = new EventListSelect(dtos, adapter);
        try {
            eventListSelect.execute().get();
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

