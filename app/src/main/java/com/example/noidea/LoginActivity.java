package com.example.noidea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText email, password;
    TextView login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.mail);
        password = findViewById(R.id.pswrdd);

        login = findViewById(R.id.sup);
        login.setOnClickListener(view -> signing());

        register = findViewById(R.id.lin);
        register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));
    }

    private void signing() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String str_email = email.getText().toString();
        String str_password = password.getText().toString();

        mAuth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Authentication Successful.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}