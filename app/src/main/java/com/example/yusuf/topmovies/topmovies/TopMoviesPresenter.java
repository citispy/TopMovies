package com.example.yusuf.topmovies.topmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.birbit.android.jobqueue.JobManager;
import com.example.yusuf.topmovies.R;
import com.example.yusuf.topmovies.http.MovieApiService;
import com.example.yusuf.topmovies.http.apimodel.Result;
import com.example.yusuf.topmovies.http.apimodel.TopRated;
import com.example.yusuf.topmovies.jobs.GetMoviesJob;
import com.example.yusuf.topmovies.root.App;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;


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
    public void runLayoutAnimation(RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
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

        final App app = (App) view.getViewContext();

        app.jobManager.addJobInBackground(new GetMoviesJob(movieApiService, page));

//        disposable.add(movieApiService.getTopRatedMovies(page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(() -> {
//                    if (view != null){
//                        view.setLoading(false);
//                    }
//                })
//                .subscribe(topRated -> {
//                    if (view != null) {
//                        view.updateData(topRated.getResults());
//                    }
//                }, error -> {
//                    if (view != null) {
//                        view.showSnackBar(error.getLocalizedMessage());
//                    }
//                }));
    }

    @Override
    public void loadMovies(TopRated topRated) {
        view.updateData(topRated.getResults());
        view.setLoading(false);
    }
}
