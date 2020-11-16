package com.afdhal_fa.treasure.view.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.databinding.FragmentAccountBinding
import com.afdhal_fa.treasure.view.account.edit_profile.EditProfileActivity
import com.afdhal_fa.treasure.view.account.edit_profile.EditProfileActivity.Companion.EXTRA_USER_DATA
import com.afdhal_fa.treasure.view.login.LoginActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.app_bar.view.*

class AccoutFragment : BaseToolbarFragment<AccountViewModel>() {
    lateinit var binding: FragmentAccountBinding
    lateinit var userid: String

    companion object {
        const val INTENT_EXTRA_RESULT = "extra_result_code_200"
        const val INTENT_CODE_REQUEST = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIfUserIsAuthenticated()

        binding.buttonLogout.setOnClickListener { logOut() }

    }

    private fun checkIfUserIsAuthenticated() {
        viewmodel.checkIfUserIsAuthenticated().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isAuthenticated!!) {
                        userid = it.data.uid
                        setUserData()
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

    private fun setUserData() {
        viewmodel.getUserData(userid).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        Glide.with(this)
                            .load(it.data.image)
                            .placeholder(R.drawable.image_placeholder)
                            .error(R.drawable.image_broken)
                            .into(binding.imageProfile)

                        binding.textName.text = if (it.data.name.equals("")) "-" else it.data.name
                        binding.textPhone.text =
                            if (it.data.phoneNumber.equals("")) "-" else it.data.phoneNumber
                        binding.textEmail.text =
                            if (it.data.email.equals("")) "-" else it.data.email
                        binding.textBirtday.text =
                            if (it.data.birtdayDate.equals("")) "-" else it.data.birtdayDate

                        binding.buttonEdit.visibility = View.VISIBLE

                        binding.buttonEdit.setOnClickListener { view ->
                            startActivityForResult(
                                Intent(this.context, EditProfileActivity::class.java).apply {
                                    putExtra(EXTRA_USER_DATA, it.data)
                                }, INTENT_CODE_REQUEST
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })
    }

    private fun setTreasureData() {
        viewmodel.getTreasureUserData(userid).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        binding.textLevelStatus.text =
                            if (it.data.level == 0) "-" else it.data.level.toString()
                        binding.textSaldoStatus.text =
                            if (it.data.saldo == 0L) "-" else it.data.saldo.toString()
                        binding.textTrashStatus.text =
                            if (it.data.totalTreash == 0) "-" else it.data.totalTreash.toString()
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })
    }

    private fun logOut() {
        viewmodel.logOutAuthenticated().observe(viewLifecycleOwner, {
            if (it is Resource.Success) {
                updateUI(it.data?.isAuthenticated!!)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INTENT_CODE_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getBooleanExtra(INTENT_EXTRA_RESULT, false)) {
                setUserData()
            }
        }
    }

    override fun initViewModel(): AccountViewModel {
        return ViewModelProvider(this)[AccountViewModel::class.java]
    }

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getString(R.string.title_account)
        return binding.includeAppbar.toolbar
    }

    private fun updateUI(boolean: Boolean) {
        if (boolean) {
            startActivity(Intent(this.context, LoginActivity::class.java))
        }
    }

}