package com.andela.app.animationchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDoctor, mPatient;
    private int DOCTOR_SIGN_IN_CODE = 0001;
    private int PATIENT_SIGN_IN_CODE = 0002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        initComponents();

    }

    private void initComponents(){
        mDoctor = findViewById(R.id.btn_doctor);
        mPatient = findViewById(R.id.btn_patient);
        mDoctor.setOnClickListener(this);
        mPatient.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_doctor: handleButtonClick(DOCTOR_SIGN_IN_CODE);
            break;
            case R.id.btn_patient: handleButtonClick(PATIENT_SIGN_IN_CODE);
        }
    }

    private void handleButtonClick(int signInCode){
        Intent intent = new Intent(SelectionActivity.this, SignInActivity.class);
        intent.putExtra("SIGN_IN_CODE", signInCode);
        startActivity(intent);
    }
}
