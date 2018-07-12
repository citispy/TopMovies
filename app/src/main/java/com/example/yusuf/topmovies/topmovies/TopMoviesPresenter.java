package com.example.yusuf.topmovies.topmovies;


import android.util.Log;

import com.example.yusuf.topmovies.http.MovieApiService;
import com.example.yusuf.topmovies.http.apimodel.TopRated;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class TopMoviesPresenter implements TopMoviesActivityMVP.Presenter {

    private TopMoviesActivityMVP.View view;
    private CompositeDisposable disposable;
    private MovieApiService movieApiService;

    public TopMoviesPresenter (MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
        disposable = new CompositeDisposable();
    }

    @Override
    public void attachView(TopMoviesActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        disposable.clear();
    }

    @Override
    public void loadData(int page) {
        if (view != null) {
            view.setLoading(true);
        }

        disposable.add(movieApiService.getTopRatedMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    if (view != null){
                        view.setLoading(false);
                    }
                })
                .subscribe(topRated -> {
                    if (view != null) {
                        view.updateData(topRated.getResults());
                    }
                }, error -> {
                    if (view != null) {
                        view.showSnackBar(error.getLocalizedMessage());
                    }
                }));
    }
}
