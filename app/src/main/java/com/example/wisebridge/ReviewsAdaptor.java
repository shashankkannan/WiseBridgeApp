package com.example.wisebridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReviewsAdaptor extends RecyclerView.Adapter<ReviewsAdaptor.ReviewsViewHolder> {
    private List<Reviews> Reviewsz;
    private OnItemClickListener clickListener;


    public ReviewsAdaptor(List<Reviews> Reviewsz) {
        this.Reviewsz = Reviewsz;
    }

    // Click listener interface
    public interface OnItemClickListener {
        void onItemClick(Reviews review);
    }

    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }


    @NotNull
    @Override
    public ReviewsAdaptor.ReviewsViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item_layout, parent, false);
        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull ReviewsViewHolder holder, int position) {
        Reviews review  = Reviewsz.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return Reviewsz.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private TextView owner;
        private TextView rev;
        private TextView rate;

        public ReviewsViewHolder(@NotNull View itemView) {
            super(itemView);
            owner = itemView.findViewById(R.id.owner);
            rev = itemView.findViewById(R.id.rev);
            rate = itemView.findViewById(R.id.rate);

            // Set click listener for the item view

        }


        public void bind(Reviews review) {
            owner.setText(review.getOwner());
            rev.setText(review.getRev());
            rate.setText(review.getRate());

        }
    }

}

