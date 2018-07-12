package com.example.yusuf.topmovies.topmovies;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yusuf.topmovies.R;
import com.example.yusuf.topmovies.helper.MyDiffUtil;
import com.example.yusuf.topmovies.http.apimodel.Result;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<Result> list;

    public ListAdapter(List<Result> list) {
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
        holder.itemName.setText(title);
        Log.d("ListAdapter", "Poster path: " + list.get(position).getPosterPath());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<Result> newList) {
//        MyDiffUtil diffUtil = new MyDiffUtil(list, newList);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtil);
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
