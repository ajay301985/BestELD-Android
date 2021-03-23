package com.eld.besteld.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eld.besteld.R
import com.eld.besteld.adapter.DutyInspectionAdapter
import com.eld.besteld.roomDataBase.*
import com.eld.besteld.utils.DataHandler
import kotlinx.android.synthetic.main.fragment_duty_inspection.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DutyInspectionFragment : Fragment() {
    private lateinit var mContext: Context
    lateinit var viewModel: DriverViewModel
    lateinit var logDataViewModel: LogDataViewModel
    private var dayData = ArrayList<DayData>()
    private var driverInformation: insertDriverInformationDao? = null
    private lateinit var driverInoAdapter: DutyInspectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        driverInoAdapter = DutyInspectionAdapter(mContext)
        viewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
        logDataViewModel = ViewModelProvider(this).get(LogDataViewModel::class.java)
        viewModel.dayDaya.observe(this, Observer {list
            -> list?.let {
            driverInoAdapter.UpdateList(it)

        }


        })
        driverInformation = EldDataBaseExicution.invoke(mContext)?.getDriverDao()
        //enterInitailEntery()
        //  fillingDriverStatusData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    private fun setRecycler() {
        rvDriverData.layoutManager = LinearLayoutManager(mContext)
        driverInoAdapter = DutyInspectionAdapter(mContext)
        rvDriverData.adapter = driverInoAdapter


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_duty_inspection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()

    }
}