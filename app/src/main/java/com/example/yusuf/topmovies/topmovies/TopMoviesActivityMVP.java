package com.example.yusuf.topmovies.topmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.yusuf.topmovies.http.apimodel.Result;
import com.example.yusuf.topmovies.http.apimodel.TopRated;

import java.util.List;

public interface TopMoviesActivityMVP {

    interface View {

        void updateData(List<Result> results);

        void showSnackBar(String string);

        void setLoading(Boolean isLoading);

        Context getViewContext();
    }

    interface Presenter {

        void loadData(int page);

        void detachView();

        void attachView(TopMoviesActivityMVP.View view);

        void runLayoutAnimation(final RecyclerView recyclerView);

        void loadMovies(TopRated topRated);
    }
}
