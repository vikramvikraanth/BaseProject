package com.basecode.app.commonClass.abstractClass

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>constructor(val dataList: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateViewHolderBase(parent, viewType)
    }
    abstract fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        this.onBindViewHolderBase(holder, position)
    }
    abstract fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int)
    override fun getItemCount(): Int = if ( dataList.size > 0) dataList.size else 10
    operator fun get(position: Int): T {
        return dataList[position]
    }
}

