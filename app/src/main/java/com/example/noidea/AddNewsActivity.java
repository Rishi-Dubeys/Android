package com.example.noidea;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.noidea.model.newGames;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class AddNewsActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 200;
    EditText name,platform,date;
    ImageView imageView;



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
                writeFirebase();
            }
        });

        Button loadImage = findViewById(R.id.loadImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void writeFirebase() {
        String str_name = name.getText().toString().trim();
        String str_platform = platform.getText().toString().trim();
        String str_date = date.getText().toString().trim();


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("News");
        String key = mDatabase.child("News").push().getKey();

        String url = "dsfkjosj";

        newGames newGame = new newGames(str_name,str_platform,str_date,url, key );
        mDatabase.child(key).setValue(newGame);

    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE){
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    void uploadImage(){

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference().child("News");

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }




}