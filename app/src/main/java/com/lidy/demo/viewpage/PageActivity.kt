package com.lidy.demo.viewpage

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.lidy.demo.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.activity_page_layout.*

class PageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_layout)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recycler_view.layoutManager =  LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        PagerSnapHelper().attachToRecyclerView(recycler_view)

    }

}
