package com.example.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;

    Fragment selFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();

        // 먼저 프래그먼트1이 처음화면에 보이게 초기화 시킨다.
        getSupportFragmentManager().beginTransaction().replace(R.id.contain, fragment1).commit();

        // 탭을 생성한다.
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("일상"));
        tabs.addTab(tabs.newTab().setText("교육"));
        tabs.addTab(tabs.newTab().setText("의료/건강"));
        tabs.addTab(tabs.newTab().setText("미용"));
        tabs.addTab(tabs.newTab().setText("간식"));

        // 탭 레이아웃에 리스너 달아주기
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // 탭이 선택되었을때
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if(position == 0){
                    selFragment = fragment1;
                }else if(position == 1){
                    selFragment = fragment2;
                }else if(position == 2){
                    selFragment = fragment3;
                }else if(position == 3){
                    selFragment = fragment4;
                }else if(position == 4){
                    selFragment = fragment5;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contain, selFragment).commit();
            }

            // 탭이 선택되지 않았을때
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            // 탭이 다시 선택되었을때
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}