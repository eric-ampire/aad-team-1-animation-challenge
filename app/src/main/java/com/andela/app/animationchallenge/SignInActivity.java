package com.andela.app.animationchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsername, mPassword;
    private Button mSignin;
    private TextView mSignup, mWelcomeMessage;
    private Intent intent;
    private int CALLING_PAGE_CODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        handleIntents();
        initComponents();
    }
    //Handle the intent and get the page code
    private void handleIntents(){
        intent =  getIntent();
        if(intent.hasExtra("SIGN_IN_CODE")){
            CALLING_PAGE_CODE = intent.getIntExtra("SIGN_IN_CODE", 0001);
        }else{
            CALLING_PAGE_CODE = 0001;
        }
    }

    private void initComponents(){
        mUsername = findViewById(R.id.et_username);
        mPassword = findViewById(R.id.et_password);
        mSignin = findViewById(R.id.btn_signin);
        mSignin.setOnClickListener(this);
        mSignup = findViewById(R.id.tv_signup);
        mSignup.setOnClickListener(this);
        mWelcomeMessage = findViewById(R.id.tv_welcome_message);
        mWelcomeMessage.setText("Welcome " + ((CALLING_PAGE_CODE == 0001) ? "Doctor" : "Patient"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signin : mSigninButtonClicked();
            break;
            case  R.id.tv_signup : mSignupButtonClicked();
        }
    }

    private  void mSigninButtonClicked(){
        Toast.makeText(SignInActivity.this, "Ooh, i did something", Toast.LENGTH_LONG).show();
    }

    private void mSignupButtonClicked(){

    }
}
