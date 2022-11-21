package com.example.noidea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.noidea.model.newGames;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewsActivity extends AppCompatActivity {

    EditText name,platform,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        name = findViewById(R.id.newName);
        platform = findViewById(R.id.newPlatform);
        date = findViewById(R.id.newRelease);

        TextView add_games_btn = findViewById(R.id.add_game_btn);
        add_games_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFirebase();
            }
        });
    }

    private void writeFirebase() {
        String str_name = name.getText().toString().trim();
        String str_platform = platform.getText().toString().trim();
        String str_date = date.getText().toString().trim();

        String url = "https://firebasestorage.googleapis.com/v0/b/noidea-d495a.appspot.com/o/godofwar.jpeg?alt=media&token=218c7bc7-a27d-44ee-aeeb-b3f54ec72cea";

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("News");
        String key = mDatabase.child("News").push().getKey();

        newGames newGame = new newGames(str_name,str_platform,str_date,url, key );
        mDatabase.child(key).setValue(newGame);
    }


}