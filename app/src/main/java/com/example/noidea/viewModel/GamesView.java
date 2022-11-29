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
import com.example.noidea.model.Games;
import java.util.List;

public class GamesView extends RecyclerView.Adapter<GamesView.ViewHolder> {

    public List<Games> GamesList;
    Context context;
    private final LayoutInflater mInflater;

    public GamesView(Context context , List<Games> GamesList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.GamesList = GamesList;
    }

    @NonNull
    @Override
    public GamesView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.item_game, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesView.ViewHolder holder, int position) {
        Games games = GamesList.get(position);

        String gameName = games.getName();
        holder.name.setText(gameName);

        String gameDate = games.getReleaseDate();
        holder.date.setText(gameDate);

        String itemUrl = games.getUrl();
        Glide.with(context).load(itemUrl).into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            
        });


    }

    @Override
    public int getItemCount() {
        return GamesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name;
        public TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.itemGameImage);
            this.name = (TextView) itemView.findViewById(R.id.itemGameName);
            this.date = (TextView) itemView.findViewById(R.id.itemGameDate);

        }
    }
}
