package com.lidy.demo;

import android.util.Log;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author lideyou
 */
public class SampleLifecycleListener implements LifecycleObserver {

    @OnLifecycleEvent(Event.ON_START)
    public void onMoveToForeground() {
        Log.d("SampleLifecycle", "Returning to foreground…");


    }


    @OnLifecycleEvent(Event.ON_STOP)
    public void onMoveToBackground(){
        Log.d("SampleLifecycle", "Returning to foreground…");
    }

}
