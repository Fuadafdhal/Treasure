package com.afdhal_fa.treasure.view.login.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.afdhal_fa.treasure.MainActivity
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.core.utils.Constants
import com.afdhal_fa.treasure.core.utils.LoginValidate
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentSignUpBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber
import java.util.*

@Suppress("UNREACHABLE_CODE", "COMPATIBILITY_WARNING")
class SignUpFragment : BaseFragment<SignUpViewModel>() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    var callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogleSignInClient()

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

        binding.buttonGoogleSign.setOnClickListener { signInGoogle() }

        binding.buttonFacebookSign.setOnClickListener { signInFacebook() }

        binding.buttonSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
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
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Success -> {
                        if (it.data?.isNew!!) {
                            createNewUser(it.data)
                            Timber.d("createUser: Success")
                        }
                    }

                    is Resource.Error -> {
                        context?.makeToast(it.message.toString())
                        onHideProgressBar()
                        Timber.e(it.message)
                    }
                }
            })
    }


    /**
     * this for intent dialog sign in with google
     */
    private fun signInGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
    }

    /**
     * @param googleSignInAccount
     * this for get credential for google sign in
     */
    private fun getGoogleAuthCredential(googleSignInAccount: GoogleSignInAccount) {
        val googleTokenId = googleSignInAccount.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }

    /**
     *@param googleAuthCredential
     * this for go to check google sign in and set feedback
     */
    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        onShowProgressBar()
        viewmodel.signInWithGoogle(googleAuthCredential)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        if (it.data?.isNew!!) {
                            createNewUser(it.data)
                            Timber.d("createUser: Success")
                        } else {
                            updateUI()
                            onHideProgressBar()
                            Timber.d("createUser: A Ready An Account!")
                        }
                    }

                    is Resource.Error -> {
                        context?.makeToast(it.message.toString())
                        onHideProgressBar()
                        Timber.d("createUser: an error")
                    }
                }

            }
    }


    /**
     * this for intent dialog sign in with facebook
     */
    private fun signInFacebook() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    signInWithFacebookAuth(result)
                    Timber.d("facebook:onSuccess:$result")
                }

                override fun onCancel() {
                    Timber.d("signInFacebook : onCalled")
                }

                override fun onError(error: FacebookException?) {
                    Timber.d("signInFacebook: onError { $error }")
                }
            })
    }

    private fun signInWithFacebookAuth(result: LoginResult?) {
        onShowProgressBar()
        val credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
        viewmodel.signInWithFacebook(credential).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isNew!!) {
                        createNewUser(it.data)
                        Timber.d("createUser: Success")
                    } else {
                        updateUI()
                        onHideProgressBar()
                        Timber.d("createUser: A Ready An Account!")
                    }
                }

                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                    onHideProgressBar()
                    Timber.d("createUser: an error")
                }
            }
        })
    }


    private fun createNewUser(mUser: User) {
        viewmodel.createUser(mUser).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isCreated!!) {
                        createNewTreasurUser(it.data.uid)
                    }
                }
                is Resource.Error -> {
                    this.onHideProgressBar()
                }
            }
        })
    }

    private fun createNewTreasurUser(uid: String) {
        viewmodel.createTreasureUser(uid).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.id != "") {
                        updateUI()
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    }
                    this.onHideProgressBar()
                }
                is Resource.Error -> {
                    this.onHideProgressBar()
                }
            }
        })
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * this for check onActivityResult from google sign in and facebook sign in
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleSignInAccount = task.getResult(ApiException::class.java)
                googleSignInAccount?.let { getGoogleAuthCredential(it) }
            } catch (e: ApiException) {
                Timber.d(e.message.toString())
            }
        }
    }

    private fun initGoogleSignInClient() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), googleSignInOptions)
    }

    override fun initViewModel(): SignUpViewModel {
        return ViewModelProvider(this)[SignUpViewModel::class.java]
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


    private fun updateUI() {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }
}