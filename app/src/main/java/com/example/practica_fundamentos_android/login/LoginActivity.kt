package com.example.practica_fundamentos_android.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practica_fundamentos_android.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            viewModel.email = binding.email.text.toString()
            viewModel.password = binding.password.text.toString()
            viewModel.login()
        }

        lifecycleScope.launch {
            viewModel.loginStatus.collect{
                when (it){
                    is LoginViewModel.LoginStatus.TokenReceived ->  {

                    }
                    is LoginViewModel.LoginStatus.Error -> {

                    }
                    is LoginViewModel.LoginStatus.CredentialsError -> {

                    }
                    is LoginViewModel.LoginStatus.Idle -> Unit
//                    else -> Unit
                }
            }
        }

    }
}