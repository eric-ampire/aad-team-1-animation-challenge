package com.andela.app.animationchallenge.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.andela.app.animationchallenge.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Check if the user is already connected
        if(false) {
            // TODO: Switch to SignInActivity
        }
    }
}