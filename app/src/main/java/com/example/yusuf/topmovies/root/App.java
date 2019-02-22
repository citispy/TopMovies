package com.example.yusuf.topmovies.root;

import android.app.Application;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;

public class App extends Application {

    private ApplicationComponent component;
    public JobManager jobManager;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        Configuration.Builder builder = new Configuration.Builder(this);
        jobManager = new JobManager(builder.build());
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
