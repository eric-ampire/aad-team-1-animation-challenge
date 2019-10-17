package com.andela.app.animationchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.andela.app.animationchallenge.animations.AppAnimator;

public class SplashActivity extends AppCompatActivity implements Animator.AnimatorListener {

    private ImageView mLogo;
    private int splashTime;
    private Handler handler;
    private AppAnimator appAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initVariables();
        initComponents();

    }

    @Override
    protected void onStart() {
        super.onStart();

        handler = new Handler();



        handler.postDelayed(goToMainActivity(), splashTime);
    }

    private void initVariables(){

        splashTime = 5000;
        appAnimator = new AppAnimator(SplashActivity.this);
    }
    private void initComponents(){
        mLogo = findViewById(R.id.iv_logo);
    }


    private Runnable goToMainActivity(){
        appAnimator.fadeAnimation(mLogo, R.animator.alpha_splash_screen_logo_animator);
        return new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
    }

    @Override
    public void onAnimationStart(Animator animation) {
        Toast.makeText(SplashActivity.this, "Animation Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Toast.makeText(SplashActivity.this, "Animation Ended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        Toast.makeText(SplashActivity.this, "Animation Repeated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        Toast.makeText(SplashActivity.this, "Animation Repeated", Toast.LENGTH_LONG).show();
    }
}
