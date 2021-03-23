package com.eld.besteld.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.eld.besteld.R
import com.eld.besteld.listener.DialogCallback
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.ivClose
import java.util.*


class CommonDialogs (private val listener: DialogCallback) : DialogFragment() {

    private lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        setListeners()
        setCallback(mContext)
    }

    fun setCallback(context: Context) {
        this.mContext = context

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

    private fun setListeners() {
        ivClose.setOnClickListener(this::onClick)
        ivDotInsp.setOnClickListener(this::onClick)
        ivHelp.setOnClickListener(this::onClick)
        ivLogbook.setOnClickListener(this::onClick)
        ivMessage.setOnClickListener(this::onClick)
        ivSOS.setOnClickListener(this::onClick)
        ivSetting.setOnClickListener(this::onClick)
        demo.setOnClickListener(this::onClick)
        test.setOnClickListener(this::onClick)
        radio.setOnClickListener(this::onClick)
    }

    fun onClick(view: View) {
        when (view) {
            ivClose -> listener.onClose()

            ivDotInsp -> listener.onDotInspect()

            ivHelp -> listener.onHelp()

            ivLogbook -> listener.onLogBook()

            ivMessage -> listener.onMessage()

            ivSOS -> listener.onSos()

            ivSetting -> listener.onSetting()

            demo -> listener.onDemo()

            test -> listener.onTest()

            radio -> listener.onRadio()
        }
        dismiss()
    }

}