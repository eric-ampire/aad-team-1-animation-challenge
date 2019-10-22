package com.andela.app.animationchallenge.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.andela.app.animationchallenge.R;
import com.andela.app.animationchallenge.fragment.LogoutDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements LogoutDialogFragment.LogoutDialogListener {

    private FirebaseAuth mAuth;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

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

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            ActionBar supportActionBar = getSupportActionBar();

            if (supportActionBar != null) {

                supportActionBar.setTitle(destination.getLabel());
            }
        });
    }


    //Adding a Menu to the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
              //TODO send to activity that the user uploads photo
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.rotate_scale_in);

                return true;
            case R.id.logout_menu:
                promptDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void promptDialog() {
        LogoutDialogFragment logoutDialogFragment=new LogoutDialogFragment();
        logoutDialogFragment.show(getSupportFragmentManager(),"logout");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        dialog.dismiss();
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.translate_left,R.anim.fade_out);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();

    }
}