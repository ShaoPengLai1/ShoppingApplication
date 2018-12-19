package com.bwei.shoppingcar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView images;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        jump();
        /*images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.8f);//第一个参数开始的透明度，第二个参数结束的透明度
                alphaAnimation.setDuration(3000);//多长时间完成这个动作
                images.startAnimation(alphaAnimation);
                RotateAnimation rotateAnimation = new RotateAnimation(0,180);
                rotateAnimation.setDuration(3000);
                images.startAnimation(rotateAnimation);

            }
        });*/



         btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (v.getId()){
                   case R.id.btn_start:
                       ObjectAnimator ani1=ObjectAnimator.ofFloat(images,"rotation",0,-180);
                       ObjectAnimator ani2=ObjectAnimator.ofFloat(images,"alpha",1f,0.8f);
                       AnimatorSet as=new AnimatorSet();
                       as.playTogether(ani1,ani2);
                       as.setDuration(3000) ;
                       as.start();
                       jump();
                       break;
               }
            }
        });

    }

    private void jump() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        },4000);
    }

    private void initView() {
        images = findViewById(R.id.images);
        btn_start = findViewById(R.id.btn_start);

    }
}
