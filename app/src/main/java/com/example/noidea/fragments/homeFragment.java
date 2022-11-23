package com.example.noidea.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.noidea.R;
import com.example.noidea.model.newGames;
import com.example.noidea.viewModel.newsGamesView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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

    public List<newGames> newGamesList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText searchbar = view.findViewById(R.id.search_bar);
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    search(editable.toString());
                }
                else {
                    search("");
                }

            }
        });

        // Assign recycler view with a linear layout
        recyclerView = view.findViewById(R.id.recyclerHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Clear List after changing page (Creates the same list everytime the page is created)
        newGamesList.clear();
        // Call the function to retrieve the data
        retrieveData();
        return view;
    }

    private void retrieveData() {
        // Initialize Firebase Database Connection
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("News");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Use model to retrieve the data from Firebase Database
                    newGames news = dataSnapshot.getValue(newGames.class);
                    newGamesList.add(news);
                }
                // Assigning the View Adapter/Model to retrieve the list populated above
                newsGamesView adapter = new newsGamesView(getContext(),newGamesList);
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


    // Soft Keyboard to stop move layout view
    // https://stackoverflow.com/questions/43115510/hiding-bottom-navigation-bar-whilst-keyboard-is-present-android
    // Search Bar Tutorial
    // https://www.youtube.com/watch?v=alJnPh4IZNo
    public void search(String s){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("News");

        Query query = dbRef.orderByChild("name").startAt(s).endAt(s + "u\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    newGamesList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        final newGames newGames = dataSnapshot.getValue(newGames.class);
                        newGamesList.add(newGames);
                    }
                }
                newsGamesView adapter = new newsGamesView(getContext(),newGamesList);
                // setting the adapter to the recycler view
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}