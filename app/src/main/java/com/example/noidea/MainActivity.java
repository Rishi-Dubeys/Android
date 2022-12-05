package com.example.noidea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.noidea.fragments.gameFragment;
import com.example.noidea.fragments.homeFragment;
import com.example.noidea.fragments.reviewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // bottom navigation tutorial
    // https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/

    reviewsFragment reviews_fragment = new reviewsFragment();
    homeFragment home_fragment = new homeFragment();
    gameFragment game_fragment = new gameFragment();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    Button login,register,profile,add_game,add_new_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginHome);
        register = findViewById(R.id.registerHome);
        profile = findViewById(R.id.homeProfile);
        add_game = findViewById(R.id.addGameHome);
        add_new_game = findViewById(R.id.addNewGameHome);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            userValidation();
        }

        profile.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,profileActivity.class)) );
        login.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
        register.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,RegisterActivity.class)));
        add_game.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, addGamesActivity.class)));
        add_new_game.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddNewsActivity.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public void userValidation(){
        String userid = mAuth.getCurrentUser().getUid();

        login.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        profile.setVisibility(View.VISIBLE);


        String admin_id = "rvXjGBGHKIMLmegIDC7Zkbx3iTI3";

        if (userid.equals(admin_id)){
            add_game.setVisibility(View.VISIBLE);
            add_new_game.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            register.setVisibility(View.INVISIBLE);
            profile.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reviews:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, reviews_fragment).commit();
                return true;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, home_fragment).commit();
                return true;
            case R.id.reviews_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, game_fragment).commit();
                return true;
        }
        return false;

    }
}