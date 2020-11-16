package com.afdhal_fa.treasure.view.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentHomeBinding
import com.afdhal_fa.treasure.view.home.notification.NotificationActivity
import com.afdhal_fa.treasure.view.home.setting.SettingActivity
import com.afdhal_fa.treasure.view.login.LoginActivity
import kotlinx.android.synthetic.main.app_bar.view.*

class HomeFragment : BaseToolbarFragment<HomeViewModel>() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userid: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIfUserIsAuthenticated()
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_setting -> {
                startActivity(Intent(context, NotificationActivity::class.java))
            }
            R.id.menu_notificationn -> {
                startActivity(Intent(context, SettingActivity::class.java))
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
                            if (it.data.saldo == 0L) "-" else it.data.saldo.toString()
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })
    }

    private fun updateUI(boolean: Boolean) {
        if (boolean) {
            startActivity(Intent(this.context, LoginActivity::class.java))
        }
    }
}