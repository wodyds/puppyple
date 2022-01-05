package com.example.community;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {
    MainActivity activity;
    Button btnFrag4;


    // 화면이 붙을 때 작동하는 메소드 오버라이드 : 초기화
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // 프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);
        activity = (MainActivity) getActivity();

        btnFrag4 = rootView.findViewById(R.id.btnFrag4);



        return rootView;
    }
}
