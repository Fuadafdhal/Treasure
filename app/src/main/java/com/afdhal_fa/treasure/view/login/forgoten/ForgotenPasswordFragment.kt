package com.afdhal_fa.treasure.view.login.forgoten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.databinding.FragmentForgotenPasswordBinding


class ForgotenPasswordFragment : BaseFragment<ForgotenViewModel>() {
    private var _binding: FragmentForgotenPasswordBinding? = null
    private val binding: FragmentForgotenPasswordBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotenPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun initViewModel(): ForgotenViewModel {
        return ViewModelProvider(this)[ForgotenViewModel::class.java]
    }
}