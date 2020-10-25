package com.afdhal_fa.treasure.view.signin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afdhal_fa.treasure.R

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
    }
}