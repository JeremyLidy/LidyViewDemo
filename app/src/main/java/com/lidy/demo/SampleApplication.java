package com.lidy.demo;

import android.app.Application;
import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * @author lideyou
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SampleLifecycleListener lifecycleListener = new SampleLifecycleListener();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleListener);

    }
}
