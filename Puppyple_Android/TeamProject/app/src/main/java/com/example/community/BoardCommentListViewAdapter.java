package com.example.community;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.DTO.BoardCommentDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;

public class BoardCommentListViewAdapter extends BaseAdapter {

    private static final String TAG = "main:BoardAdapter";

    //메인에서 넘겨받을것 -> 생성자를 만든다.
    //화면이 없는 것들은 context를 받아줘야한다.

    Context context;
    ArrayList<BoardCommentDTO> dtos;

    //화면을 붙이기 위해서, 객체 생성
    LayoutInflater inflater;

    public BoardCommentListViewAdapter(Context context, ArrayList<BoardCommentDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
/////////////////////////////////////////////////////////////////////////////////
    //여기 안에서만 메소드를 만들어서 추가할 것

    //메소드를 만들때는 무조건 여기에 생성을 해줘야한다. 여기라는 말은 adapter에서
    //하나의 dto 추가하기 (PetDTO)

    public void addDto(BoardCommentDTO dto) {
        dtos.add(dto);
    }

    //dtos에 저장된 dto 갯수
    @Override
    public int getCount() {
        return dtos.size();
    }

    //dtos에 특정위치에 있는 dto 가져오기(SingerDTO)
    @Override
    public Object getItem(int position) {
        return dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //가장중요하다. : 화면을 생성하고 특정 클릭이벤트를 만들수 있다.
    //만약 화면 5개를 생성한다면 getView가 5번 실행된다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: " +position);

        BoardCommentListViewHolder BoardCommentListViewHolder;

        if(convertView == null) {
            //없기때문에 화면을 생성한다.
            convertView = inflater.inflate(R.layout.board_comment,
                    parent, false);

            BoardCommentListViewHolder = new BoardCommentListViewHolder();

            //생성한 곳에 petListViewHolder에 있는 tvpetname과 singerview.xml에 있는 것과
            //tvpet과 연결을시켜준다.
            //붙인 화면과 아래에 생성한 뷰홀더를 연결한다

            BoardCommentListViewHolder.cmt_userid_tv = convertView.findViewById(R.id.cmt_userid_tv);
            BoardCommentListViewHolder.cmt_date_tv = convertView.findViewById(R.id.cmt_date_tv);
            BoardCommentListViewHolder.cmt_content_tv = convertView.findViewById(R.id.cmt_content_tv);
            BoardCommentListViewHolder.cmt_id_tv = convertView.findViewById(R.id.cmt_id_tv);
            BoardCommentListViewHolder.cmt_flag_tv = convertView.findViewById(R.id.cmt_flag_tv);
            convertView.setTag(BoardCommentListViewHolder);

        }else {//캐시된 뷰가 있을 경우 그냥 거기에 getTag로 넣어준다는 의미

            BoardCommentListViewHolder = (BoardCommentListViewHolder) convertView.getTag();

        }

        //먼저 선택한 DTO 데이터 가져오기
        BoardCommentDTO dto = dtos.get(position);
        String id = String.valueOf(dto.getId());
        String flag = dto.getFlag();
        String member_id = dto.getMember_nickname();
        String writedate = dto.getWritedate();
        String content = dto.getContent();

        BoardCommentListViewHolder.cmt_id_tv.setText(id);
        BoardCommentListViewHolder.cmt_flag_tv.setText(flag);
        BoardCommentListViewHolder.cmt_userid_tv.setText(member_id);
        BoardCommentListViewHolder.cmt_date_tv.setText(writedate);
        BoardCommentListViewHolder.cmt_content_tv.setText(content);



        //이미지는 gradle을 사용해야한다.
        // URL 이미지로더


        return convertView;

    }


    //3. xml 파일을 사용된 모든 변수를 adapter에서 선언하다.
    public class BoardCommentListViewHolder {
        public TextView cmt_userid_tv,cmt_date_tv, cmt_content_tv,cmt_id_tv,cmt_flag_tv;
    }


}

