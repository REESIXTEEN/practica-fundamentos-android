package com.example.practica_fundamentos_android.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.practica_fundamentos_android.R
import com.example.practica_fundamentos_android.databinding.ActivityMainBinding
import com.example.practica_fundamentos_android.login.LoginActivity
import com.example.practica_fundamentos_android.login.LoginViewModel
import com.example.practica_fundamentos_android.main.fragments.FragmentTable
import com.example.practica_fundamentos_android.network.LIFE
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerView.id, FragmentTable(viewModel))
            .commitNow()

        binding.logOutBtn.setOnClickListener {
            viewModel.deleteToken(this)
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}