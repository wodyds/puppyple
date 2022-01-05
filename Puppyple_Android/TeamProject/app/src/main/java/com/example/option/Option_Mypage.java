package com.example.option;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PUPPYPLE.R;
import com.example.petInfo.PetListView_Activity;

public class Option_Mypage extends AppCompatActivity {


    TextView tvMyPagePetInfoModify, tvMyPageMemberModify, tvmypost ,tvservicequit ,mypage_cu_center;
    ImageView mypage_back;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_mypage);

        mypage_back = findViewById(R.id.mypage_back);
        mypage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }
        });

            tvMyPagePetInfoModify = findViewById(R.id.tvMyPagePetInfoModify);

            tvMyPagePetInfoModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), PetListView_Activity.class);
                    startActivity(intent);
                }
            });

/////////////////////////////////////////////////////////////////////////////////////////

        tvMyPageMemberModify = findViewById(R.id.tvMyPageMemberModify);
        tvMyPageMemberModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Option_member_modify.class);
                startActivity(intent);
            }
        });

        tvmypost = findViewById(R.id.tvmypost);
        tvmypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Option_ChkMyPost.class);
                startActivity(intent);
            }
        });

        tvservicequit = findViewById(R.id.tvservicequit);
        tvservicequit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Option_member_quit.class);
                startActivity(intent);
            }
        });

//        mypage_cu_center = findViewById(R.id.mypage_cu_center);
//        mypage_cu_center.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:01012345678"));
//                startActivity(intent);
//            }
//        });
    }
}

