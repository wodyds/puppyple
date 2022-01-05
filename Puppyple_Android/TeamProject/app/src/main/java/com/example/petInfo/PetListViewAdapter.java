package com.example.petInfo;

import static com.example.Common.CommonMethod.loginDTO;
import static com.example.Common.CommonMethod.petDTO_main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.ATask.PetListViewDelete;
import com.example.DTO.PetDTO;
import com.example.PUPPYPLE.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PetListViewAdapter extends BaseAdapter {

    private static final String TAG = "main:PetListViewAdapter";

    //메인에서 넘겨받을것 -> 생성자를 만든다.
    //화면이 없는 것들은 context를 받아줘야한다.

    Context context;
    ArrayList<PetDTO> dtos;
    PetListViewAdapter adapter;

    int requestCode;

    //화면을 붙이기 위해서, 객체 생성
    LayoutInflater inflater;
    AlertDialog dialog;

    public PetListViewAdapter(Context context, ArrayList<PetDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
/////////////////////////////////////////////////////////////////////////////////
    //여기 안에서만 메소드를 만들어서 추가할 것

    //메소드를 만들때는 무조건 여기에 생성을 해줘야한다. 여기라는 말은 adapter에서
    //하나의 dto 추가하기 (PetDTO)

    public void addDto(PetDTO dto) {
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
        Log.d(TAG, "getView: " + position);

        PetListViewHolder petListViewHolder;

        if (convertView == null) {
            //없기때문에 화면을 생성한다.
            convertView = inflater.inflate(R.layout.pet_listview_templete,
                    parent, false);

            petListViewHolder = new PetListViewHolder();

            //생성한 곳에 petListViewHolder에 있는 tvpetname과 singerview.xml에 있는 것과
            //tvpet과 연결을시켜준다.
            //붙인 화면과 아래에 생성한 뷰홀더를 연결한다.

            petListViewHolder.ivPetListView = convertView.findViewById(R.id.ivPetListView);
            petListViewHolder.tvPetListViewName = convertView.findViewById(R.id.tvPetListViewName);
            petListViewHolder.tvPetListViewBreed = convertView.findViewById(R.id.tvPetListViewBreed);
            petListViewHolder.ivPetListViewDelete = convertView.findViewById(R.id.ivPetListViewDelete);

            //20211216 체크박스 추가 by JP
            petListViewHolder.chBoxPetListView = convertView.findViewById(R.id.chBoxPetListView);


            convertView.setTag(petListViewHolder);
        } else {//캐시된 뷰가 있을 경우 그냥 거기에 getTag로 넣어준다는 의미
            petListViewHolder = (PetListViewHolder) convertView.getTag();
        }

        //먼저 선택한 DTO 데이터 가져오기
        PetDTO dto = dtos.get(position);
        String pet_name = dto.getPet_name();
        String pet_breed = dto.getPet_breed();

        petListViewHolder.tvPetListViewName.setText(pet_name);
        petListViewHolder.tvPetListViewBreed.setText(pet_breed);

        //이미지는 gradle을 사용해야한다.
        // URL 이미지로더
//        if (dto.getPet_filename() != null) {
            Glide.with(context)
                    .load(dto.getPet_filename())
                    .circleCrop()
                    .placeholder(R.drawable.user_basic)
                    .into(petListViewHolder.ivPetListView);
//        }else if (dto.getPet_filename() == null) {
//            Glide.with(context)
//                    .load(R.drawable.user_basic)
//                    .circleCrop()
//                    .into(petListViewHolder.ivPetListView);
//        }


        //20211125 리스트뷰에서 이미지 클릭시 상세정보이벤트 걸어주기
        petListViewHolder.ivPetListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petListViewPopupXml(dtos.get(position));
            }
        });

        //20211216 리스트뷰에서 대표이미지 체크박스 클릭시 그 해당 펫의 id가 저장이 되도록 기능 구현
        petListViewHolder.chBoxPetListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                // Is the view now checked?
                boolean checked = ((CheckBox) view).isChecked();
                Log.d(TAG, "onClick: checked => " + checked);

                if (checked) {
                    petListViewHolder.chBoxPetListView.setChecked(true);
                    petDTO_main = dtos.get(position);
                } else {
                    petListViewHolder.chBoxPetListView.setChecked(false);
                    petDTO_main = null;
                }

            }
        });

        //20211124 휴지통 이미지 클릭시 실행
        petListViewHolder.ivPetListViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /////////////////////////////////////////////////////////////////////////////////////////
                // 로그아웃처리 (로그인정보 삭제 후 홈화면)
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("반려동물등록정보삭제");
                builder.setMessage("정말 삭제 하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (loginDTO != null) {
                            //사용할 변수 명 초기화
                            String member_id = loginDTO.getMember_id();

                            Log.d(TAG, "onClick: " + dto.getPet_id());

                            String pet_id = String.valueOf(dto.getPet_id());

                            ////이제 위의 정보를 비동기 Task로 넘겨 서버에게 전달한다.
                            PetListViewDelete petListViewDelete =
                                    new PetListViewDelete(member_id, pet_id, dtos, adapter);
                            try {
                                petListViewDelete.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //20211124 어댑터는 activity가 없어서 아래와 같이 context와, flag를 해주고,
                            //그 Listview에 갱신이 되도록 해준다.
                            Intent intent = new Intent(context, PetListView_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            context.startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        return convertView;
    }//getView()

    //20211125 petListViewPopup 클릭시 보여실 팝업창 레이아웃
    private void petListViewPopupXml(PetDTO dto) {

        //1. res에 xml파일을 만든다.
        //2. 그 xml 파일을 inflate 시켜서 setView 한다.

        //팝업창에 xml 붙이기
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pet_listview_popup, null);

        //xml에 있는 리소스 찾기
        ImageView ivPetListViewPopup = view.findViewById(R.id.ivPetListViewPopupImage);
        TextView tvPetListViewPopupName = view.findViewById(R.id.tvPetListViewPopupName);
        TextView tvPetListViewPopupBreed = view.findViewById(R.id.tvPetListViewPopupBreed);
        TextView tvPetListViewPopupAge = view.findViewById(R.id.tvPetListViewPopupAge);
        TextView tvPetListViewPopupGender = view.findViewById(R.id.tvPetListViewPopupGender);
        TextView tvPetListViewPopupNuetering = view.findViewById(R.id.tvPetListViewPopupNuetering);
        TextView tvPetListViewPopupWeight = view.findViewById(R.id.tvPetListViewPopupWeight);

        ImageView ivPetInfoPopupBack = view.findViewById(R.id.ivPetInfoPopupBack);
        Button btnPetInfoPopupModify = view.findViewById(R.id.btnPetInfoPopupModify);


        //xml에 데이터 연결하기
        Glide.with(context)
                .load(dto.getPet_filename())
                .placeholder(R.drawable.user_basic)
                .into(ivPetListViewPopup);

        tvPetListViewPopupName.setText("이름 : " + dto.getPet_name());
        tvPetListViewPopupBreed.setText("견종 : " + dto.getPet_breed());
        tvPetListViewPopupAge.setText("나이 : " + dto.getPet_age());
        
        if (dto.getPet_gender().equals("M")) {
            tvPetListViewPopupGender.setText("성별 : 수컷");    
        } else if (dto.getPet_gender().equals("F")){
            tvPetListViewPopupGender.setText("성별 : 암컷");
        }
        
        tvPetListViewPopupNuetering.setText("중성화여부 : " + dto.getPet_nuetering());
        tvPetListViewPopupWeight.setText("몸무게 : " + dto.getPet_weight());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("").setView(view); //타이틀(상세정보) 공백처리


        ivPetInfoPopupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        btnPetInfoPopupModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PetListViewInfoModify_Activity.class);
                intent.putExtra("dto", dto);
                context.startActivity(intent);
            }
        });


        dialog = builder.create();

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //background 둥근 모서리 처리하려고 기존배경 투명하게 처리

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width =1250;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //dialog.show();

        Window window = dialog.getWindow();
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }


    //3. xml 파일을 사용된 모든 변수를 adapter에서 선언하다.
    public class PetListViewHolder {
        public ImageView ivPetListView, ivPetListViewDelete;
        public TextView tvPetListViewName, tvPetListViewBreed;


        //20211216 체크박스추가
        public CheckBox chBoxPetListView;

    }


}

