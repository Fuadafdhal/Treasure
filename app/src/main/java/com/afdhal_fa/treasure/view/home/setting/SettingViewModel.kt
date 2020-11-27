package com.afdhal_fa.treasure.view.home.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.R

class SettingViewModel : ViewModel() {

    fun getSettingTop(): LiveData<MutableList<Setting>> =
        MutableLiveData<MutableList<Setting>>().apply {
            val dataArray = ArrayList<Setting>()

            dataArray.add(Setting("Ganti Bahasa", R.drawable.ic_translate))
            dataArray.add(Setting("Notifikasi", R.drawable.ic_notification_setting))

            postValue(dataArray)
        }

    fun getSettingBottom(): LiveData<MutableList<Setting>> =
        MutableLiveData<MutableList<Setting>>().apply {
            val dataArray = ArrayList<Setting>()


            dataArray.add(Setting("Bantuan", R.drawable.ic_help))
            dataArray.add(Setting("Kebijakan Privasi", R.drawable.ic_padlock))
            dataArray.add(Setting("Ketentuan Layanan", R.drawable.ic_assignment))
            dataArray.add(Setting("Beri Kami Nilai", R.drawable.ic_star))

            postValue(dataArray)
        }

}
