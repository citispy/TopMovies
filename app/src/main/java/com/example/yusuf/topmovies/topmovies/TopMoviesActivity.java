package com.example.yusuf.topmovies.topmovies;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.yusuf.topmovies.R;
import com.example.yusuf.topmovies.http.apimodel.Result;
import com.example.yusuf.topmovies.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class TopMoviesActivity extends AppCompatActivity implements TopMoviesActivityMVP.View {

    private final String TAG = TopMoviesActivity.class.getSimpleName();
    private int page = 1;

    private boolean isRecyclerViewLoading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rootView)
    ViewGroup rootView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    TopMoviesActivityMVP.Presenter presenter;

    private ListAdapter listAdapter;

    private List<Result> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movies);

        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            this.finish();
            Intent intent = new Intent(TopMoviesActivity.this, TopMoviesActivity.class);
            startActivity(intent);
        });

        listAdapter = new ListAdapter(resultList);
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(listAdapter);
        slideInBottomAnimationAdapter.setDuration(1000);
        slideInBottomAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(slideInBottomAnimationAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(TopMoviesActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);

        //Infinite scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (isRecyclerViewLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            isRecyclerViewLoading = false;
                            page = page + 1;
                            presenter.loadData(page);
                        }
                    }
                }
            }
        });

        presenter.attachView(this);
        presenter.loadData(page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();

    }

    @Override
    public void updateData(List<Result> results) {
        int itemCount = listAdapter.getItemCount();
        listAdapter.updateData(results);
        listAdapter.notifyItemInserted(itemCount);
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setLoading(Boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
        if (!isLoading && !isRecyclerViewLoading) {
            isRecyclerViewLoading = true;
        }
    }
}
