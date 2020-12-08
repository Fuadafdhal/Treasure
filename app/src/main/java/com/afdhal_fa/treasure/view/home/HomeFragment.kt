package com.afdhal_fa.treasure.view.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.utils.toRupiah
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentHomeBinding
import com.afdhal_fa.treasure.view.home.notification.NotificationActivity
import com.afdhal_fa.treasure.view.home.setting.SettingActivity
import com.afdhal_fa.treasure.view.login.LoginActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

class HomeFragment : BaseToolbarFragment<HomeViewModel>(), TabLayout.OnTabSelectedListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userid: String
    private lateinit var tipsAdapter: TipsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tipsAdapter = TipsAdapter()

        if (activity != null) {
            checkIfUserIsAuthenticated()
            setHasOptionsMenu(true)
            setTreasureTips()

            with(binding.rvTips) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = tipsAdapter
            }

            binding.tabLayoutDiagram.addOnTabSelectedListener(this)
            initBarChart()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_setting -> {
                startActivity(Intent(context, SettingActivity::class.java))
            }
            R.id.menu_notificationn -> {
                startActivity(Intent(context, NotificationActivity::class.java))
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getText(R.string.app_name)
        return binding.includeAppbar.toolbar
    }

    private fun checkIfUserIsAuthenticated() {
        viewmodel.checkIfUserIsAuthenticated().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isAuthenticated!!) {
                        userid = it.data.uid
                        setTreasureData()
                    }
                }
                is Resource.Error -> {
                    context?.makeToast("Error")
                    if (!it.data?.isAuthenticated!!) {
                        updateUI(it.data.isAuthenticated)
                    }
                }
            }
        })
    }

    private fun setTreasureData() {
        viewmodel.getTreasureUserData(userid).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        binding.textLevel.text =
                            if (it.data.level == 0) "-" else it.data.level.toString()
                        binding.textSaldo.text =
                            if (it.data.saldo == 0L) "-" else it.data.saldo.toInt().toRupiah()
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })
    }


    private fun setTreasureTips() {
        viewmodel.getTipsTreasure().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        tipsAdapter.setItem(it.data)
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })
    }

    private fun initBarChart() {
        binding.barChart.description.isEnabled = false
        binding.barChart.setPinchZoom(false)

        binding.barChart.setDrawBarShadow(false)
        binding.barChart.setDrawGridBackground(false)


        val xAxis: XAxis = binding.barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        binding.barChart.axisLeft.setDrawGridLines(false)
        binding.barChart.legend.isEnabled = false
        binding.barChart.animateXY(2000, 2000)


        val barSetMinggu = BarDataSet(dataValueMinguan(), "Minggu")
        barSetMinggu.color =
            ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
        barSetMinggu.setDrawValues(false)

        val barData = BarData()
        barData.addDataSet(barSetMinggu)

        val dataSetMinggu = ArrayList<IBarDataSet>()
        dataSetMinggu.add(barSetMinggu)

        val dataMinggu = BarData(dataSetMinggu)
        binding.barChart.data = dataMinggu
        binding.barChart.setFitBars(true)
        binding.barChart.invalidate()
    }

    private fun updateUI(boolean: Boolean) {
        if (boolean) {
            startActivity(Intent(this.context, LoginActivity::class.java))
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Timber.d(tab?.position.toString())
        binding.barChart.animateXY(2000, 2000)
        when (tab?.position) {
            0 -> {
                Timber.d("Tab Select name : ${tab.text}")
                context?.makeToast(tab.text.toString())

                val barSetMinggu = BarDataSet(dataValueMinguan(), "Minggu")
                barSetMinggu.color =
                    ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
                barSetMinggu.setDrawValues(false)

                val barData = BarData()
                barData.addDataSet(barSetMinggu)

                val dataSetMinggu = ArrayList<IBarDataSet>()
                dataSetMinggu.add(barSetMinggu)

                val dataMinggu = BarData(dataSetMinggu)
                binding.barChart.data = dataMinggu
                binding.barChart.setFitBars(true)
            }

            1 -> {
                Timber.d("Tab Select name : ${tab.text}")
                context?.makeToast(tab.text.toString())

                val barSetBulan = BarDataSet(dataValueBulan(), "Bulan")
                barSetBulan.color =
                    ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
                barSetBulan.setDrawValues(false)

                val barDataBulan = BarData()
                barDataBulan.addDataSet(barSetBulan)

                val dataSetBulan = ArrayList<IBarDataSet>()
                dataSetBulan.add(barSetBulan)

                val dataBulan = BarData(dataSetBulan)
                binding.barChart.data = dataBulan
                binding.barChart.setFitBars(true)
            }

            2 -> {
                Timber.d("Tab Select name : ${tab.text}")
                context?.makeToast(tab.text.toString())

                val barSetTahun = BarDataSet(dataValueTahun(), "Tahun")
                barSetTahun.color =
                    ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
                barSetTahun.setDrawValues(false)

                val barDataTahun = BarData()
                barDataTahun.addDataSet(barSetTahun)

                val dataSetTahun = ArrayList<IBarDataSet>()
                dataSetTahun.add(barSetTahun)

                val dataTahun = BarData(dataSetTahun)
                binding.barChart.data = dataTahun
                binding.barChart.setFitBars(true)

            }
        }

        if (binding.barChart.data != null && binding.barChart.data.dataSetCount > 0) {
            binding.barChart.data.notifyDataChanged()
            binding.barChart.notifyDataSetChanged()
        }

        binding.barChart.invalidate()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun dataValueMinguan(): ArrayList<BarEntry> {
        return ArrayList<BarEntry>().apply {
            add(BarEntry(0f, 1f))
            add(BarEntry(1f, 4f))
            add(BarEntry(2f, 6f))
            add(BarEntry(3f, 3f))
            add(BarEntry(4f, 9f))
            add(BarEntry(5f, 2f))
            add(BarEntry(6f, 8f))
        }
    }

    private fun dataValueBulan(): ArrayList<BarEntry> {
        return ArrayList<BarEntry>().apply {
            add(BarEntry(0f, 10f))
            add(BarEntry(1f, 4f))
            add(BarEntry(2f, 6f))
            add(BarEntry(3f, 3f))
            add(BarEntry(4f, 1f))
            add(BarEntry(5f, 2f))
            add(BarEntry(6f, 1f))
            add(BarEntry(7f, 7f))
            add(BarEntry(8f, 1f))
            add(BarEntry(9f, 7f))
            add(BarEntry(10f, 1f))
            add(BarEntry(11f, 11f))
        }
    }

    private fun dataValueTahun(): ArrayList<BarEntry> {
        return ArrayList<BarEntry>().apply {
            add(BarEntry(0f, 1f))
            add(BarEntry(1f, 4f))
            add(BarEntry(2f, 6f))
            add(BarEntry(6f, 3f))
            add(BarEntry(4f, 9f))
            add(BarEntry(5f, 2f))
            add(BarEntry(3f, 8f))
            add(BarEntry(7f, 7f))
        }
    }
}