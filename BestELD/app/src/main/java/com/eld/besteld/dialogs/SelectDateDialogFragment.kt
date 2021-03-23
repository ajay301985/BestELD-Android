package com.eld.besteld.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eld.besteld.R
import com.eld.besteld.adapter.DayDataListAdapter
import kotlinx.android.synthetic.main.select_date_layout_dialog.*
import java.util.*

class SelectDateDialogFragment : DialogFragment(){

    lateinit var mContext : Context
    lateinit var dayDataListAdapter: DayDataListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_date_layout_dialog,container,false)
    }

    override fun onStart() {
        super.onStart()
        settingCallBack(mContext)
        settingDayListAdapter()
    }

    private fun settingDayListAdapter() {

        rvdayRecycelr.layoutManager = LinearLayoutManager(mContext)
        val dayDataListAdapter = DayDataListAdapter(mContext)
        rvdayRecycelr.adapter = dayDataListAdapter


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
            layoutParams.gravity = Gravity.BOTTOM
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.attributes = layoutParams
        }
        return dialog
    }
    fun settingCallBack(context: Context) {
        this.mContext = context
    }
}