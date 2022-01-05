package com.example.community;

import static com.example.Common.CommonMethod.loginDTO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ATask.GalleryBoardListSelect;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Community_Fragment4 extends Fragment {
    FloatingActionButton new_button;
    ListView Gallery_Board;
    ImageView ivBoardListView;
    ArrayList<BoardDTO> dtos;
    BoardListViewAdapter adapter;
    String member_id = "";


    // 화면이 붙을 때 작동하는 메소드 오버라이드 : 초기화
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.community_fragment4, container, false);

        new_button = rootView.findViewById(R.id.new_button);
        Gallery_Board = rootView.findViewById(R.id.Gallery_Board);
        if(loginDTO != null){
            member_id = loginDTO.getMember_id();
        }else{
            member_id = "";
        }

        // 새글 등록 버튼 클릭 시 이벤트
        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginDTO != null) {
                    Intent intent = new Intent(getActivity(),Community_register.class);
                    intent.putExtra("member_id",member_id);

                    //20211221 게시판글작성시 게시판에 따로 자동으로 Spinner 선택되도록 설정 by JP
                    intent.putExtra("flag", "G");

                    startActivityForResult(intent, 1002);

                }else {
                    Toast.makeText(getActivity(), "로그인하셔야 글 등록이 가능합니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivBoardListView = rootView.findViewById(R.id.ivBoardListView);
        refresh();

        Gallery_Board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                BoardDTO dto = (BoardDTO) adapter.getItem(i);

                //Toast.makeText(getActivity(),dto.getBoard_title(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Community_Detail.class);
                intent.putExtra("dto", dto);
                intent.putExtra("member_id", member_id);
                startActivityForResult(intent, 1002);


            }
        });



//        //112421 JP
//        Intent intent = new Intent(getActivity(), Community_Fragment1.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        getActivity().startActivity(intent);
        ////////////////////////////////////////////

        return rootView;
    }

    public void refresh(){
        dtos = new ArrayList<>();
        adapter = new BoardListViewAdapter(getActivity(), dtos);
        Gallery_Board.setAdapter(adapter);

        GalleryBoardListSelect galleryBoardListSelect = new GalleryBoardListSelect(dtos, adapter);
        try {
            galleryBoardListSelect.execute().get();
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
