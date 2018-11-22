package com.lidy.demo.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxrelay2.BehaviorRelay
import com.lidy.demo.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.example_layout.*
import io.reactivex.observers.TestObserver
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.functions.Consumer
import com.jakewharton.rxrelay2.ReplayRelay
import io.reactivex.Observer
import io.reactivex.internal.schedulers.SingleScheduler
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import io.reactivex.disposables.Disposables
import com.lidy.demo.example.ExampleActivity.RandomRelay




/**
 * @author lideyou
 */
class ExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_layout)
        button.setOnClickListener {
            testA()
            testB()
        }
        RecommendedLoaderDelegate.attch(this)
    }

    private fun testA() {
        val subject = BehaviorRelay.createDefault("default")
        val disposable = subject.subscribe { s -> Log.d("ExampleActivity", s) }
        subject.accept("test 1")
        subject.accept("test 2")
        subject.accept("test 3")

    }

    private fun testB() {

        val publishRelay = PublishRelay.create<Int>()
        val firstObserver = TestObserver.create<Int>()
        val secondObserver = TestObserver.create<Int>()

        publishRelay.subscribe(firstObserver)
        firstObserver.assertSubscribed()
        publishRelay.accept(5)
        publishRelay.accept(10)
        publishRelay.subscribe(secondObserver)
        secondObserver.assertSubscribed()
        publishRelay.accept(15)
        firstObserver.assertValues(5, 10, 15)
        firstObserver.assertOf {
            Log.d("ExampleActivity", "firstObserver ${it.valueCount()}")
        }
        // second receives only the last event
        secondObserver.assertValue(15)

    }

    private fun testC() {

        val behaviorRelay = BehaviorRelay.create<Int>()
        val firstObserver = TestObserver.create<Int>()
        val secondObserver = TestObserver.create<Int>()
        behaviorRelay.accept(5)
        behaviorRelay.subscribe(firstObserver)
        behaviorRelay.accept(10)
        behaviorRelay.subscribe(secondObserver)
        behaviorRelay.accept(15)
        firstObserver.assertValues(5, 10, 15)
        secondObserver.assertValues(10, 15)
    }

    /**
     * 使用默认值
     */
    private fun testC1() {
        val behaviorRelay = BehaviorRelay.createDefault(1)
        val firstObserver = TestObserver<Int>()
        behaviorRelay.subscribe(firstObserver)
        firstObserver.assertValue(1)
    }

    /**
     * 没有默认值
     */
    private fun testC2() {
        val behaviorRelay = BehaviorRelay.create<Int>()
        val firstObserver = TestObserver<Int>()
        behaviorRelay.subscribe(firstObserver)
        firstObserver.assertEmpty()
    }

    /**
     * ReplayRelay 会把事件分发到所有的订阅者中
     * 所有事件会被缓存，然后订阅者将接受到所有事件
     */
    private fun replayRelayTestA() {
        val replayRelay = ReplayRelay.create<Int>()
        val firstObserver = TestObserver.create<Int>()
        val secondObserver = TestObserver.create<Int>()
        replayRelay.subscribe(firstObserver)
        replayRelay.accept(5)
        replayRelay.accept(10)
        replayRelay.accept(15)
        replayRelay.subscribe(secondObserver)
        firstObserver.assertValues(5, 10, 15)
        secondObserver.assertValues(5, 10, 15)
    }


    /**
     * 可以设置缓冲事件数
     */
    private fun replayRelayTestB() {
        /**
         * 最大缓冲数目是2，之前的会丢弃
         */
        val replayRelay = ReplayRelay.createWithSize<Int>(2)
        val firstObserver = TestObserver.create<Int>()
        replayRelay.accept(5)
        replayRelay.accept(10)
        replayRelay.accept(15)
        replayRelay.accept(20)
        replayRelay.subscribe(firstObserver)
        firstObserver.assertValues(15, 20)
    }

    /**
     * 可以设置最大缓冲时间保留当前的事件信息
     */
    private fun replayRelayTestC() {
        val scheduler = SingleScheduler()
        val replayRelay: ReplayRelay<Int> = ReplayRelay.createWithTime(2000L, TimeUnit.MILLISECONDS, scheduler)
        val current = scheduler.now(TimeUnit.MILLISECONDS)
        val firstObserver = TestObserver.create<Int>()
        replayRelay.accept(5)
        replayRelay.accept(10)
        replayRelay.accept(15)
        replayRelay.accept(20)
        Thread.sleep(3000)
        replayRelay.subscribe(firstObserver)
        firstObserver.assertEmpty()
    }


    /**
     * 定制一个自定义的 Relay 中转站
     */
    class RandomRelay : Relay<Int>() {

        var random = Random

        var observers: MutableList<Observer<in Int>> = mutableListOf()

        override fun accept(value: Int?) {
            val observerIndex = random.nextInt() % observers.size
            if (value != null) {
                observers[observerIndex].onNext(value)
            }
        }

        override fun subscribeActual(observer: Observer<in Int>) {
            observers.add(observer)
            observer.onSubscribe(Disposables.fromRunnable { println("Disposed") })
        }

        override fun hasObservers(): Boolean {

            return observers.isEmpty()
        }

    }

    private fun testD() {
        val randomRelay = RandomRelay()
        val firstObserver = TestObserver.create<Int>()
        val secondObserver = TestObserver.create<Int>()
        randomRelay.subscribe(firstObserver)
        randomRelay.subscribe(secondObserver)
        randomRelay.accept(5)
        if (firstObserver.values().isEmpty()) {
            secondObserver.assertValue(5)
        } else {
            firstObserver.assertValue(5)
            secondObserver.assertEmpty()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
