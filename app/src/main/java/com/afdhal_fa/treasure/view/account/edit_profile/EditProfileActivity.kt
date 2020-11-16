package com.afdhal_fa.treasure.view.account.edit_profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.utils.setMarginStart
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.ActivityMainEditProfileBinding
import com.afdhal_fa.treasure.view.account.AccoutFragment.Companion.INTENT_EXTRA_RESULT
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.app_bar.view.*
import timber.log.Timber


class EditProfileActivity : BaseToolbarActivity<EditProfileViewModel>() {
    private lateinit var binding: ActivityMainEditProfileBinding

    companion object {
        const val EXTRA_USER_DATA = "extra_user_data"
    }

    private lateinit var uid: String
    private lateinit var image: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_edit_profile)

        intent.getParcelableExtra<User>(EXTRA_USER_DATA)?.let {
            uid = it.uid
            image = it.image
            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_broken)
                .into(binding.imageProfile)

            binding.textFieldName.editText?.setText(it.name)
            binding.textFieldPhone.editText?.setText(it.phoneNumber)
            binding.textFieldEmail.editText?.setText(it.email)
            binding.textFieldBirtday.editText?.setText(it.birtdayDate)
        }

        binding.buttonSave.setOnClickListener { view ->
            val textName = binding.textFieldName.editText?.text.toString().trim()
            val textPhone = binding.textFieldPhone.editText?.text.toString().trim()
            val textBirtdayDate = binding.textFieldBirtday.editText?.text.toString().trim()

            if (validate(textName, textPhone, textBirtdayDate)) {
                onShowProgressBar()
                viewmodel.updatePtofile(uid, image, textName, textPhone, textBirtdayDate)
                    .observe(this, {
                        when (it) {
                            is Resource.Success -> {
                                setResult(
                                    RESULT_OK,
                                    Intent().apply { putExtra(INTENT_EXTRA_RESULT, true) })
                                makeToast("Memperbarui Profile sukses")
                                onHideProgressBar()
                                onBackPressed()
                            }
                            is Resource.Error -> {
                                onHideProgressBar()
                                makeToast("Memperbarui Profile gagal")
                                Timber.d(it.message)
                            }
                        }
                    })
            }
        }

        binding.buttonChangePicture.setOnClickListener {
            makeToast("Coming soon !!!")
        }
    }

    private fun validate(
        name: String,
        phoneNumber: String,
        birtday: String
    ): Boolean {

        if (name.isEmpty()) {
            setNameError("Nama tidak boleh kosong")
        } else {
            setNameError(null)
        }

        if (phoneNumber.isEmpty()) {
            setPhoneError("Nomor telpon tidak boleh kosong")
        } else {
            setPhoneError(null)
        }

        if (birtday.isEmpty()) {
            setBirtdayDateError("Tanggal lahit tidak boleh kosong")
        } else {
            setBirtdayDateError(null)
        }

        if (
            name.isEmpty() or phoneNumber.isEmpty() or birtday.isEmpty()
        ) {
            return false
        }

        return true
    }


    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getString(R.string.title_edit_profile)
        binding.includeAppbar.textTitleToolbar.setMarginStart(72)
        return binding.includeAppbar.toolbar
    }

    override fun setToolbarButtonBack() = true

    override fun initViewModel(): EditProfileViewModel {
        return ViewModelProvider(this)[EditProfileViewModel::class.java]
    }

    private fun setNameError(err: String?) {
        if (err != null) {
            binding.textFieldName.isErrorEnabled = true
            binding.textFieldName.helperText = err
            binding.textFieldName.boxStrokeColor =
                ContextCompat.getColor(this, R.color.design_default_color_error)

        } else {
            binding.textFieldName.isErrorEnabled = false
            binding.textFieldName.helperText = null
            binding.textFieldName.boxStrokeColor =
                ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }

    private fun setPhoneError(err: String?) {
        if (err != null) {
            binding.textFieldPhone.isErrorEnabled = true
            binding.textFieldPhone.helperText = err
            binding.textFieldPhone.boxStrokeColor =
                ContextCompat.getColor(this, R.color.design_default_color_error)


        } else {
            binding.textFieldPhone.isErrorEnabled = false
            binding.textFieldPhone.helperText = null
            binding.textFieldPhone.boxStrokeColor =
                ContextCompat.getColor(this, R.color.colorPrimary)

        }
    }

    private fun setBirtdayDateError(err: String?) {
        if (err != null) {
            binding.textFieldBirtday.isErrorEnabled = true
            binding.textFieldBirtday.helperText = err
            binding.textFieldBirtday.boxStrokeColor =
                ContextCompat.getColor(this, R.color.design_default_color_error)


        } else {
            binding.textFieldBirtday.isErrorEnabled = false
            binding.textFieldBirtday.helperText = null
            binding.textFieldBirtday.boxStrokeColor =
                ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }

    override fun onShowProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onHideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}