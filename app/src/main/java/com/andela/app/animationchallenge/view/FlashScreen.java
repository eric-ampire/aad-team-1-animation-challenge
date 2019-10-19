package com.andela.app.animationchallenge.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.andela.app.animationchallenge.R;
import com.andela.app.animationchallenge.view.MainActivity;

public class FlashScreen extends AppCompatActivity {

    private int SPLASH_TIME = 4000; // 4 seconds

    private Animation animLogo, tranlateLogo;
    private  ImageView logo, foto, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hiding the toolbar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_flash_screen);


        ConstraintLayout constraintLayout = findViewById(R.id.flash_screen_layout);
        logo=(ImageView)findViewById(R.id.logo);
        foto=(ImageView)findViewById(R.id.foto);
        share=(ImageView)findViewById(R.id.share);


        //Code to fade in and out background color
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);



        //launching fade animation
        animationDrawable.start();

        logoAnimation();



        //code for flashscreen to delay and give way to next page
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity (new Intent(FlashScreen.this, MainActivity.class) );
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME);
    }



    //method to logo_animation the logo
    private void logoAnimation() {


        animLogo= AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        logo.startAnimation(animLogo);

        tranlateLogo= AnimationUtils.loadAnimation(this,R.anim.translate_right);
        foto.startAnimation(tranlateLogo);

        tranlateLogo= AnimationUtils.loadAnimation(this,R.anim.translate_left);
        share.startAnimation(tranlateLogo);

    }
}
