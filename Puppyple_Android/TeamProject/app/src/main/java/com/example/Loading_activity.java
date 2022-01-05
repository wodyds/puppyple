package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.PUPPYPLE.R;

public class Loading_activity extends Activity {

    ImageView imageView, foot1, foot2, foot3, foot4, foot5;
    Animation loading1, loading2, loading3, loading4, loading5, loading6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        foot1 =  findViewById(R.id.foot1);
        foot2 =  findViewById(R.id.foot2);
        foot3 =  findViewById(R.id.foot3);
        foot4 =  findViewById(R.id.foot4);
        foot5 =  findViewById(R.id.foot5);
        imageView = findViewById(R.id.loadingText);

        loading1 = AnimationUtils.loadAnimation(this,R.anim.loading_animation1);
        loading2 = AnimationUtils.loadAnimation(this,R.anim.loading_animation2);
        loading3 = AnimationUtils.loadAnimation(this,R.anim.loading_animation3);
        loading4 = AnimationUtils.loadAnimation(this,R.anim.loading_animation4);
        loading5 = AnimationUtils.loadAnimation(this,R.anim.loading_animation5);
        loading6 = AnimationUtils.loadAnimation(this,R.anim.loading_animation6);


        foot5.setAnimation(loading1);
        foot4.setAnimation(loading2);
        foot3.setAnimation(loading3);
        foot2.setAnimation(loading4);
        foot1.setAnimation(loading5);
        imageView.setAnimation(loading6);

        startLoading();

    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }

        }, 3800);


    }


}