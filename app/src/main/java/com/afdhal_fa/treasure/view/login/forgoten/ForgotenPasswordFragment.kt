package com.afdhal_fa.treasure.view.login.forgoten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.core.utils.LoginValidate
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonForgotenPassowrd.setOnClickListener { forgotenPassword() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun initViewModel(): ForgotenViewModel {
        return ViewModelProvider(this)[ForgotenViewModel::class.java]
    }

    private fun forgotenPassword() {
        val textEmail = binding.textFieldEmail.editText?.text.toString().trim()

        if (validate(textEmail)) {
            forgotenWithEmail(textEmail)
        }
    }

    private fun forgotenWithEmail(textEmail: String) {
        onShowProgressBar()
        viewmodel.forgotenPasswordWithEmail(textEmail).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data!!.isReset) {
                        context?.makeToast("Password berhasil di reset silahkan cek email anda")
                    }
                    onHideProgressBar()
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                    onHideProgressBar()
                }
            }
        })
    }

    private fun validate(email: String): Boolean {
        if (!LoginValidate.isValidEmail(email)) {
            binding.textFieldEmail.error = "email salah"
        } else {
            binding.textFieldEmail.error = null
        }

        if (!LoginValidate.isValidEmail(email)) {
            return false
        }
        return true
    }

    override fun onShowProgressBar() {
        super.onShowProgressBar()
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onHideProgressBar() {
        super.onHideProgressBar()
        binding.progressBar.visibility = View.GONE
    }
}