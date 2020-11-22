package com.afdhal_fa.treasure.view.exchange.choose_nominal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.setMarginStart
import com.afdhal_fa.treasure.databinding.ActivityChoseeNominalBinding
import com.afdhal_fa.treasure.view.exchange.ExchangeFragment
import com.afdhal_fa.treasure.view.exchange.ExchangeFragment.Companion.INTENT_RESUTL_EXTRA_POSITION

class ChoseeNominalActivity : BaseToolbarActivity<NominalViewModel>() {

    private lateinit var binding: ActivityChoseeNominalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoseeNominalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nominalAdapter = NominalAdapter()

        viewmodel.getNominal().observe(this, {
            nominalAdapter.setItem(it)
            binding.rvNominal.adapter = nominalAdapter
        })

        nominalAdapter.onItemClick = { select ->
            val mIntent = Intent().apply {
                putExtra(ExchangeFragment.INTENT_RESUTL_EXTRA_NOMINAL, select)
                putExtra(
                    INTENT_RESUTL_EXTRA_POSITION,
                    intent.getIntExtra(INTENT_RESUTL_EXTRA_POSITION, 0)
                )
            }
            setResult(RESULT_OK, mIntent)
            finish()
        }

        with(binding.rvNominal) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getString(R.string.title_nominal)
        binding.includeAppbar.textTitleToolbar.setMarginStart(72)
        binding.includeAppbar.toolbar.setNavigationIcon(R.drawable.ic_close)
        return binding.includeAppbar.toolbar
    }

    override fun setToolbarButtonBack() = true

    override fun initViewModel() = ViewModelProvider(this)[NominalViewModel::class.java]

}