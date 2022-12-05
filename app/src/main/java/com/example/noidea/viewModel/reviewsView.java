package com.example.noidea.viewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noidea.R;
import com.example.noidea.model.Games;
import com.example.noidea.model.Review;

import java.util.List;

public class reviewsView extends RecyclerView.Adapter<reviewsView.ViewHolder> {

    public List<Review> reviewList;
    Context context;
    private final LayoutInflater mInflater;

    public reviewsView(Context context , List<Review> reviewList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.reviewList = reviewList;
    }


    @NonNull
    @Override
    public reviewsView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.item_review, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewsView.ViewHolder holder, int position) {

        Review review = reviewList.get(position);

        String title = review.getTitle();
        holder.title.setText("Game: " + title);

        String date = review.getDate();
        holder.date.setText("Date: " + date);

        String user = review.getName();
        holder.user.setText("User: "+user);

        String rating = review.getRating();
        holder.rating.setText("Rate: " + rating);

        String desc = review.getReview();
        holder.review.setText(desc);


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, review,rating, date ,user ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.reviewTitle);
            this.review = itemView.findViewById(R.id.reviewDesc);
            this.rating = itemView.findViewById(R.id.reviewRate);
            this.date = itemView.findViewById(R.id.reviewDate);
            this.user = itemView.findViewById(R.id.reviewUser);

        }
    }
}
