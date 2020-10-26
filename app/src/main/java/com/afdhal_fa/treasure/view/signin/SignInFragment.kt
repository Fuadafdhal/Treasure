package com.afdhal_fa.treasure.view.signin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.utils.LoginValidate
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
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


@Suppress("DEPRECATION")
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewmodel: SignInViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    var callbackManager = CallbackManager.Factory.create()

    companion object {
        var TAG = SignInFragment::class.java.simpleName
        private const val RC_SIGN_IN = 2020

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

        viewmodel = ViewModelProvider(this)[SignInViewModel::class.java]

        initGoogleSignInClient()
        printHashKey(requireContext())

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.buttonGoogleSign.setOnClickListener { signInGoogle() }

        binding.buttonFacebookSign.setOnClickListener {
//            signInFacebook()
            Toast.makeText(requireContext(), "Comming soon !!!", Toast.LENGTH_SHORT).show()
        }

        binding.buttonSignIn.setOnClickListener { signInEmail() }
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
                    Log.d(TAG, "signInFacebook : onCalled")
                }

                override fun onError(error: FacebookException?) {
                    Log.d(TAG, "signInFacebook: onError { $error }")
                }
            })
    }

    /**
     * this for go to check sign in with email
     */
    private fun signInEmail() {
        val textEmail = binding.textFieldEmail.editText?.text.toString().trim()
        val textPassword = binding.textFieldPassword.editText?.text.toString().trim()

        if (validate(textEmail, textPassword)) return

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
                Log.d(TAG, e.message.toString())
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

    /**
     *@param googleAuthCredential
     * this for go to check google sign in and set feedback
     */
    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        viewmodel.signInWithGoogle(googleAuthCredential)
        viewmodel.authenticatedUserLiveData?.observe(viewLifecycleOwner) { authenticatedUser ->
            if (authenticatedUser != null) {
                when (authenticatedUser) {
                    is Resource.Success -> {
                        Toast.makeText(
                            this.requireContext(),
                            "${authenticatedUser.data?.name}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                        Toast.makeText(this.requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun signInWithFacebookAuth(result: LoginResult?) {
        val credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
        viewmodel.signInWithFacebook(credential)
        viewmodel.authenticatedUserLiveData?.observe(viewLifecycleOwner, { authenticatedUser ->
            if (authenticatedUser != null) {
                when (authenticatedUser) {
                    is Resource.Success -> {
                        Toast.makeText(
                            this.requireContext(),
                            "${authenticatedUser.data?.name}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                        Toast.makeText(this.requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

    }

    private fun updateUI() {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            TODO("make not go to nex package")
        } else {
            TODO("make go to nex package")
        }
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

    @SuppressLint("PackageManagerGetSignatures")
    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.packageManager
                .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }
    }

}