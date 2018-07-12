package com.example.yusuf.topmovies.topmovies;


import com.example.yusuf.topmovies.http.apimodel.Result;

import java.util.List;

public interface TopMoviesActivityMVP {

    interface View {

        void updateData(List<Result> results);

        void showSnackBar(String string);

        void setLoading(Boolean isLoading);
    }

    interface Presenter {

        void loadData(int page);

        void detachView();

        void attachView(TopMoviesActivityMVP.View view);
    }
}
