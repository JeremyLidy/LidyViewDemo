package com.lidy.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lidy.demo.animator.AnimatorActivity
import com.lidy.demo.coordinator.CoordinatorActivity
import com.lidy.demo.drag.DragActivity
import com.lidy.demo.viewpage.PageListActivity
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
        }

        animator_button.setOnClickListener {
            val intent = Intent().setClass(this, AnimatorActivity::class.java)
            this.startActivity(intent)
        }

        float_action_button.setOnClickListener {
            val intent = Intent().setClass(this, CoordinatorActivity::class.java)
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
