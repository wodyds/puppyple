package com.example;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.PUPPYPLE.R;
import com.example.youtube.Youtube_Fragment1;
import com.example.youtube.Youtube_Fragment2;
import com.example.youtube.Youtube_Fragment3;
import com.example.youtube.Youtube_Fragment4;
import com.example.youtube.Youtube_Fragment5;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Fragment4 extends Fragment {
    private static final String TAG = "main:Fragment4";

    private ArrayList<String> name = new ArrayList<>();
    TabLayout tabs;
    ViewPager viewPager;
    Youtube_Fragment1 youtubeFragment1;
    Youtube_Fragment2 youtubeFragment2;
    Youtube_Fragment3 youtubeFragment3;
    Youtube_Fragment4 youtubeFragment4;
    Youtube_Fragment5 youtubeFragment5;

    Fragment selFragment = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.youtube_activity_main, container, false);

        Log.d(TAG, "onCreateView: Fragment4");

        viewPager = rootView.findViewById(R.id.youtube_contain);

        // viewpager가 사용시 미리 로딩하는 페이지수
        viewPager.setOffscreenPageLimit(5);

        youtubeFragment1 = new Youtube_Fragment1();
        youtubeFragment2 = new Youtube_Fragment2();
        youtubeFragment3 = new Youtube_Fragment3();
        youtubeFragment4 = new Youtube_Fragment4();
        youtubeFragment5 = new Youtube_Fragment5();

        MyPagerAdapter adapter =
                new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addItem(youtubeFragment1);
        adapter.addItem(youtubeFragment2);
        adapter.addItem(youtubeFragment3);
        adapter.addItem(youtubeFragment4);
        adapter.addItem(youtubeFragment5);

        // 먼저 프래그먼트1이 처음화면에 보이게 초기화 시킨다.
        getChildFragmentManager().beginTransaction().replace(R.id.youtube_contain, youtubeFragment1).commit();

        viewPager.setAdapter(adapter);

        // 탭을 생성한다.
        tabs = rootView.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                int position = tab.getPosition();
//
//
//                if (position == 0) {
//                    selFragment = youtubeFragment1;
//                } else if (position == 1) {
//                    selFragment = youtubeFragment2;
//                } else if (position == 2) {
//                    selFragment = youtubeFragment3;
//                } else if (position == 3) {
//                    selFragment = youtubeFragment4;
//                } else if (position == 4) {
//                    selFragment = youtubeFragment5;
//                }
//
//                getChildFragmentManager().beginTransaction().replace(R.id.youtube_contain, selFragment).commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


        return rootView;
    }
        class MyPagerAdapter extends FragmentStatePagerAdapter {
            // 뷰페이저에 보일 프래그먼트들을 담는 공간
            ArrayList<Fragment> items = new ArrayList<>();

            public MyPagerAdapter(@NonNull FragmentManager fm) {
                super(fm);
            }

            // ArrayList에 프래그먼트를 추가하는 매소드
            public void addItem(Fragment item) {
                items.add(item);
            }


            // items 인덱스 위치에 있는 프래그먼트를 가져온다
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return items.get(position);
            }

            // items가 가지고 있는 프래그먼트의 갯수
            @Override
            public int getCount() {
                return items.size();
            }

            //xml의 Tabstrip에 넣을 문자

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "일상";
                } else if (position == 1) {
                    return "교육";
                } else if (position == 2) {
                    return "의료/건강";
                } else if (position == 3) {
                    return "미용";
                } else if (position == 4) {
                    return "간식";
                } else {
                    return null;
                }

            }
        }
    }
