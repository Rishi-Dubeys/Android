package com.example.noidea.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.noidea.AddNewsActivity;
import com.example.noidea.LoginActivity;
import com.example.noidea.MainActivity;
import com.example.noidea.R;
import com.example.noidea.ReviewsActivity;
import com.example.noidea.addGamesActivity;
import com.example.noidea.model.Review;
import com.example.noidea.viewModel.reviewsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reviewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public reviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static reviewsFragment newInstance(String param1, String param2) {
        reviewsFragment fragment = new reviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    List<Review> reviewList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);


        recyclerView = view.findViewById(R.id.recyclerReview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewList.clear();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            retrieveData();
        } else {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

        return view;
    }

    private void retrieveData() {

        String userid = mAuth.getCurrentUser().getUid();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Reviews-User").child(userid);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Review review = dataSnapshot.getValue(Review.class);
                    reviewList.add(review);
                }
                // Use model to retrieve the data from Firebase Database


                // Assigning the View Adapter/Model to retrieve the list populated above
                reviewsView adapter = new reviewsView(getContext(), reviewList);
                // setting the adapter to the recycler view
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        dbRef.addValueEventListener(postListener);


    }
}