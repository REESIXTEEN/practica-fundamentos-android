package com.example.practica_fundamentos_android.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.practica_fundamentos_android.databinding.ActivityLoginBinding
import com.example.practica_fundamentos_android.main.MainActivity

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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginStatus.collect {
                    when (it) {
                        is LoginViewModel.LoginStatus.TokenReceived -> {
                            val intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                        is LoginViewModel.LoginStatus.Error -> {
                            Toast.makeText(baseContext, it.error, Toast.LENGTH_LONG).show()
                        }
                        is LoginViewModel.LoginStatus.CredentialsError -> {
                            Toast.makeText(baseContext, "Email or password empty", Toast.LENGTH_LONG).show()
                        }
                        is LoginViewModel.LoginStatus.Idle -> Unit
                    }
                }
            }
        }

    }
}