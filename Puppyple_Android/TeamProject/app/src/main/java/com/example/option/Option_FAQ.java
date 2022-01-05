package com.example.option;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ATask.FAQListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Option_FAQ extends AppCompatActivity {

    ImageView option_faq_back;
    private RecyclerView recyclerview;
    ArrayList<BoardDTO> dtos;
    ExpandableListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_faq_main);
        dtos = new ArrayList<>();
        FAQListSelect faqListSelect = new FAQListSelect(dtos);

        try {
            faqListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 뒤로가기 버튼 클릭 이벤트
        option_faq_back = findViewById(R.id.option_faq_back);
        option_faq_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });


        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<com.example.option.ExpandableListAdapter.Item> data = new ArrayList<>();
        ArrayList<ExpandableListAdapter.Item> itemsHeader = new ArrayList<>();
        for(int i=0; i < dtos.size(); i++){

            itemsHeader.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, dtos.get(i).getBoard_title()));
            itemsHeader.get(i).invisibleChildren = new ArrayList<>();
            itemsHeader.get(i).invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD,dtos.get(i).getBoard_content()));

            data.add(itemsHeader.get(i));
        }

//        com.example.option.ExpandableListAdapter.Item places1 = new com.example.option.ExpandableListAdapter.Item(com.example.option.ExpandableListAdapter.HEADER,
//                "자유게시판에 사진 올리고 싶은데 어떻게 올리나요??????????????????????????");
//        places1.invisibleChildren = new ArrayList<>();
//        places1.invisibleChildren.add(new com.example.option.ExpandableListAdapter.Item(com.example.option.ExpandableListAdapter.CHILD,
//                "로그인을 한 후 게시글 올리기에 클릭하고 게시글을 올리면 됩니다."));
//
//        com.example.option.ExpandableListAdapter.Item places2 = new com.example.option.ExpandableListAdapter.Item(com.example.option.ExpandableListAdapter.HEADER,
//                "회원가입은 어디서 하면 되나요?");
//        places2.invisibleChildren = new ArrayList<>();
//        places2.invisibleChildren.add(new com.example.option.ExpandableListAdapter.Item(com.example.option.ExpandableListAdapter.CHILD,
//                "옵션 페이지에 가면 '로그인을 해주세요'라는 페이지에 들어가면 가입할 수 있습니다.옵션 페이지에 가면 '로그인을 해주세요'라는 페이지에 들어가면 가입할 수 있습니다2."));
//
//        com.example.option.ExpandableListAdapter.Item places3 = new com.example.option.ExpandableListAdapter.Item(com.example.option.ExpandableListAdapter.HEADER,
//                "고객센터에 전화하면 다 들어주나요?");
//        places3.invisibleChildren = new ArrayList<>();
//        places3.invisibleChildren.add(new com.example.option.ExpandableListAdapter.Item(com.example.option.ExpandableListAdapter.CHILD,
//                "안되는거 빼고 다 됩니다."));
//
//        data.add(places1);
//        data.add(places2);
//        data.add(places3);

        recyclerview.setAdapter(new com.example.option.ExpandableListAdapter(data));

    }




}