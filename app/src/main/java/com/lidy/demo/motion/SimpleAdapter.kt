package com.lidy.demo.motion

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class  SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int = 50

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
