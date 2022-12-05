package com.example.noidea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.noidea.model.Games;
import com.example.noidea.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity {


    TextView name, email , address;
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        address = findViewById(R.id.profileAddress);
        signOut = findViewById(R.id.signout);

        signOut.setOnClickListener(view -> logoutAuth());

        retrieveData();
    }

    private void logoutAuth() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(profileActivity.this,MainActivity.class));
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private void retrieveData() {

        String userid = mAuth.getCurrentUser().getUid();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("User").child(userid);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    String userName = user.getName();
                    String userAddress = user.getAddress();
                    String userEmail = user.getEmail();

                    name.setText(userName);
                    email.setText(userEmail);
                    address.setText(userAddress);

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}