package com.example.bigm_offlinephonebook.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bigm_offlinephonebook.databinding.ActivityLoginBinding
import com.example.bigm_offlinephonebook.ui.AppBaseActivity
import com.example.bigm_offlinephonebook.ui.HomeActivity
import com.example.bigm_offlinephonebook.ui.InputActivity
import com.example.bigm_offlinephonebook.utils.Constants
import com.example.bigm_offlinephonebook.utils.extentions.getSharedPrefInstance
import com.example.bigm_offlinephonebook.utils.extentions.launchActivity
import com.example.bigm_offlinephonebook.utils.extentions.toast

class LoginActivity : AppBaseActivity() {
    companion object {
        private const val TAG: String = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getSharedPrefInstance().getBooleanValue(
                Constants.SharedPref.loginOrNot,
                false
            )
        ) {
            launchActivity<HomeActivity>()
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (loginValidation()) {
                Log.d(TAG, "Login Validation Success")
                getSharedPrefInstance().setValue(Constants.SharedPref.loginOrNot, true)
                launchActivity<InputActivity>()
                finish()
            } else {
                toast("Login Validation Failed")
                Log.d(TAG, "Login Validation Failed")
            }
        }
    }

    private fun loginValidation(): Boolean {

        return (binding.etEmail.text.toString().isNotEmpty()
                && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
            .matches()
                && binding.tiePassword.text.toString().isNotEmpty())
    }
}