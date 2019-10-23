package com.andela.app.animationchallenge.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.andela.app.animationchallenge.R;
import com.andela.app.animationchallenge.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    private static final String TAG = "RegisterActivity";
    ImageView ImgUserPhoto;
    static int PReqCode = 1;

    Uri pickedImgUri;

    private EditText userName, userEmail, userPassword, userPassword2;
    private TextView goToLogin;
    private ProgressBar loadingProgress;
    private Button regBtn;

    private FirebaseAuth mAuth;
    Boolean photoChosen = false;

    // Writing to the database when user signs up to create a user object
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //From this activity the user will have the possibility to switch to the SignInActivity

        mAuth = FirebaseAuth.getInstance();
        //init views
        userName = (EditText) findViewById(R.id.regName);
        userEmail = (EditText) findViewById(R.id.regEmail);
        userPassword = (EditText) findViewById(R.id.regPassword);
        userPassword2 = (EditText) findViewById(R.id.regPassword2);
        goToLogin = (TextView) findViewById(R.id.logintext);
        loadingProgress = (ProgressBar) findViewById(R.id.reg_progress);
        loadingProgress.setVisibility(View.INVISIBLE);
        regBtn = (Button) findViewById(R.id.regBtn);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        userName.setTypeface(custom_font);
        userEmail.setTypeface(custom_font);
        userPassword.setTypeface(custom_font);
        userPassword2.setTypeface(custom_font);
        regBtn.setTypeface(custom_font);
        goToLogin.setTypeface(custom_font);

        regBtn.setOnClickListener(view -> {
            final String name = userName.getText().toString();
            final String email = userEmail.getText().toString();
            final String password = userPassword.getText().toString();
            final String password2 = userPassword2.getText().toString();

            if (email.isEmpty()) {
                userEmail.setError("field is required!");
                return;
            }

            if (name.isEmpty()) {
                userName.setError("field is required!");
                return;
            }

            if (password.isEmpty()) {
                userPassword.setError("field is required!");
                return;
            }

            if (password2.isEmpty()) {
                userPassword2.setError("field is required!");
                return;
            }

            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || password2.isEmpty() || !password.equals(password2)) {
                //something wrong
                //we need to display an error
                showMessage("Please Verify Fields");
                regBtn.setVisibility(View.VISIBLE);
                loadingProgress.setVisibility(View.INVISIBLE);
            } else {
                //everything is cool here ,all fields are fields and passwords the same
                if (photoChosen) {
                    regBtn.setVisibility(View.INVISIBLE);
                    loadingProgress.setVisibility(View.VISIBLE);
                    CreateUserAccount(email, name, password);
                } else {
                    Toast.makeText(getApplicationContext(), "click image to select your profile", Toast.LENGTH_LONG).show();
                }

            }
        });

        ImgUserPhoto = (ImageView) findViewById(R.id.signUpImgPhoto);
        ImgUserPhoto.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= 22) {
                checkAndRequestForPermission();
            } else {
                openGallery();
            }

        });//end of imageClick..

        goToLogin.setOnClickListener(view -> {
            Intent loginIntent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }

    //CreateUserAccount creates a user account using the Firebase method CreateUserAccountWithEmailAndPassword
    private void CreateUserAccount(String email, final String name, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    showMessage("Account created");
                    FirebaseUser user = mAuth.getCurrentUser();
                    //After creating a user we need to update the profile and name
                    updateUserInfo(name, pickedImgUri, user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }
            });

    }

    //updateUserInfor updates the userPhoto in the firebase currentUser instance
    private void updateUserInfo(final String name, final Uri pickedImgUri, final FirebaseUser user) {
        //first we need to upload the photo to firebase storage and get the uri
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());

        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(taskSnapshot -> {

            //image uploaded successfully
            //now we can get our image uri
            imageFilePath.getDownloadUrl().addOnSuccessListener(uri -> {
                ///uri contain user image uri
                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(uri)
                    .build();

                user.updateProfile(profileUpdate)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            //user information updated ...
                            saveUser(user);
                            updateUI();
                            showMessage("Registration Successfull");
                        }

                    });

            });
        });
    }

    // saveUser creates a user record in the firebase firestore (users->userid->User)
    private void saveUser(FirebaseUser user) {
        User newUser = new User();

        String userId = user.getUid();
        newUser.setDisplayName(user.getDisplayName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setPhotoUrl(user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "");
        newUser.setProviderId(user.getProviderId());
        newUser.setEmailVerified(user.isEmailVerified());
        newUser.setUid(userId);

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .set(newUser)
            .addOnSuccessListener(aVoid -> {
                // Write was successful!
                Log.d(TAG, "User saved to database");
            })
            .addOnFailureListener(e -> {
                // Write failed
                // ...
                Log.d(TAG, e.toString());
            });

    }

    //updateUI opens the Mainactivity after successful registration
    private void updateUI() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.rotate_scale_in);
        finish();
    }

    //showMessage displays a Toast to indicate an event
    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //openGalley calls an intent to get photos in user device so that user can select the profile picture
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    //checkAndRequestFropermission checks to see if user has granted FotoShare App permission to Read External Storage ..if not it asks for it
    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(SignUpActivity.this, "Please accept required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    //onActivityResult gets the result of the selected photo by the user
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            //the user has successfully selected a photo...
            //we need to save its references yeah
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

            photoChosen = true;
        } else {
            photoChosen = false;
        }

    }
}
