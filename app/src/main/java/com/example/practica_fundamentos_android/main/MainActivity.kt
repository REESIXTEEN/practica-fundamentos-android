package com.example.practica_fundamentos_android.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.practica_fundamentos_android.databinding.ActivityMainBinding
import com.example.practica_fundamentos_android.login.LoginViewModel

const val TOKEN_ID = "token"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    sealed class MainStatus{
        object Loading : MainStatus()
        data class Error(val error: String) : MainStatus()
        object Success : MainStatus()
    }
}