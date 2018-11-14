package com.lidy.demo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lidy.demo.animator.AnimatorActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.content_main.*



class MainActivity : AppCompatActivity() {

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        dashboard_button.setOnClickListener {
            val intent = Intent().setClass(this, DrawActivity::class.java)
            this.startActivity(intent)

//            setSimple()

        }

        animator_button.setOnClickListener {
            val intent = Intent().setClass(this, AnimatorActivity::class.java)
            this.startActivity(intent)
        }
    }


    private fun setSimple() {
        //
        RxAndroidPlugins.setMainThreadSchedulerHandler { AndroidSchedulers.from(mainLooper, true) }
        compositeDisposable.add(Observable.just("你好")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s -> println(s) })

        compositeDisposable.isDisposed
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
