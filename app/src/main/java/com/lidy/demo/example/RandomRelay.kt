package com.lidy.demo.example

import android.util.Log
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observer
import io.reactivex.disposables.Disposables

/**
 * 自定义的中转站
 */
class RandomRelay : Relay<Int>() {

    private var random = kotlin.random.Random

    private var observers: MutableList<Observer<in Int>> = mutableListOf()

    override fun accept(integer: Int) {
        val observerIndex = random.nextInt(observers.size) and Integer.MAX_VALUE
        observers[observerIndex].onNext(integer)
    }

    override fun hasObservers(): Boolean {
        return observers.isEmpty()
    }

    override fun subscribeActual(observer: Observer<in Int>) {
        observers.add(observer)
        observer.onSubscribe(Disposables.fromRunnable { Log.w("RandomRelay", "Disposed") })
    }

}
