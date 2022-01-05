package com.example;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.PUPPYPLE.R;
import com.example.community.Community_Fragment1;
import com.example.community.Community_Fragment2;
import com.example.community.Community_Fragment3;
import com.example.community.Community_Fragment4;
import com.example.community.Community_Fragment5;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Fragment3 extends Fragment {
    private static final String TAG = "main:Fragment3";

    TabLayout tabs;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    Fragment1 fragment1;
    MainActivity mActivity;
    Context mContext;
    Community_Fragment1 communityFragment1;
    Community_Fragment2 communityFragment2;
    Community_Fragment3 communityFragment3;
    Community_Fragment4 communityFragment4;
    Community_Fragment5 communityFragment5;
    Fragment selFragment = null;

    // 메인액티비티 위에 올리기
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) getActivity();
        mContext = context;

    }

    // 메인액티비티에서 내리기
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =  (ViewGroup)inflater.inflate(R.layout.community_main, container, false);

        Log.d(TAG, "onCreateView: Fragment3");

        viewPager = rootView.findViewById(R.id.community_contain);

        // viewpager가 사용시 미리 로딩하는 페이지수
        viewPager.setOffscreenPageLimit(5);

        communityFragment1 = new Community_Fragment1();
        communityFragment2 = new Community_Fragment2();
        communityFragment3 = new Community_Fragment3();
        communityFragment4 = new Community_Fragment4();
        communityFragment5 = new Community_Fragment5();

        MyPagerAdapter adapter =
                new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addItem(communityFragment1);
        adapter.addItem(communityFragment2);
        adapter.addItem(communityFragment3);
        adapter.addItem(communityFragment4);
        adapter.addItem(communityFragment5);


        // 먼저 프래그먼트1이 처음화면에 보이게 초기화 시킨다.
        //getChildFragmentManager().beginTransaction().replace(R.id.community_contain, communityFragment1).commit();


        viewPager.setAdapter(adapter);

        // 탭을 생성한다.
        tabs = rootView.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageSelected(int position) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /*tabs.getTabAt(0).setText("전체");
        tabs.getTabAt(1).setText("자유게시판");
        tabs.getTabAt(2).setText("정보공유");
        tabs.getTabAt(3).setText("갤러리");
        tabs.getTabAt(4).setText("중고거래");

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();


                if(position == 0){
                    selFragment = communityFragment1;
                }else if(position == 1){
                    selFragment = communityFragment2;
                }else if(position == 2){
                    selFragment = communityFragment3;
                }else if(position == 3){
                    selFragment = communityFragment4;
                }else if(position == 4){
                    selFragment = communityFragment5;
                }

                getChildFragmentManager().beginTransaction().replace(R.id.community_contain,selFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


*/

        return rootView;
    }



    static class MyPagerAdapter extends FragmentStatePagerAdapter {
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
                return "전체";
            } else if (position == 1) {
                return "자유";
            } else if (position == 2) {
                return "정보공유";
            } else if (position == 3) {
                return "갤러리";
            } else if (position == 4) {
                return "중고거래";
            } else {
                return "";
            }

        }
    }
}
