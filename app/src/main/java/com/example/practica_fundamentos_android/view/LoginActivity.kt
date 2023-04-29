package com.example.practica_fundamentos_android.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practica_fundamentos_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_login)


    }
}