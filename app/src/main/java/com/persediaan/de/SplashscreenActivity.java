package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashscreenActivity extends AppCompatActivity {
    Animation topAnim,botAnim;
    ProgressBar progressBar;

    LottieAnimationView imgAnim;
    TextView title;
    Intent home,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        home=new Intent(this,MainActivity.class);
        login=new Intent(this,LoginActivity.class);

        imgAnim = findViewById(R.id.animation_view);
        title = findViewById(R.id.tvTitle);

//        progressBar = findViewById(R.id.progressSplash);
//        progressBar.setVisibility(View.GONE);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        imgAnim.setAnimation(topAnim);
        title.setAnimation(botAnim);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        },1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(imgAnim,"imgTrans");
                pairs[1] = new Pair<View,String>(title,"titleTrans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashscreenActivity.this,pairs);
                startActivity(login,options.toBundle());
                finish();
            }
        },3000);


    }
}