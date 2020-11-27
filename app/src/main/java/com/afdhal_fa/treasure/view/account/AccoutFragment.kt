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
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentAccountBinding
import com.afdhal_fa.treasure.view.account.edit_profile.EditProfileActivity
import com.afdhal_fa.treasure.view.account.edit_profile.EditProfileActivity.Companion.EXTRA_USER_DATA
import com.afdhal_fa.treasure.view.login.LoginActivity
import com.bumptech.glide.Glide

class AccoutFragment : BaseToolbarFragment<AccountViewModel>() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var userid: String

    companion object {
        const val INTENT_EXTRA_RESULT = "extra_result_code_200"
        const val INTENT_REQUEST_CODE_EDIT_PROFILE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
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
        viewmodel.getUserData(userid).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        Glide.with(this)
                            .load(result.data.image)
                            .placeholder(R.drawable.image_placeholder)
                            .error(R.drawable.image_broken)
                            .into(binding.imageProfile)

                        binding.textName.text =
                            if (result.data.name.equals("")) "-" else result.data.name
                        binding.textPhone.text =
                            if (result.data.phoneNumber.equals("")) "-" else result.data.phoneNumber
                        binding.textEmail.text =
                            if (result.data.email.equals("")) "-" else result.data.email
                        binding.textBirtday.text =
                            if (result.data.birtdayDate.equals("")) "-" else result.data.birtdayDate

                        binding.buttonEdit.visibility = View.VISIBLE

                        binding.buttonEdit.setOnClickListener {
                            startActivityForResult(
                                Intent(this.context, EditProfileActivity::class.java).apply {
                                    putExtra(EXTRA_USER_DATA, result.data)
                                }, INTENT_REQUEST_CODE_EDIT_PROFILE
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(result.message.toString())
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

                        //TODO : Change to price convert
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
        if (requestCode == INTENT_REQUEST_CODE_EDIT_PROFILE && resultCode == RESULT_OK && data != null) {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}