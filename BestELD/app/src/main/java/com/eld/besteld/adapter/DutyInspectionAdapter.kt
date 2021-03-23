package com.eld.besteld.adapter

import  android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eld.besteld.R
import com.eld.besteld.roomDataBase.DayData
import com.eld.besteld.utils.CommonUtils
import com.eld.besteld.utils.TimeUtility
import kotlinx.android.synthetic.main.driver_information_recycler_layout.view.*
import kotlinx.android.synthetic.main.odometer_reading_row_layout.view.*

class DutyInspectionAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<DutyInspectionAdapter.ViewHolder>() {


    val dayData = ArrayList<DayData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DutyInspectionAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.driver_information_recycler_layout, parent, false)
        )
    }

    fun UpdateList(newList: List<DayData>) {
        dayData.clear()
        if (newList.count() > 0) {
            dayData.addAll(newList)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return dayData.size
    }

    override fun onBindViewHolder(holder: DutyInspectionAdapter.ViewHolder, position: Int) {

        holder.itemView.tvNotes.text = dayData.get(position).rideDesciption
        holder.itemView.tvLocation.text = "Invalid Location"// dayData.get(position).day
        if (CommonUtils.DEBUB_MODE == true) {
            holder.itemView.tvstarttime_?.text = TimeUtility.timeForDateString(dayData.get(position).startTime)//dayData.get(position).startTime
            holder.itemView.tvEndtime_?.text = TimeUtility.timeForDateString(dayData.get(position).endTime)//dayData.get(position).endTime
        }else {
            holder.itemView.tvstarttime_?.text = TimeUtility.timeForDateString(dayData.get(position).startTime)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}