package com.afdhal_fa.treasure.view.account.edit_profile

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.setMarginStart
import com.afdhal_fa.treasure.databinding.ActivityMainEditProfileBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.app_bar.view.*

class EditProfileActivity : BaseToolbarActivity<EditProfileViewModel>() {
    private lateinit var binding: ActivityMainEditProfileBinding

    companion object {
        const val EXTRA_USER_DATA = "extra_user_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_edit_profile)

        intent.getParcelableExtra<User>(EXTRA_USER_DATA)?.let {
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
    }

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = "Edit Profile"
        binding.includeAppbar.textTitleToolbar.setMarginStart(72)
        return binding.includeAppbar.toolbar
    }

    override fun setToolbarButtonBack(): Boolean {
        return true
    }

    override fun initViewModel(): EditProfileViewModel {
        return ViewModelProvider(this)[EditProfileViewModel::class.java]
    }
}