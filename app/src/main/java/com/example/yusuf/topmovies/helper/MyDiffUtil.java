package com.example.yusuf.topmovies.helper;

import android.support.v7.util.DiffUtil;

import com.example.yusuf.topmovies.http.apimodel.Result;

import java.util.List;

public class MyDiffUtil extends DiffUtil.Callback {

    private List<Result> oldList;
    private List<Result> newList;

    public MyDiffUtil(List<Result> oldList, List<Result> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
