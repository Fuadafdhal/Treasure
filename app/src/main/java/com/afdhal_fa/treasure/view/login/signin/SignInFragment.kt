package com.afdhal_fa.treasure.view.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afdhal_fa.treasure.MainActivity
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.core.utils.Constants.RC_SIGN_IN
import com.afdhal_fa.treasure.core.utils.LoginValidate
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentSignInBinding
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


@Suppress("DEPRECATION")
class SignInFragment : BaseFragment<SignInViewModel>() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    var callbackManager = CallbackManager.Factory.create()

    override fun onStart() {
        super.onStart()
        updateUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogleSignInClient()

        binding.buttonSignIn.setOnClickListener { signInEmail() }

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.buttonGoogleSign.setOnClickListener { signInGoogle() }

        binding.buttonFacebookSign.setOnClickListener {
            signInFacebook()
            Toast.makeText(requireContext(), "Comming soon !!!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun initGoogleSignInClient() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), googleSignInOptions)
    }


    /**
     * this for intent dialog sign in with google
     */
    private fun signInGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * this for intent dialog sign in with facebook
     */
    private fun signInFacebook() {
        LoginManager.getInstance()
            .logInWithReadPermissions(requireActivity(), Arrays.asList("public_profile", "email"))
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    signInWithFacebookAuth(result)
                }

                override fun onCancel() {
                    Timber.d("signInFacebook : onCalled")
                }

                override fun onError(error: FacebookException?) {
                    Timber.d("signInFacebook: onError { $error }")
                }
            })
    }

    /**
     * this for go to check sign in with email
     */
    private fun signInEmail() {
        val textEmail = binding.textFieldEmail.editText?.text.toString().trim()
        val textPassword = binding.textFieldPassword.editText?.text.toString().trim()

        if (validate(textEmail, textPassword)) {
            signInWithEmailAuth(textEmail, textPassword)
        }

    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * this for check onActivityResult from google sign in and facebook sign in
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleSignInAccount = task.getResult(ApiException::class.java)
                googleSignInAccount?.let { getGoogleAuthCredential(it) }
            } catch (e: ApiException) {
                Timber.d(e.message.toString())
            }
        }
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

    private fun signInWithEmailAuth(email: String, password: String) {
        onShowProgresbar()
        viewmodel.signInWithEmail(email, password).observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        if (it.data!!.isAuthenticated) {
                            updateUI()
                        }
                        onHideProgresbar()
                    }
                    is Resource.Error -> {
                        context?.makeToast(it.message.toString())
                        onHideProgresbar()
                    }
                }
            }
        })
    }

    /**
     *@param googleAuthCredential
     * this for go to check google sign in and set feedback
     */
    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        onShowProgresbar()
        viewmodel.signInWithGoogle(googleAuthCredential)
            .observe(viewLifecycleOwner) { authenticatedUser ->
                if (authenticatedUser != null) {
                    when (authenticatedUser) {
                        is Resource.Success -> {
                            if (authenticatedUser.data?.isNew!!) {
                                createNewUser(authenticatedUser.data)
                                Timber.d("createUser: Success")
                            } else {
                                updateUI()
                                onHideProgressBar()
                                Timber.d("createUser: A Ready An Account!")
                            }
                        }
                        is Resource.Error -> {
                            onHideProgresbar()
                            Toast.makeText(
                                this.requireContext(),
                                authenticatedUser.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
    }

    private fun signInWithFacebookAuth(result: LoginResult?) {
        val credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
        viewmodel.signInWithFacebook(credential).observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        updateUI()
                        context?.makeToast(it.data?.name.toString())
                    }
                    is Resource.Error -> {
                        Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }


    private fun createNewUser(mUser: User) {
        viewmodel.createUser(mUser).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isCreated!!) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                        updateUI()
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


    /**
     * @param email
     * @param password
     * @return true or false
     *  this is for validate textfield email adn password
     **/
    private fun validate(email: String, password: String): Boolean {
        if (!LoginValidate.isValidEmail(email)) {
            binding.textFieldEmail.error = "email salah"
        } else {
            binding.textFieldEmail.error = null
        }

        if (!LoginValidate.isValidPassoword(password)) {
            binding.textFieldPassword.error = "password salah"
        } else {
            binding.textFieldPassword.error = null
        }

        if (!LoginValidate.isValidEmail(email) and !LoginValidate.isValidPassoword(password)) {
            return false
        }
        return true
    }


    private fun updateUI() {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private fun onShowProgresbar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.textFieldEmail.isEnabled = false
        binding.textFieldPassword.isEnabled = false
        binding.buttonSignIn.isEnabled = false
        binding.buttonSignUp.isEnabled = false
        binding.buttonGoogleSign.isEnabled = false
        binding.buttonFacebookSign.isEnabled = false
    }

    private fun onHideProgresbar() {
        binding.progressBar.visibility = View.GONE
        binding.textFieldEmail.isEnabled = true
        binding.textFieldPassword.isEnabled = true
        binding.buttonSignIn.isEnabled = true
        binding.buttonSignUp.isEnabled = true
        binding.buttonGoogleSign.isEnabled = true
        binding.buttonFacebookSign.isEnabled = true
    }

    override fun initViewModel(): SignInViewModel {
        return ViewModelProvider(this)[SignInViewModel::class.java]
    }

}