package com.example.noidea;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noidea.model.newGames;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class AddNewsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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

        // Date Picker Code
        // https://stackoverflow.com/a/14933504/15880071

        date.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(this,this, 2023, 0, 1);
            dialog.show();
        });


        TextView add_games_btn = findViewById(R.id.add_game_btn);
        add_games_btn.setOnClickListener(view -> upload_image());

        imageView = findViewById(R.id.press_img);
        imageView.setOnClickListener(view -> imageChooser());
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

    // Write in Firebase
    // https://firebase.google.com/docs/database/android/start
    private void writeFirebase(String key , String url) {

        String str_name = name.getText().toString().trim();
        String str_platform = platform.getText().toString().trim();
        String str_date = date.getText().toString().trim();

        // initialize Firebase Database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("News");

        // Adding Data to Firebase using the model
        newGames newGame = new newGames(str_name, str_platform, str_date, key ,url);
        mDatabase.child(key).setValue(newGame);
    }

    // Upload Files to Database and get url from the upload
    // https://firebase.google.com/docs/storage/android/upload-files
    public void upload_image() {

        // initialize Firebase Database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("News");

        // Getting upload key and make it the ID of the table
        String key = mDatabase.child("News").push().getKey();

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        if (imageUri != null) {
            // Adding image to Firebase Storage
            assert key != null;
            StorageReference storageRef = storage.getReference().child("News").child(key);
            storageRef.putFile(imageUri).addOnCompleteListener(task -> {
                // Getting url from image upload
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                    // inserting url into Firebase Database
                    String url = uri.toString();
                    System.out.println(url);
                    writeFirebase(key,url);

                    // Changing activity after upload
                    startActivity(new Intent(AddNewsActivity.this,RegisterActivity.class));
                });
            });
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        int mMonth = month + 1;
        date.setText(day + "/" + mMonth + '/' + year);
    }
}