package com.eld.besteld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eld.besteld.R

class DayDataListAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<DayDataListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayDataListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.day_meta_data_recycler, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: DayDataListAdapter.ViewHolder, position: Int) {
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}