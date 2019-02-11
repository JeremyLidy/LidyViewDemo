package com.lidy.demo;

import android.app.Application;
import androidx.lifecycle.ProcessLifecycleOwner;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author lideyou
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SampleLifecycleListener lifecycleListener = new SampleLifecycleListener();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleListener);
        /*
         * 设置全局的线程
         */
        RxAndroidPlugins.onMainThreadScheduler(AndroidSchedulers.from(getMainLooper(), true));
    }
}
