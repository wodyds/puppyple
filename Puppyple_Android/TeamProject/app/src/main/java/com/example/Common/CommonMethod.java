package com.example.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.DTO.MemberDTO;
import com.example.DTO.PetDTO;

import java.util.ArrayList;

public class CommonMethod {
    //이걸 만드는 이유는 static으 로 어디서든 접근이 가능하도록 하려는것임

    //나의 ip를 선언해 놓는다.
    public static String ipConfig = "http://211.223.59.27:8002";
//    public static String ipConfig = "http://192.168.0.49:8002"; //JP
//    public static String ipConfig = "http://192.168.0.27:80"; //YM

    //로그인이 되었을때 loginDTO에 입력해 놓을 dto를 생성한다.
    public static MemberDTO loginDTO = null;
    public static PetDTO petDTO = null;
    public static ArrayList<PetDTO> dtos = null;

    //20211216 대표펫
    public static PetDTO petDTO_main = null;


    // 네트워크에 연결되어 있는가
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                Log.d("isconnected : ", "WIFI 로 설정됨");
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d("isconnected : ", "일반망으로 설정됨");
            }
            Log.d("isconnected : ", "OK => " + info.isConnected());
            return true;
        }else {
            Log.d("isconnected : ", "False => 데이터 통신 불가!!!" );
            return false;
        }

    }

}
