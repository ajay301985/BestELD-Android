package com.eld.besteld.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eld.besteld.R
import com.eld.besteld.adapter.EldDeviceListAdapter
import com.eld.besteld.listener.EldDialogCallBack
import com.iosix.eldblelib.EldScanObject
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.eld_device_list_layout.*
import java.util.*

class EldListDialog(
    private val listener: EldDialogCallBack,
    eldDeviceList: MutableList<EldScanObject>
) : DialogFragment() {

    private lateinit var mContext: Context
    private var eldDeviceList = mutableListOf<EldScanObject>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.eld_device_list_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        setRecycler()
        setListeners()
        setCallback(mContext)
    }

    private fun setRecycler() {

        rvBlutoothList.layoutManager = LinearLayoutManager(mContext)
        val eldDeviceAdapter = EldDeviceListAdapter(mContext,eldDeviceList)
        rvBlutoothList.adapter = eldDeviceAdapter


    }

    fun setCallback(context: Context) {
        this.mContext = context
        this.eldDeviceList = eldDeviceList

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(Objects.requireNonNull(mContext))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.dialogAnimation;
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.window?.let {
            val layoutParams: WindowManager.LayoutParams =
                it.attributes
            it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            layoutParams.dimAmount = 0.0f
            layoutParams.gravity = Gravity.CENTER
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.attributes = layoutParams
        }
        return dialog
    }

    private fun setListeners() {

    }

    fun onClick(view: View) {
        when (view) {

        }
        dismiss()
    }

}