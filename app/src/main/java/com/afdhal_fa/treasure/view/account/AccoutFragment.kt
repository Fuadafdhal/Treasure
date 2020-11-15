package com.afdhal_fa.treasure.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.databinding.FragmentAccountBinding
import kotlinx.android.synthetic.main.app_bar.view.*

class AccoutFragment : BaseToolbarFragment<AccountViewModel>() {
    lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel


    }

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getString(R.string.title_account)
        return binding.includeAppbar.toolbar
    }

    override fun initViewModel(): AccountViewModel {
        return ViewModelProvider(this)[AccountViewModel::class.java]
    }
}