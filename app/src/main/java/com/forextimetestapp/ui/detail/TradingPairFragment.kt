package com.forextimetestapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.forextimetestapp.R
import com.forextimetestapp.model.TradingPair
import com.forextimetestapp.ui.base.BaseFragment
import com.forextimetestapp.utils.KEY_TRADING_PAIR
import com.forextimetestapp.utils.RxBus
import com.forextimetestapp.utils.WEBSOCKET_URL
import com.forextimetestapp.widget.CustomMarker
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_trading_pair.*
import okhttp3.*


class TradingPairFragment : BaseFragment() {

    private var ws: WebSocket? = null

    private lateinit var tradingPair: TradingPair

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trading_pair, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tradingPair = arguments!!.getParcelable(KEY_TRADING_PAIR)!!

        setOnClickListener()
        setData()
        setChart()
    }

    override fun onPause() {
        super.onPause()

        ws?.cancel()
    }

    private fun setOnClickListener() {
        ib_toolbar_back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setData() {
        tv_toolbar_title.text = tradingPair.title
    }

    private fun setChart() {
        val entries = ArrayList<Entry>()
        val vl = LineDataSet(entries, "Test")

        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.gray
        vl.fillAlpha = R.color.red

        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.data = LineData(vl)
        lineChart.axisRight.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.animateX(800, Easing.EaseInExpo)

        val markerView = CustomMarker(activity!!, R.layout.marker_view)
        lineChart.marker = markerView

        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()

        val request = Request.Builder()
            .url(WEBSOCKET_URL)
            .build()

        ws = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("TAG_OPEN", "opened")
                webSocket.send(
                    "{ \n" +
                            "  \"event\": \"subscribe\", \n" +
                            "  \"channel\": \"ticker\", \n" +
                            "  \"symbol\": \"${tradingPair.code}\"\n" +
                            "}"
                )
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                try {

                    val messageModel = Gson().fromJson<ArrayList<Any>>(text, ArrayList::class.java)
                    RxBus.getInstance().send(messageModel)

                    Log.d("TAG_ARR_4", (messageModel[1] as ArrayList<Float>)[0].toString())

                    val entryY = (messageModel[1] as ArrayList<Float>)[0]

                    activity!!.runOnUiThread {
                        kotlin.run {
                            val data = lineChart.getData()

                            if (data != null) {

                                data.addEntry(
                                    Entry(
                                        entries.size.toFloat() + 1.0f,
                                        entryY
                                    ), 0
                                )
                                data.notifyDataChanged()

                                lineChart.notifyDataSetChanged()
                                lineChart.setVisibleXRangeMaximum(120f)
                                lineChart.moveViewToX(data.entryCount.toFloat())
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
}
