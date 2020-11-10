package com.afdhal_fa.treasure.core.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class VPagerAdapater(private val fragment: List<VPager>, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var dataVPager1: String? = null
    var dataVPager2: String? = null

    override fun getItem(position: Int): Fragment {
        val mBundle = Bundle()
        mBundle.putString(Constants.VPAGER1, getData1())
        mBundle.putString(Constants.VPAGER2, getData2())

        fragment[position].fragment.arguments = mBundle

        return fragment[position].fragment
    }

    override fun getPageTitle(position: Int): CharSequence? = fragment[position].title

    override fun getCount(): Int = fragment.size

    fun setData(data: String) {
        dataVPager1 = data
    }

    fun setData(dataVPager1: String, dataVPager2: String) {
        this.dataVPager1 = dataVPager1
        this.dataVPager2 = dataVPager2
    }

    fun getData1(): String? = dataVPager1

    fun getData2(): String? = dataVPager2

}