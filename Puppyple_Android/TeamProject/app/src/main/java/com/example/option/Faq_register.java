package com.example.option;

import static com.example.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.BoardAdd_Nofile;
import com.example.PUPPYPLE.R;

import java.util.concurrent.ExecutionException;

public class Faq_register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText et_title, et_content;
    Button reg_btn;
    Button btn_back;
    ImageView post_back;
    String state = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_register);

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

        // 등록하기 버튼 클릭 이벤트
        reg_btn = findViewById(R.id.btn_regist);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //20211119
                //0.회원의 id도 같이 물려주려는것
                String member_id = loginDTO.getMember_id();


                //1. 게시글 등록에 필요한 정보를 가져온다.
                String board_title = String.valueOf(et_title.getText());
                String board_content = et_content.getText().toString();
                String flag = "F";


                //2. 이정보를 비동기 Task로 넘겨 서버에게 전달한다.

                BoardAdd_Nofile boardAdd = new
                        BoardAdd_Nofile(flag, member_id, board_title, board_content);

                try {
                    state = boardAdd.execute().get().trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (state.equals("1")) {
                    Toast.makeText(Faq_register.this,
                            "FAQ 등록 완료", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(Faq_register.this,
                            "FAQ 등록에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // 취소하기 버튼 클릭 이벤트 (뒤로가기)
        post_back = findViewById(R.id.post_back);
        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
