package com.example.demo.fragments

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.demo.R
import com.example.demo.ViewModel.cryptoViewModel
import com.example.demo.databinding.FragmentDetailsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.components.AxisBase
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: cryptoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        setupChartStyle()
        observeViewModel()

        viewModel.getChart("bitcoin", "usd", "7")
    }

    private fun setupChartStyle() {
        binding.lineChart.apply {
            setTouchEnabled(true)
            setPinchZoom(true)
            setBackgroundColor(Color.WHITE)
            setNoDataText("Loading chart...")
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.isEnabled = false
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
        }
    }

    private fun observeViewModel() {

        viewModel.chartData.observe(viewLifecycleOwner) { chartResponse ->
            chartResponse?.let { response ->

                val entries = ArrayList<Entry>()
                response.prices.forEachIndexed { index, price ->
                    entries.add(Entry(index.toFloat(), price[1].toFloat()))
                }

                // Setup X-axis formatter with timestamps
                val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
                binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        val index = value.toInt()
                        return if (index in response.prices.indices) {
                            val timestamp = response.prices[index][0].toLong()
                            sdf.format(Date(timestamp))
                        } else ""
                    }
                }

                drawChart(entries)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun drawChart(entries: List<Entry>) {
        val dataSet = LineDataSet(entries, "Price Chart").apply {
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
            color = Color.BLUE
        }

        val lineData = LineData(dataSet)
        binding.lineChart.apply {
            data = lineData
            animateX(1000)
            invalidate()
        }
    }
}