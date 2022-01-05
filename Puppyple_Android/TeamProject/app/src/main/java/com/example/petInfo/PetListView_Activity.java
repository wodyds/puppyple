package com.example.petInfo;

import static com.example.Common.CommonMethod.loginDTO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.PetListSelect;
import com.example.DTO.PetDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PetListView_Activity extends AppCompatActivity {
    private static final String TAG = "main:PetListView";

    ImageView ivPetListView, ivPetListViewDelete;
    ListView petListView;

    ArrayList<PetDTO> dtos;

    PetListViewAdapter adapter;

    ImageView petList_back;

    Button btn_petAdd;

    //20211216 CheckBox 대표이미지 기능추가 by JP
    CheckBox chBoxPetListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_listview);

        petList_back = findViewById(R.id.petList_back);
        petList_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });


        btn_petAdd = findViewById(R.id.btn_petAdd);
        btn_petAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PetInfoAdd_Activity.class);
                startActivityForResult(intent, 1001);
            }
        });

        ivPetListView = findViewById(R.id.ivPetListView);
        petListView = findViewById(R.id.petListView);

        //20211216 CheckBox 대표 이미지
        chBoxPetListView = findViewById(R.id.chBoxPetListView);

        //중요: dtos 넘겨줄때 반드시 생성해서 넘겨준다.
        dtos = new ArrayList<>();

        //어댑터 객체를 생성한다.
        adapter = new PetListViewAdapter(PetListView_Activity.this, dtos);

        petListView.setAdapter(adapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PetDTO dto = (PetDTO) adapter.getItem(position);

                Toast.makeText(PetListView_Activity.this,
                        "이름 : " + dto.getPet_name() +"\n견종 : " + dto.getPet_breed() +
                        "\n나이 : " + dto.getPet_age() + "\n성별 : " + dto.getPet_gender() +
                        "\n중성화여부 : " + dto.getPet_nuetering() + "\n몸무게 : " + dto.getPet_weight()
                , Toast.LENGTH_SHORT).show();

            }
        });

        //서버에 pet ArrayList를 구한다
        //참조: MyProjectX-MainActivity.java에서 참고함

        //11/22/2021 11:52 AM 주석처리
//        Log.d(TAG, "onCreate: member_id => " + loginDTO.getMember_id());
        String member_id = loginDTO.getMember_id();
        PetListSelect petListSelect = new PetListSelect(member_id, dtos, adapter);
        try {
            petListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //20211124에서 그것에 대한 onNewIntent를 해줘서, 다시 받아온다.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String member_id = loginDTO.getMember_id();
        PetListSelect petListSelect = new PetListSelect(member_id, dtos, adapter);
        try {
            petListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult1: " + requestCode);

        if(requestCode == 1001){
            Log.d(TAG, "onActivityResult2: " + loginDTO.getMember_id());

            String member_id = loginDTO.getMember_id();
            PetListSelect petListSelect = new PetListSelect(member_id, dtos, adapter);
            try {
                petListSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
