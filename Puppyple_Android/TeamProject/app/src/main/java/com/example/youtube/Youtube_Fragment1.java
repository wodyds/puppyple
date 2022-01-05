package com.example.youtube;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.PUPPYPLE.R;

public class Youtube_Fragment1 extends Fragment {
//    Youtube_MainActivity activity;
    Button btnFrag1;
    ImageView youtube_daily1;

    // 화면이 붙을 때 작동하는 메소드 오버라이드 : 초기화
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // 프래그먼트가 속한 액티비티 가져옴
//        activity = (Youtube_MainActivity) getActivity();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.youtube_fragment1, container, false);
//        activity = (Youtube_MainActivity) getActivity();

        youtube_daily1 = rootView.findViewById(R.id.youtube_daily1);
        youtube_daily1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Youtube_1.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}
