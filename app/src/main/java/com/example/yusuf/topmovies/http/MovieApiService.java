package com.example.yusuf.topmovies.http;

import com.example.yusuf.topmovies.http.apimodel.TopRated;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("top_rated")
    Call<TopRated> getTopRatedMovies(@Query("page") Integer page);
}
