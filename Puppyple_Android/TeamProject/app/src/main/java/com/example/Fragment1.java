package com.example;

import static com.example.Common.CommonMethod.dtos;
import static com.example.Common.CommonMethod.loginDTO;
import static com.example.Common.CommonMethod.petDTO_main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.ATask.ArduFeedMotor;
import com.example.ATask.MainBoardListSelect;
import com.example.ATask.MainNoticeListSelect;
import com.example.ATask.PetListSelect;
import com.example.Common.CommonMethod;
import com.example.DTO.BoardDTO;
import com.example.PUPPYPLE.R;
import com.example.community.Community_Detail;
import com.example.community.MainBoardListViewAdapter;
import com.example.login.Login_Activity;
import com.example.option.Event_Img;
import com.example.option.Notice_Detail;
import com.example.option.Option_Notice;
import com.example.petInfo.PetListView_Activity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import me.relex.circleindicator.CircleIndicator;

public class Fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "main: Fragment1";

    //20211216 메가폰 설정 by JP
    static TextView introduce;

    Dialog dialog;
    Context mContext;
    TextView textView;
    MainActivity mActivity;
    SwipeRefreshLayout swipe_pet;
    ArrayList<BoardDTO> dto,dto1;
    MainBoardListViewAdapter adapter,adapter1;
    //20211125 홈화면 이미지뷰 펫 사진이 보이고 클릭시 PetListView로 옮기기
    ImageView home_imageView;
    TextView home_Name,home_Age,home_gender,home_Breed,home_Name_title,home_Age_title,home_gender_title,home_Breed_title,login_plz,btn_notice_go,btn_board_go,btn_event_go;
    ListView lv_notice,lv_board;
    String member_id = "";
    ViewPager2 mPager;
    ImageViewPagerAdapter pagerAdapter;
    int num_page = 3;
    CircleIndicator mIndicator;
    private ViewPager viewPager;
    private ImageViewPagerAdapter ImagePagerAdapter;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_home, container, false);

        //20211216 메가폰 설정 by JP
        introduce = rootView.findViewById(R.id.introduce);
        introduce.setSelected(true);

        home_imageView = rootView.findViewById(R.id.home_imageView);

        home_Name = rootView.findViewById(R.id.home_Name);
        home_Age = rootView.findViewById(R.id.home_Age);
        home_gender = rootView.findViewById(R.id.home_gender);
        home_Breed = rootView.findViewById(R.id.home_Breed);
        home_Name_title = rootView.findViewById(R.id.home_name_title);
        home_Age_title = rootView.findViewById(R.id.home_age_title);
        home_gender_title = rootView.findViewById(R.id.home_gender_title);
        home_Breed_title = rootView.findViewById(R.id.home_Breed_title);
        btn_notice_go = rootView.findViewById(R.id.btn_notice_go);
        btn_board_go = rootView.findViewById(R.id.btn_board_go);
        btn_event_go = rootView.findViewById(R.id.btn_event_go);
        lv_notice = rootView.findViewById(R.id.lv_notice);
        lv_board = rootView.findViewById(R.id.lv_board);
        lv_notice.setVerticalScrollBarEnabled(false);
        lv_board.setVerticalScrollBarEnabled(false);

        login_plz = rootView.findViewById(R.id.login_plz);
        swipe_pet = rootView.findViewById(R.id.swipe_pet);
        swipe_pet.setOnRefreshListener(this);
        onRefresh();

        // 문구 클릭 시 로그인창
        login_plz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Login_Activity.class);
                startActivityForResult(intent,1000);
            }
        });

        //home_imageView 버튼 클릭시 확인
        rootView.findViewById(R.id.home_imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginDTO != null) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), PetListView_Activity.class);
                    startActivity(intent);
                }else if (loginDTO == null) {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivityForResult(intent,1000);
                }
            }
        });

        if(loginDTO != null){
            member_id = loginDTO.getMember_id();
        }else{
            member_id = "";
        }




        // 공지사항 more 버튼 클릭
        btn_notice_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Option_Notice.class);
                startActivity(intent);
            }
        });

        // 자유게시판 more 버튼 클릭
        btn_board_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.fragmentChange(3);
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.tab3);
            }
        });

        // 이벤트 more 버튼 클릭
        btn_event_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Event_Img.class);
                startActivity(intent);
            }
        });
        // 메인화면 커뮤니티 클릭 리스너
        lv_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardDTO dto = (BoardDTO) adapter.getItem(position);

                //Toast.makeText(getActivity(),dto.getBoard_title(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Community_Detail.class);
                intent.putExtra("dto", dto);
                intent.putExtra("member_id", member_id);
                startActivityForResult(intent, 1002);
            }
        });
        // 메인화면 공지사항 클릭 리스너
        lv_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardDTO dto = (BoardDTO) adapter1.getItem(position);

                //Toast.makeText(getActivity(),dto.getBoard_title(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Notice_Detail.class);
                intent.putExtra("dto", dto);
                intent.putExtra("member_id", member_id);
                startActivityForResult(intent, 1002);
            }
        });
//        if (loginDTO != null && petDTO != null) {
//
//            Log.d(TAG, "onCreateView: " + petDTO.getPet_name());
//
//            String pet_filename = petDTO.getPet_filename();
//            Glide.with(getActivity().getApplicationContext())
//                    .load(pet_filename)
//                    .into(home_imageView);
//        }





        //캠버튼 클릭 이벤트
        rootView.findViewById(R.id.iot_Cam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginDTO == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "로그인하셔야 이용 가능합니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivityForResult(intent,1000);
                }else{
                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(300);
                    dialog =  new Dialog(mContext);       // Dialog 초기화
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                    dialog.setContentView(R.layout.dialog_iot_cam);    // xml 레이아웃 파일과 연결
                    showMessage_cam();
                }
            }

        });


        //밥버튼 클릭 이벤트
        rootView.findViewById(R.id.iot_Feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginDTO == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "로그인하셔야 이용 가능합니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivityForResult(intent,1000);
                }else{
                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(300);
                    dialog =  new Dialog(mContext);       // Dialog 초기화
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                    dialog.setContentView(R.layout.dialog_iot_feed);    // xml 레이아웃 파일과 연결
                    showMessage_feed();
                }
            }
        });

            viewPager = rootView.findViewById(R.id.viewPager);
            pagerAdapter = new ImageViewPagerAdapter(mContext);
            viewPager.setAdapter(pagerAdapter);



            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                @Override
                public void run() {
                    if(currentPage == 3) {
                        currentPage = 0;
                    }
                    viewPager.setCurrentItem(currentPage++, true);
                }
            };

            mIndicator = rootView.findViewById(R.id.indicator);
            mIndicator.setViewPager(viewPager);
            mIndicator.createIndicators(num_page,0);
            num_page++;
            if(num_page == 3){
                num_page = 0;
            }



            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);





        return rootView;
    }

    public void showMessage_cam() {
        dialog.show(); // 다이얼로그 띄우기
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // 아니오 버튼
        textView = dialog.findViewById(R.id.dialog_iot_cam_no);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        textView = dialog.findViewById(R.id.dialog_iot_cam_yes);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                Toast.makeText(mContext, "IoT 펫 카메라와 연결합니다", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // 다이얼로그 닫기, IoT 기능 구현 후 삭제할것

                //20211216 webView 카메라 연결 by JP
                Intent intent = new Intent(getActivity().getApplicationContext(), WebCamActivity.class);
                startActivity(intent);

            }
        });
    }

    public void showMessage_feed() {
        dialog.show(); // 다이얼로그 띄우기
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // 아니오 버튼
        textView = dialog.findViewById(R.id.dialog_iot_feed_no);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        textView = dialog.findViewById(R.id.dialog_iot_feed_yes);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                Toast.makeText(mContext, "사료급여 신호를 보냅니다", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // 다이얼로그 닫기, IoT 기능 구현 후 삭제할것

                //서버로 데이터를 보낸다
                ArduFeedMotor arduFeedMotor = new ArduFeedMotor();
                try {
                    arduFeedMotor.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }



    @Override
    public void onRefresh() {
        dtos = new ArrayList<>();

        // 메인화면 공지사항 리스트 조회
        dto1 = new ArrayList<>();
        adapter1 = new MainBoardListViewAdapter(mContext, dto1);
        lv_notice.setAdapter(adapter1);
        MainNoticeListSelect mainNoticeListSelect = new MainNoticeListSelect(dto1, adapter1);
        try {
            mainNoticeListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 메인화면 커뮤니티 리스트 조회
        dto = new ArrayList<>();
        adapter = new MainBoardListViewAdapter(mContext, dto);
        lv_board.setAdapter(adapter);
        MainBoardListSelect mainboardListSelect = new MainBoardListSelect(dto, adapter);
        try {
            mainboardListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





        if (loginDTO != null) {
            String member_id = loginDTO.getMember_id();
            PetListSelect petListSelect = new PetListSelect(member_id, dtos);
            Log.d(TAG, "onCreate: " +  dtos.size());
            try {
                petListSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (dtos.size() > 0) {

                Log.d(TAG, "onCreate: " +  dtos.size());
                CommonMethod.dtos = dtos;

            }

        }

        if(loginDTO == null){
            home_imageView.setImageResource(R.drawable.user_basic);
            home_Name.setVisibility(View.GONE);
            home_Age.setVisibility(View.GONE);
            home_gender.setVisibility(View.GONE);
            home_Breed.setVisibility(View.GONE);
            home_Name_title.setVisibility(View.GONE);
            home_Age_title.setVisibility(View.GONE);
            home_gender_title.setVisibility(View.GONE);
            home_Breed_title.setVisibility(View.GONE);
            login_plz.setVisibility(View.VISIBLE);
        }else if(loginDTO != null && (dtos == null || dtos.size() == 0)){
            home_imageView.setImageResource(R.drawable.user_basic);
            home_Name.setVisibility(View.GONE);
            home_Age.setVisibility(View.GONE);
            home_gender.setVisibility(View.GONE);
            home_Breed.setVisibility(View.GONE);
            home_Name_title.setVisibility(View.GONE);
            home_Age_title.setVisibility(View.GONE);
            home_gender_title.setVisibility(View.GONE);
            home_Breed_title.setVisibility(View.GONE);
            login_plz.setVisibility(View.VISIBLE);
            login_plz.setText("등록한 펫 정보가 없습니다");
        } else if (loginDTO != null && dtos.size() > 0) {
            //20211216 대표이미지 by JP
            if (petDTO_main == null) {
                String pet_filename = dtos.get(0).getPet_filename();
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                requestOptions.skipMemoryCache(false);
                requestOptions.signature(new ObjectKey(System.currentTimeMillis()));

                Glide.with(mContext)
                        .load(pet_filename)
                        .circleCrop()
                        .placeholder(R.drawable.user_basic)
                        .into(home_imageView);

                home_Name.setVisibility(View.VISIBLE);
                home_Age.setVisibility(View.VISIBLE);
                home_gender.setVisibility(View.VISIBLE);
                home_Breed.setVisibility(View.VISIBLE);
                home_Name_title.setVisibility(View.VISIBLE);
                home_Age_title.setVisibility(View.VISIBLE);
                home_gender_title.setVisibility(View.VISIBLE);
                home_Breed_title.setVisibility(View.VISIBLE);
                login_plz.setVisibility(View.GONE);

                home_Name.setText(dtos.get(0).getPet_name());
                home_Age.setText(dtos.get(0).getPet_age());

                if (dtos.get(0).getPet_gender().equals("M")) {
                    home_gender.setText("수컷");
                }else if (dtos.get(0).getPet_gender().equals("F")) {
                    home_gender.setText("암컷");
                }

                home_Breed.setText(dtos.get(0).getPet_breed());
            } else if (petDTO_main != null) {

                String pet_filename = petDTO_main.getPet_filename();
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                requestOptions.skipMemoryCache(false);
                requestOptions.signature(new ObjectKey(System.currentTimeMillis()));

                Glide.with(mContext)
                        .load(pet_filename)
                        .circleCrop()
                        .placeholder(R.drawable.user_basic)
                        .into(home_imageView);

                home_Name.setVisibility(View.VISIBLE);
                home_Age.setVisibility(View.VISIBLE);
                home_gender.setVisibility(View.VISIBLE);
                home_Breed.setVisibility(View.VISIBLE);
                home_Name_title.setVisibility(View.VISIBLE);
                home_Age_title.setVisibility(View.VISIBLE);
                home_gender_title.setVisibility(View.VISIBLE);
                home_Breed_title.setVisibility(View.VISIBLE);
                login_plz.setVisibility(View.GONE);

                home_Name.setText(petDTO_main.getPet_name());
                home_Age.setText(petDTO_main.getPet_age());

                if (petDTO_main.getPet_gender().equals("M")) {
                    home_gender.setText("수컷");
                }else if (petDTO_main.getPet_gender().equals("F")) {
                    home_gender.setText("암컷");
                }

                home_Breed.setText(petDTO_main.getPet_breed());

            }
        }

        swipe_pet.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            onRefresh();
        }
    }
}
