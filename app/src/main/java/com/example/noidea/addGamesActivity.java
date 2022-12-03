package com.example.noidea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noidea.model.Games;
import com.example.noidea.model.newGames;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class addGamesActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    private static final int SELECT_PICTURE = 200;
    EditText name,platform,date,publisher, description;
    Uri imageUri;
    ImageView imageView;
    Button selectImage;
    TextView addGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_games);

        date = findViewById(R.id.releaseGames);
        platform = findViewById(R.id.platformGames);
        name = findViewById(R.id.nameGames);
        publisher = findViewById(R.id.publisherGames);
        description = findViewById(R.id.descGames);

        selectImage = findViewById(R.id.selectImage);
        selectImage.setOnClickListener(view -> imageChooser());

        addGames = findViewById(R.id.addGame);
        addGames.setOnClickListener(view -> upload_image());

        imageView = findViewById(R.id.gameImages);
        imageView.setOnClickListener(view -> imageChooser());

        date.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(this,this, 2023, 0, 1);
            dialog.show();
        });
    }

    // Open Image folder in device and print image in the Image View
    // https://stackoverflow.com/questions/38352148/get-image-from-the-gallery-and-show-in-imageview
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
        String str_publisher = publisher.getText().toString().trim();
        String str_description = description.getText().toString().trim();

        // initialize Firebase Database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Games");

        // Adding Data to Firebase using the model
        Games games = new Games(str_name, str_platform, str_date, key ,url, str_publisher,str_description);
        mDatabase.child(key).setValue(games);
    }

    public void upload_image() {

        // initialize Firebase Database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Games");

        // Getting upload key and make it the ID of the table
        String key = mDatabase.child("Games").push().getKey();

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        if (imageUri != null) {
            // Adding image to Firebase Storage
            assert key != null;
            StorageReference storageRef = storage.getReference().child("Games").child(key);
            storageRef.putFile(imageUri).addOnCompleteListener(task -> {
                // Getting url from image upload
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                    // inserting url into Firebase Database
                    String url = uri.toString();
                    System.out.println(url);
                    writeFirebase(key,url);

                    // Changing activity after upload
                    startActivity(new Intent(addGamesActivity.this,RegisterActivity.class));
                });
            });
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        int mMonth = month + 1;
        String release_date = day + "/" + mMonth + '/' + year;
        date.setText(release_date);
    }
}