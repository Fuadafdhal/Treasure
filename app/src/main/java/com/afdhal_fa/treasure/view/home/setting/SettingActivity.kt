package com.afdhal_fa.treasure.view.home.setting

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.utils.setMarginStart
import com.afdhal_fa.treasure.databinding.ActivitySettingBinding

class SettingActivity : BaseToolbarActivity<SettingViewModel>() {

    private lateinit var binding: ActivitySettingBinding

    private lateinit var adapterSettingTop: SettingAdapter
    private lateinit var adapterSettingBottom: SettingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterSettingTop = SettingAdapter()
        adapterSettingBottom = SettingAdapter()

        with(binding.rvSettingTop) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = adapterSettingTop
        }

        with(binding.rvSettingBottom) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = adapterSettingBottom
        }

        viewmodel.getSettingTop().observe(this, {
            adapterSettingTop.setItem(it)
        })
        viewmodel.getSettingBottom().observe(this, {
            adapterSettingBottom.setItem(it)
        })

        initOnklikItemTopHandler()
        initOnklikItemBottomHandler()
    }

    private fun initOnklikItemTopHandler() {
        adapterSettingTop.onItemClick = {
            when (it.image) {
                R.drawable.ic_translate -> {
                    makeToast(it.title)
                }
                R.drawable.ic_notification_setting -> {
                    makeToast(it.title)
                }
            }
        }
    }

    private fun initOnklikItemBottomHandler() {
        adapterSettingBottom.onItemClick = {
            when (it.image) {
                R.drawable.ic_help -> {
                    makeToast(it.title)
                }
                R.drawable.ic_padlock -> {
                    makeToast(it.title)
                }
                R.drawable.ic_assignment -> {
                    makeToast(it.title)
                }
                R.drawable.ic_star -> {
                    makeToast(it.title)
                }
            }
        }
    }

    override fun setToolbar(): Toolbar {
        binding.appbarLayout.textTitleToolbar.text = getString(R.string.title_setting)
        binding.appbarLayout.textTitleToolbar.setMarginStart(72)
        return binding.appbarLayout.toolbar
    }

    override fun setToolbarButtonBack() = true


    override fun initViewModel() =
        ViewModelProvider(this)[SettingViewModel::class.java]
}