package com.example.noidea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.noidea.fragments.gameFragment;
import com.example.noidea.fragments.homeFragment;
import com.example.noidea.fragments.reviewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // bottom navigation tutorial
    // https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/

    reviewsFragment reviews_fragment = new reviewsFragment();
    homeFragment home_fragment = new homeFragment();
    gameFragment game_fragment = new gameFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.loginHome);
        Button register = (Button) findViewById(R.id.registerHome);

        login.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
        register.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,RegisterActivity.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
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