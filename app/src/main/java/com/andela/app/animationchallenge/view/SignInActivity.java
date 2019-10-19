package com.andela.app.animationchallenge.view;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.andela.app.animationchallenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private final static String TAG="LoginActivity";
    private EditText userMail,userPassword;
    private  TextView goToSignUp;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private ImageView loginPhoto;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //In this activity the user will have the possibility to switch to the SignUpActivity
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        userMail=findViewById(R.id.login_mail);
        userPassword=findViewById(R.id.login_password);
        btnLogin=findViewById(R.id.loginBtn);
        loginProgress=findViewById(R.id.login_progress);
        loginPhoto=findViewById(R.id.loginImgPhoto);
        goToSignUp=(TextView)findViewById(R.id.signUptext);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        btnLogin.setTypeface(custom_font1);
        goToSignUp.setTypeface(custom_font);
        userMail.setTypeface(custom_font);
        userPassword.setTypeface(custom_font);

        //onPhotoClick listener that takes user to the signUp activity
        loginPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(registerIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.rotate_scale_in);
                finish();
            }
        });

        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail=userMail.getText().toString();
                final String password=userPassword.getText().toString();

                if(mail.isEmpty()){
                    userMail.setError("field is required!");
                    return;
                }

                if(password.isEmpty()){
                    userPassword.setError("field is required!");
                    return;
                }

                if(mail.isEmpty() || password.isEmpty()){
                    showMessage("Please verify all fields");
                    loginProgress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }else{
                    loginProgress.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);
                    signIn(mail,password);
                }
            }
        });

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.rotate_scale_in);
                finish();
            }
        });

    }
    //SignIn method logs the user in and changes UI
    private void signIn(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loginProgress.setVisibility(View.INVISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);

                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            loginProgress.setVisibility(View.INVISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
    //updateUI takes user to the MainAcitivity
    private void updateUI(FirebaseUser user) {
        Intent homeIntent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(homeIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.rotate_scale_in);
        finish();
    }
    //showMessage Displays a Toast
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    //onstart is overriden to check if user is already logged in ..if yes user is taken to MainAcitivity
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //user is already connected....
            updateUI(currentUser);
        }
    }

}



