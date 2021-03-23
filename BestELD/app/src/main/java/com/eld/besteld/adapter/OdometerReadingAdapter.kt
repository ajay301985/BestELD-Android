package com.eld.besteld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.eld.besteld.R
import kotlinx.android.synthetic.main.select_date_layout_dialog.*

class OdometerReadingAdapter(private val mContext : Context):

    RecyclerView.Adapter<OdometerReadingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OdometerReadingAdapter.ViewHolder {

        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.day_meta_data_recycler,parent,false))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: OdometerReadingAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }
}