package com.example.yusuf.topmovies.topmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yusuf.topmovies.R;
import com.example.yusuf.topmovies.http.apimodel.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<Result> list;

    ListAdapter(List<Result> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        holder.rank.setText(String.valueOf(position + 1));
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + list.get(position).getPosterPath())
                .resize(200, 300)
                .centerCrop()
                .into(holder.imageView);

        String title = list.get(position).getTitle();
        String year = list.get(position).getReleaseDate().substring(0, 4);
        String itemText = title + " (" + year + ")";
        holder.itemName.setText(itemText);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<Result> newList) {
        //MyDiffUtil diffUtil = new MyDiffUtil(list, newList);
        //DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtil);
        list.addAll(newList);
        //diffResult.dispatchUpdatesTo(this);
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        TextView rank;
        ImageView imageView;
        TextView itemName;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            imageView = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.movieName);

        }
    }
}
