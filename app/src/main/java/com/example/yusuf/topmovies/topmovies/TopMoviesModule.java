package com.example.yusuf.topmovies.topmovies;

import com.example.yusuf.topmovies.http.ApiModuleForName;
import com.example.yusuf.topmovies.http.MovieApiService;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModuleForName.class})
public class TopMoviesModule {

    @Provides
    public TopMoviesActivityMVP.Presenter provideTopMoviesActivityPresenter(MovieApiService movieApiService) {
        return new TopMoviesPresenter(movieApiService);
    }
}
