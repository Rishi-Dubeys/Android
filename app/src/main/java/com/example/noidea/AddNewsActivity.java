package com.example.noidea;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noidea.model.newGames;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;


public class AddNewsActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 200;
    EditText name, platform, date;
    ImageView imageView;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        name = findViewById(R.id.newName);
        platform = findViewById(R.id.newPlatform);
        date = findViewById(R.id.newRelease);
        imageView = findViewById(R.id.press_img);


        TextView add_games_btn = findViewById(R.id.add_game_btn);
        add_games_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_image();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

    }


    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


    private void writeFirebase(String key , String url) {

        String str_name = name.getText().toString().trim();
        String str_platform = platform.getText().toString().trim();
        String str_date = date.getText().toString().trim();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("News");

        newGames newGame = new newGames(str_name, str_platform, str_date, key ,url);
        mDatabase.child(key).setValue(newGame);
    }

    public void upload_image() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("News");
        String key = mDatabase.child("News").push().getKey();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        if (imageUri != null) {
            StorageReference storageRef = storage.getReference().child("News").child(key);

            storageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            System.out.println(url);
                            // adding url to Realtime Database
                            writeFirebase(key,url);
                            startActivity(new Intent(AddNewsActivity.this,RegisterActivity.class));

                        }
                    });
                }
            });
        }
    }
}