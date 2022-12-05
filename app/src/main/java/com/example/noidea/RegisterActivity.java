package com.example.noidea;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.noidea.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {


    EditText email , password , name , address;
    TextView signup,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.mail);
        password = findViewById(R.id.pswrdd);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);

        signup = findViewById(R.id.sup);
        signup.setOnClickListener(view -> registerUser());

        login = findViewById(R.id.lin);
        login.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this,LoginActivity.class)));


    }
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private void registerUser() {

        String str_email = email.getText().toString();
        String str_password = password.getText().toString();

        mAuth.createUserWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            // add data to firebase
                            writeFirebase();
                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }

    private void writeFirebase() {

        String str_email = email.getText().toString();
        String str_password = password.getText().toString();
        String str_name = name.getText().toString();
        String str_address = address.getText().toString();
        String userid = mAuth.getCurrentUser().getUid();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User").child(userid);

        // add data using the model
        User user = new User(str_name,str_email,str_password,str_address,userid);
        myRef.setValue(user);

    }



}