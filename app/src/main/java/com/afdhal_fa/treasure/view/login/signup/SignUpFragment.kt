package com.afdhal_fa.treasure.view.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.databinding.FragmentSignUpBinding

@Suppress("UNREACHABLE_CODE")
class SignUpFragment : BaseFragment<SignUpViewModel>() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignUp.setOnClickListener { signUpEmail() }

        viewmodel.validate.observe(viewLifecycleOwner, {
            if (it is Resource.Error) {
                if (it.message != null && it.data != null) {
                    handelUIState(it.message, it.data)
                }
            }
        })
    }

    private fun signUpEmail() {
        val textName = binding.textFieldName.editText?.text.toString().trim()
        val textEmail = binding.textFieldEmail.editText?.text.toString().trim()
        val textPhone = binding.textFieldPhone.editText?.text.toString().trim()
        val textPassword = binding.textFieldPassword.editText?.text.toString().trim()

        if (viewmodel.validate(textName, textEmail, textPhone, textPassword)) {
            signUpWithEmailAuth(textName, textEmail, textPhone, textPassword)
        }
    }

    private fun signUpWithEmailAuth(
        textName: String,
        textEmail: String,
        textPhone: String,
        textPassword: String
    ) {
        TODO("Not yet implemented")
    }

    private fun handelUIState(message: String, it: Validate) {
        it.textName.let {
            setNameError(it)
        }

        it.textEmail.let {
            if (message.equals("empty")) {
                setEmailError(it)
            } else {
                setEmailError(it)
            }
        }

        it.textPhone.let {
            setPhoneError(it)

        }

        it.textPassword.let {
            if (message.equals("empty")) {
                setPasswordError(it)
            } else {
                setPasswordError(it)
            }
        }
    }


    override fun onShowProgressBar() {

        binding.progressBar.visibility = View.VISIBLE
        binding.textFieldName.isEnabled = false
        binding.textFieldEmail.isEnabled = false
        binding.textFieldPhone.isEnabled = false
        binding.textFieldPassword.isEnabled = false
        binding.buttonSignIn.isEnabled = false
        binding.buttonSignUp.isEnabled = false
        binding.buttonGoogleSign.isEnabled = false
        binding.buttonFacebookSign.isEnabled = false
    }

    override fun onHideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.textFieldName.isEnabled = true
        binding.textFieldEmail.isEnabled = true
        binding.textFieldPhone.isEnabled = true
        binding.textFieldPassword.isEnabled = true
        binding.buttonSignIn.isEnabled = true
        binding.buttonSignUp.isEnabled = true
        binding.buttonGoogleSign.isEnabled = true
        binding.buttonFacebookSign.isEnabled = true
    }

    fun setNameError(err: String?) {
        if (err != null) {
            binding.textFieldName.isErrorEnabled = true
            binding.textFieldName.helperText = err
        } else {
            binding.textFieldName.isErrorEnabled = false
            binding.textFieldName.helperText = null
        }
    }

    fun setEmailError(err: String?) {
        if (err != null) {
            binding.textFieldEmail.isErrorEnabled = true
            binding.textFieldEmail.helperText = err
        } else {
            binding.textFieldEmail.isErrorEnabled = false
            binding.textFieldEmail.helperText = null
        }
    }

    fun setPhoneError(err: String?) {
        if (err != null) {
            binding.textFieldPhone.isErrorEnabled = true
            binding.textFieldPhone.helperText = err
        } else {
            binding.textFieldPhone.isErrorEnabled = false
            binding.textFieldPhone.helperText = null
        }
    }

    fun setPasswordError(err: String?) {
        if (err != null) {
            binding.textFieldPassword.isErrorEnabled = true
            binding.textFieldPassword.helperText = err
        } else {
            binding.textFieldPassword.isErrorEnabled = false
            binding.textFieldPassword.helperText = null
        }
    }

    override fun initViewModel(): SignUpViewModel {
        return ViewModelProvider(this)[SignUpViewModel::class.java]
    }

}