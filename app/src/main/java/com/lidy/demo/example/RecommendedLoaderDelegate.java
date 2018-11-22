package com.lidy.demo.example;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class RecommendedLoaderDelegate implements LifecycleObserver {

    private final @NonNull
    ExampleActivity aboutActivity;

    private RecommendedLoaderDelegate(@NonNull ExampleActivity aboutActivity) {
        this.aboutActivity = aboutActivity;
    }

    public static RecommendedLoaderDelegate attch(@NonNull ExampleActivity activity) {
        RecommendedLoaderDelegate delegate = new RecommendedLoaderDelegate(activity);
        activity.getLifecycle().addObserver(delegate);
        return delegate;
    }

    public void setTestA() {
        RecommendedLoader loader = RecommendedLoader.getInstance();
        loader.setTestA();
    }

    public void setTestB() {

    }


    @OnLifecycleEvent(Event.ON_DESTROY)
    public void onDestory() {

    }
}
