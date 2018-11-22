package com.lidy.demo.example;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author lideyou
 */
public class RecommendedLoader {

    private  Completable first;
    private  Completable second;
    private Completable error;
    private Throwable throwable = new RuntimeException();

    public static @NonNull
    RecommendedLoader getInstance() {
        return new RecommendedLoader();
    }

    public void setUpCompletables() {
        first = Completable.fromSingle(Single.just(1));
        second = Completable.fromRunnable(() -> {});
        error = Single.error(throwable)
                .ignoreElement();
    }

    public void setCompletedSuccessfully(){



    }

    public Observable<Integer> setTestA() {

        return Observable.just(1);
    }


}

