package com.eld.besteld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eld.besteld.R
import com.iosix.eldblelib.EldScanObject
import kotlinx.android.synthetic.main.ble_device_layout.view.*

class EldDeviceListAdapter(
    private val mContext: Context,
    private val list: MutableList<EldScanObject>

) : RecyclerView.Adapter<EldDeviceListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EldDeviceListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.ble_device_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: EldDeviceListAdapter.ViewHolder, position: Int) {
        val text = list[position]
        if (list.size>0){
            holder.bindItem(mContext,text)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView
        fun bindItem(mContext: Context, text: EldScanObject) {
            itemView.tvBletext.text = text.deviceId
        }
    }


}