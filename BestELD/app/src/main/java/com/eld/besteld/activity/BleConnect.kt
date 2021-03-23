package com.eld.besteld.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eld.besteld.R
import com.iosix.eldblelib.*
import java.util.*

//import com.iosix.eldblelib.EldEn
//import com.iosix.eldblelib.EldEmissionsParametersRecord;
//import com.iosix.eldblelib.EldEngineParametersRecord;
//import com.iosix.eldblelib.EldTransmissionParametersRecord;
//import com.iosix.eldblelib.EldEn
//import com.iosix.eldblelib.EldEmissionsParametersRecord;
//import com.iosix.eldblelib.EldEngineParametersRecord;
//import com.iosix.eldblelib.EldTransmissionParametersRecord;
class BleConnect : AppCompatActivity(R.layout.activity_ble_connect) {
    var MAC: String? = null
    val context: Context = this
    private var updateSelection = 0

    private var mEldManager: EldManager? = null
    private var exit = false
    var reqdelinprogress = false
    var startseq = 0
    var endseq = 0
    var reccount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Required to allow bluetooth scanning
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            //working hereø
            ), 1
        )
        mEldManager = EldManager.GetEldManager(this, "123456789A")
        runOnUiThread {

        }
        ScanForEld()
    }


    override fun onBackPressed() {
        if (exit) {
            mEldManager!!.DisconnectEld()
            finish() // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3 * 1000.toLong())
        }
    }


    private val bleConnectionStateChangeCallback: EldBleConnectionStateChangeCallback =
        object : EldBleConnectionStateChangeCallback() {
            override fun onConnectionStateChange(newState: Int) {
                runOnUiThread {

                }
                if (newState == 0) {
                }
            }
        }
    private val bleDataCallback: EldBleDataCallback = object : EldBleDataCallback() {
        override fun OnDataRecord(
            dataRec: EldBroadcast,
            RecordType: EldBroadcastTypes
        ) {
            runOnUiThread {
                if (dataRec is EldBufferRecord) {

                    startseq = dataRec.startSeqNo
                    endseq = dataRec.endSeqNo
                } else if (RecordType == EldBroadcastTypes.ELD_DATA_RECORD) {

                } else if (RecordType == EldBroadcastTypes.ELD_CACHED_RECORD) {

                    if (reqdelinprogress) {
                        reccount++
                        Log.d("TESTING", "received $reccount records")
                        if (reccount == 10) {
                            Log.d(
                                "TESTING",
                                "delete " + startseq + "-" + (startseq + 9)
                            )
                            mEldManager!!.DeleteRecord(startseq, startseq + 9)
                            Log.d("TESTING", "request " + (startseq + 10))
                            mEldManager!!.RequestRecord(startseq + 10)
                        } else if (reccount == 11) {
                            Log.d("TESTING", "success!")
                            reqdelinprogress = false
                            reccount = 0
                        }
                    }
                    if (dataRec is EldCachedPeriodicRecord) {
                        Log.d(
                            "TESTING",
                            "Odometer " + dataRec.odometer
                        )
                        Log.d(
                            "TESTING",
                            "Engine Hours " + dataRec.engineHours
                        )
                        Log.d(
                            "TESTING",
                            "RPM " + dataRec.rpm
                        )
                        Log.d(
                            "TESTING",
                            "Satellites " + dataRec.numSats
                        )
                        Log.d(
                            "TESTING",
                            "Lat " + dataRec.latitude
                        )
                        Log.d(
                            "TESTING",
                            "Lon " + dataRec.longitude
                        )
                        Log.d(
                            "TESTING",
                            "Unix Time " + dataRec.unixTime
                        )
                        Log.d(
                            "TESTING",
                            "Sequence Number " + dataRec.seqNum
                        )

                        // mDataView.append("CACHED REC"+((EldCachedPeriodicRecord)(dataRec)).getBroadcastString());
                    } else if (dataRec is EldCachedNewTimeRecord) {
                        dataRec.engineHours
                        dataRec.newUnixTime
                    } else if (dataRec is EldCachedNewVinRecord) {
                        Log.d(
                            "TESTING",
                            "Vin " + dataRec.vin
                        )
                        Log.d(
                            "TESTING",
                            "Odometer " + dataRec.odometer
                        )
                        Log.d(
                            "TESTING",
                            "Engine Hours " + dataRec.engineHours
                        )
                        Log.d(
                            "TESTING",
                            "Unix Time " + dataRec.unixTime
                        )
                        Log.d(
                            "TESTING",
                            "Sequence Number " + dataRec.seqNum
                        )
                    }
                } else if (RecordType == EldBroadcastTypes.ELD_DRIVER_BEHAVIOR_RECORD) {
                    val rec = dataRec as EldDriverBehaviorRecord
                    if (rec is EldDriverBehaviorRecord) {
                        rec.absStatus

                    }
                } else if (RecordType == EldBroadcastTypes.ELD_ENGINE_PARAMETERS_RECORD) {

                } else if (RecordType == EldBroadcastTypes.ELD_EMISSIONS_PARAMETERS_RECORD) {

                } else if (RecordType == EldBroadcastTypes.ELD_TRANSMISSION_PARAMETERS_RECORD) {

                } else if (RecordType == EldBroadcastTypes.ELD_FUEL_RECORD) {
                    val rec = dataRec as EldFuelRecord

                }
            }
        }
    }
    private val bleScanCallback: EldBleScanCallback = object : EldBleScanCallback() {
        override fun onScanResult(device: EldScanObject) {
            Log.d("BLETEST", "BleScanCallback single")
            val strDevice: String
            if (device != null) {
                strDevice = device.deviceId
                runOnUiThread {  }
                val res = mEldManager!!.ConnectToEld(
                    bleDataCallback,
                    EnumSet.of(
                        EldBroadcastTypes.ELD_BUFFER_RECORD,
                        EldBroadcastTypes.ELD_CACHED_RECORD,
                        EldBroadcastTypes.ELD_DATA_RECORD
                    ),
                    bleConnectionStateChangeCallback
                )
                if (res != EldBleError.SUCCESS) {
                    runOnUiThread {  }
                }
            } else {
                runOnUiThread {  }
            }
        }

        override fun onScanResult(deviceList: ArrayList<EldScanObject>?) {
            Log.d("BLETEST", "BleScanCallback multiple")
            val strDevice: String
            val so: EldScanObject
            if (deviceList != null) {
                so = deviceList[0] as EldScanObject
                strDevice = so.deviceId
                MAC = strDevice
                runOnUiThread {  }
                val res = mEldManager!!.ConnectToEld(
                    bleDataCallback,
                    EnumSet.of(
                        EldBroadcastTypes.ELD_BUFFER_RECORD,
                        EldBroadcastTypes.ELD_CACHED_RECORD,
                        EldBroadcastTypes.ELD_FUEL_RECORD,
                        EldBroadcastTypes.ELD_DATA_RECORD,
                        EldBroadcastTypes.ELD_DRIVER_BEHAVIOR_RECORD,
                        EldBroadcastTypes.ELD_EMISSIONS_PARAMETERS_RECORD,
                        EldBroadcastTypes.ELD_ENGINE_PARAMETERS_RECORD,
                        EldBroadcastTypes.ELD_TRANSMISSION_PARAMETERS_RECORD
                    ),
                    bleConnectionStateChangeCallback,
                    strDevice
                )
                if (res != EldBleError.SUCCESS) {
                    runOnUiThread {

                        throw RuntimeException("Stub!")

                    }
                }
            } else {
                runOnUiThread { }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_BT_ENABLE) {
            // Make sure the request was successful

            //Working here
            if (resultCode == Activity.RESULT_OK) {
                mEldManager!!.ScanForElds(bleScanCallback)
            } else {
            }
        }
    }

    /*
    Scan for the ELD using EldBleLib  - if Bluetooth is not enabled it will return a NOT_ENABLED Error
    in which case invoke EnableBluetooth to enable bluetooth and in the custom intent on success
    invoke the scan functions
     */
    private fun ScanForEld() {
        if (mEldManager!!.ScanForElds(bleScanCallback) == EldBleError.BLUETOOTH_NOT_ENABLED) mEldManager!!.EnableBluetooth(
            REQUEST_BT_ENABLE
        )
    }

    companion object {
        private const val REQUEST_BASE = 100
        private const val REQUEST_BT_ENABLE = REQUEST_BASE + 1
    }
}

