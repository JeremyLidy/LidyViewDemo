package com.lidy.demo.rx;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.single.SingleJust;
import io.reactivex.schedulers.Schedulers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Publisher;

/**
 * @author lideyou
 */
public class TestSimpleActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSimple();
        singleTask();
    }


    private void setSimple() {
        compositeDisposable.add(Observable.just("你好")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> System.out.println(s)));
    }

    private void singleTask() {

        Single<String> single = Single.just("1");
        single = single.subscribeOn(AndroidSchedulers.mainThread());
        single = single.observeOn(Schedulers.io());
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }

    private void delayTask() {

        Single.fromCallable(() -> "test")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    /**
     * map 也是数据转换
     */
    private void testMap() {
        Single<String> single = Single.just("12");
        single.map(Integer::valueOf)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(getIntegerObserver());
    }

    /**
     * compose Single<String> 转换为 String<Integer> 可以指定线程 Schedulers
     */
    private void testCompose() {

        Single<String> single = Single.just("1");
        single.compose(
                upstream -> upstream.map(s -> Integer.valueOf(s) + 1))
                .subscribe(getIntegerObserver());

    }


    private void testConcat() {

        int index = 0;
        Single.concat(Single.just(3), Single.just(5))
                .filter(integer -> integer > 2)
                .first(index)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    private SingleObserver<Integer> getIntegerObserver() {
        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private void setObserverTask() {

        List<String> lists = new ArrayList<>();
        lists.add("22");
        Observable<String> observable = Observable.fromIterable(lists);

    }

    private void observerTask() {

        Observable<String> observable = Observable.just("1");

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
