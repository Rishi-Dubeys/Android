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

public class newGamesView extends RecyclerView.Adapter<newGamesView.ViewHolder> {


    public List<newGames> newGamesList;
    Context context;
    private final LayoutInflater mInflater;


    public newGamesView(Context context , List<newGames> newGamesList){
        this.mInflater = LayoutInflater.from(context);
        this.newGamesList = newGamesList;
        this.context = context;
    }

    @NonNull
    @Override
    public newGamesView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newGamesView.ViewHolder holder, int position) {
        newGames newGames = newGamesList.get(position);

        String gameName = newGames.getName();
        holder.name.setText(gameName);

        String gameDate = newGames.getReleaseDate();
        holder.date.setText(gameDate);



        String itemUrl = newGames.getUrl();
        Glide.with(context).load(itemUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newGamesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name , date;
        ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.itemImage);
            this.name = itemView.findViewById(R.id.itemName);
            this.date = itemView.findViewById(R.id.itemDate);
        }
    }
}

