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

     class  PageAdapter : BaseAdapter() {


         override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
             TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         }

         override fun getItem(position: Int): Any {
             TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         }

         override fun getItemId(position: Int): Long {
             TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         }

         override fun getCount(): Int {
             TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         }

     }
}
