package com.eld.besteld.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eld.besteld.R
import com.eld.besteld.paint.DataPoint
import kotlinx.android.synthetic.main.fragment_graph.*


class GraphFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        graph.setData(generateRandomDataPoints())

        /* drawView = DrawView(context);
         drawView!!.setBackgroundColor(Color.WHITE);
 */
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context

    }
    private fun generateRandomDataPoints(): List<DataPoint> {
      /*  val random = Random()
        return (0..1).map {
            DataPoint(it, random.nextInt(2) + 4)
        }*/
        val firstPoint = DataPoint(0,200, 160,200)
        val firstPoint2 = DataPoint(160,300, 300,300)
        val firstPoint3 = DataPoint(300,400, 400,400)
        val firstPoint4 = DataPoint(400,500, 500,500)
        return listOf(firstPoint,firstPoint2,firstPoint3,firstPoint4)

    }
}


/*
        case .ONDUTY, .YARD: //4
          yPosition = 165
        case .OFFDUTY, .PERSONAL: // 1
          yPosition = 61
        case .SLEEPER:  //2
          yPosition = 100
        case .DRIVING:  //3
          yPosition = 135
 */

/*
        case .ONDUTY, .YARD: //4
          yPosition = 165
        case .OFFDUTY, .PERSONAL: // 1
          yPosition = 61
        case .SLEEPER:  //2
          yPosition = 100
        case .DRIVING:  //3
          yPosition = 135
 */

