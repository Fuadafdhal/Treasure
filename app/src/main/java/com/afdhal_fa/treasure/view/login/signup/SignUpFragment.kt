package com.afdhal_fa.treasure.view.login.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.core.utils.LoginValidate
import com.afdhal_fa.treasure.databinding.FragmentSignUpBinding
import timber.log.Timber

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

        binding.textFieldEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!LoginValidate.isValidEmail(s.toString().trim())) {
                    setEmailError("Email tidak benar")
                } else {
                    setEmailError(null)
                }
            }
        })

        binding.buttonSignUp.setOnClickListener { signUpEmail() }


        viewmodel.authResult?.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isNew!!) {
                        viewmodel.createUser(it.data)
                        Timber.d("createUser: Success")
                    } else {
                        Timber.d("createUser: A Ready An Account!")
                    }
                }

                is Resource.Error -> {
                    Timber.e(it.message)
                    Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    this.onHideProgressBar()
                }

                is Resource.Loading -> {

                }
            }
        })

        viewmodel.createNewUserResult?.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isCreated!!) {
                        TODO()
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    }

                    this.onHideProgressBar()
                }

                is Resource.Error -> {
                    this.onHideProgressBar()

                }

                is Resource.Loading -> {
                }
            }
        })
    }

    private fun signUpEmail() {
        val textName = binding.textFieldName.editText?.text.toString().trim()
        val textEmail = binding.textFieldEmail.editText?.text.toString().trim()
        val textPhone = binding.textFieldPhone.editText?.text.toString().trim()
        val textPassword = binding.textFieldPassword.editText?.text.toString().trim()

        if (validate(textName, textEmail, textPhone, textPassword)) {
            onShowProgressBar()
            signUpWithEmailAuth(textName, textEmail, textPhone, textPassword)
        }
    }

    private fun signUpWithEmailAuth(
        textName: String,
        textEmail: String,
        textPhone: String,
        textPassword: String
    ) {
        viewmodel.signUpWithEmail(textName, textEmail, textPhone, textPassword)
    }


    private fun setNameError(err: String?) {
        if (err != null) {
            binding.textFieldName.isErrorEnabled = true
            binding.textFieldName.helperText = err
            binding.textFieldName.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.design_default_color_error)

        } else {
            binding.textFieldName.isErrorEnabled = false
            binding.textFieldName.helperText = null
            binding.textFieldName.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        }
    }

    private fun setEmailError(err: String?) {
        if (err != null) {
            binding.textFieldEmail.isErrorEnabled = true
            binding.textFieldEmail.helperText = err
            binding.textFieldEmail.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.design_default_color_error)


        } else {
            binding.textFieldEmail.isErrorEnabled = false
            binding.textFieldEmail.helperText = null
            binding.textFieldEmail.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        }
    }

    private fun setPhoneError(err: String?) {
        if (err != null) {
            binding.textFieldPhone.isErrorEnabled = true
            binding.textFieldPhone.helperText = err
            binding.textFieldPhone.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.design_default_color_error)


        } else {
            binding.textFieldPhone.isErrorEnabled = false
            binding.textFieldPhone.helperText = null
            binding.textFieldPhone.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        }
    }

    private fun setPasswordError(err: String?) {
        if (err != null) {
            binding.textFieldPassword.isErrorEnabled = true
            binding.textFieldPassword.helperText = err
            binding.textFieldPassword.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.design_default_color_error)

        } else {
            binding.textFieldPassword.isErrorEnabled = false
            binding.textFieldPassword.helperText = null
            binding.textFieldPassword.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        }
    }

    private fun validate(
        textName: String,
        textEmail: String,
        textPhone: String,
        textPassword: String
    ): Boolean {

        if (textName.isEmpty()) {
            setNameError("Nama tidak boleh kosong")
        } else {
            setNameError(null)
        }

        if (textEmail.isEmpty()) {
            setEmailError("Email tidak boleh kosong")
        } else if (!LoginValidate.isValidEmail(textEmail)) {
            setEmailError("Email tidak benar")
        } else {
            setEmailError(null)
        }

        if (textPhone.isEmpty()) {
            setPhoneError("Nomor HP tidak boleh kosong")
        } else {
            setPhoneError(null)
        }

        if (textPassword.isEmpty()) {
            setPasswordError("Password tidak boleh kosong")
        } else if (!LoginValidate.isValidPassoword(textPassword)) {
            setPasswordError("Password harus memiliki 8 karakter")
        } else {
            setPasswordError(null)
        }

        if (
            textName.isEmpty() or textEmail.isEmpty() or textPhone.isEmpty() or textPassword.isEmpty() or
            !LoginValidate.isValidEmail(textEmail) or
            !LoginValidate.isValidPassoword(textPassword)
        ) return false

        return true
    }

    override fun onShowProgressBar() {

        binding.progressBar.visibility = View.VISIBLE
//        binding.textFieldName.isEnabled = false
//        binding.textFieldEmail.isEnabled = false
//        binding.textFieldPhone.isEnabled = false
//        binding.textFieldPassword.isEnabled = false
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

    override fun initViewModel(): SignUpViewModel {
        return ViewModelProvider(this)[SignUpViewModel::class.java]
    }

}