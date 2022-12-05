package com.example.noidea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.noidea.model.Review;
import com.example.noidea.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReviewsActivity extends AppCompatActivity {

    TextView add_review,title;
    EditText desc,username ;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        title = findViewById(R.id.titleReviews);
        desc = findViewById(R.id.descReview);
        ratingBar = findViewById(R.id.ratingBar);
        username = findViewById(R.id.userReview);
        add_review = findViewById(R.id.sup);
        add_review.setOnClickListener(view -> writeFirebase());

        Intent intent = getIntent();
        String str_title = intent.getStringExtra("game");
        title.setText(str_title);

        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        getUsername();

    }

    private void getUsername() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myUsername = FirebaseDatabase.getInstance().getReference("User").child(userId);

        myUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                String name = user.getName();
                username.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void writeFirebase() {

        String str_desc = desc.getText().toString();
        String str_rating = String.valueOf(ratingBar.getRating());
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        String str_title = intent.getStringExtra("game");
        String str_gameid = intent.getStringExtra("gameID");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());
        
        String name = username.getText().toString();


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Reviews");
        DatabaseReference myRefUser = FirebaseDatabase.getInstance().getReference("Reviews-User");
        DatabaseReference myRefGame = FirebaseDatabase.getInstance().getReference("Reviews-Game");


        String key = myRef.child("Games").push().getKey();


        Review review = new Review(str_title,str_desc,str_rating, currentDate , key , userId , name ,str_gameid );

        myRef.child(key).setValue(review);
        myRefUser.child(userId).child(key).setValue(review);
        myRefGame.child(str_gameid).child(key).setValue(review);

        startActivity(new Intent(this, MainActivity.class));


    }


}