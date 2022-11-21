package com.example.noidea.viewModel;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noidea.R;
import com.example.noidea.model.newGames;

import java.util.List;

public class newsGamesView extends RecyclerView.Adapter<newsGamesView.ViewHolder> {


    public List<newGames> newGamesList;
    Context context;
    private LayoutInflater mInflater;


    public newsGamesView(Context context , List<newGames> newGamesList){
        this.mInflater = LayoutInflater.from(context);
        this.newGamesList = newGamesList;
        this.context = context;
    }

    @NonNull
    @Override
    public newsGamesView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.item_news, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newsGamesView.ViewHolder holder, int position) {
        newGames newGames = newGamesList.get(position);

        String gameName = newGames.getName();
        holder.textView.setText(gameName);

        String itemUrl = newGames.getUrl();
        Glide.with(context).load(itemUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newGamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.itemImage);
            this.textView = (TextView) itemView.findViewById(R.id.itemName);
        }
    }
}

