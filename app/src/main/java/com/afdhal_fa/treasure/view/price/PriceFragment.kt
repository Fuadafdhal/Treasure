package com.afdhal_fa.treasure.view.price

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentPriceBinding
import kotlinx.android.synthetic.main.app_bar.view.*


class PriceFragment : BaseToolbarFragment<PriceViewModel>() {
    private lateinit var binding: FragmentPriceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvPrice) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        setPriceList()

    }

    private fun setPriceList() {
        viewmodel.viewPriceList().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        binding.rvPrice.adapter = PriceAdapter(it.data)
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })

    }


    override fun setToolbar(): Toolbar {
        binding.appbarLayout.textTitleToolbar.text = getString(R.string.title_price_trash)
        return binding.appbarLayout.toolbar
    }

    override fun initViewModel(): PriceViewModel {
        return ViewModelProvider(this)[PriceViewModel::class.java]
    }
}