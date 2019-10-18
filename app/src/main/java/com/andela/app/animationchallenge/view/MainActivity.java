package com.andela.app.animationchallenge.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.andela.app.animationchallenge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checking if the user is already connected otherwise go to SignInActivity...
        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent SignInIntent=new Intent(MainActivity.this,SignInActivity.class);
            startActivity(SignInIntent);
            finish();
        }

        //TODO : implementing Logout feature on this Activity UI
        //using ----> mAuth.signOut();
        //TODO : implementing the MainActivity for user to see shared photos and app navigation......
    }

}