package com.eld.besteld.paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val dataSet = mutableListOf<DataPoint>()
    private var xMin = 0
    private var xMax = 4
    private var yMin = 0
    private var yMax = 4
    private lateinit var currentDataPointObj: DataPoint

    private val dataPointLinePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 4f
        isAntiAlias = true
    }



    private val dataPointLinePaintGreen = Paint().apply {
        color = Color.GREEN
        strokeWidth = 4f
        isAntiAlias = true
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        //canvas.drawLine(100.0F, 100.0F, 200.0F, 100.0F, dataPointLinePaint)

        var test = false
        dataSet.forEachIndexed { index, currentDataPoint ->
            val startX = currentDataPoint.xStart.toRealX()
            val startY = currentDataPoint.yStart.toRealY()
            val endX = currentDataPoint.xEnd.toRealX()
            val endY = currentDataPoint.yEnd.toRealY()

            /*if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                val startY = currentDataPoint.yVal.toRealY()
                val endX = nextDataPoint.xVal.toRealX()
                val endY = nextDataPoint.yVal.toRealY()
                canvas.drawLine(100.0f, 500.0f, 100f, 500f, dataPointLinePaint)
                canvas.drawLine(200.0f, 500.0f, 200f, 200f, dataPointLinePaint)
            }*/
            if (test == true) {
                canvas.drawLine(currentDataPointObj.xEnd.toRealX(), currentDataPointObj.yEnd.toRealY(), startX, startY, dataPointLinePaintGreen)
            }
            canvas.drawLine(startX, startY, endX, endY, dataPointLinePaint)
            currentDataPointObj = currentDataPoint
            test = true
//            canvas.drawLine(200.0f, 200.0f, 200f, 200f, dataPointLinePaint)
//            canvas.drawLine(200.0f, 200.0f, 300f, 300f, dataPointLinePaint)
           /* canvas.drawCircle(realX, realY, 7f, dataPointFillPaint)
            canvas.drawCircle(realX, realY, 7f, dataPointPaint)*/
        }
    }

    fun setData(newDataSet: List<DataPoint>) {
//        xMin = newDataSet.maxBy { it.xVal }?.xVal ?: 0
//        xMax = newDataSet.maxBy { it.xVal }?.xVal ?: 0
//        yMin = newDataSet.minBy { it.yVal }?.yVal ?: 0
//        yMax = newDataSet.maxBy { it.yVal }?.yVal ?: 0
        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }

    private fun Int.toRealX() = toFloat()// / xMax * width
    private fun Int.toRealY() = toFloat()// / yMax * height
}

data class DataPoint(
    val xStart: Int,
    val yStart: Int,
    val xEnd: Int,
    val yEnd: Int
    )