package com.example.yusuf.topmovies.root;

import com.example.yusuf.topmovies.http.ApiModuleForName;
import com.example.yusuf.topmovies.topmovies.TopMoviesActivity;
import com.example.yusuf.topmovies.topmovies.TopMoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModuleForName.class, TopMoviesModule.class})
public interface ApplicationComponent {

    void inject(TopMoviesActivity target);
}
