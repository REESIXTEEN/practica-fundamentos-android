package com.example.practica_fundamentos_android.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.login.LoginViewModel
import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.model.HeroeDTO
import com.example.practica_fundamentos_android.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(): ViewModel() {

    private val network: Network = Network()
    private lateinit var token: String
    var heroes: List<Heroe> = listOf()

    private val _mainStatus = MutableStateFlow<MainStatus>(MainStatus.Loading)
    val mainStatus: StateFlow<MainStatus> = _mainStatus


    fun getToken(context: Context) {
        token = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getString("token", "").toString()
    }

    fun getHeroes() {
        _mainStatus.value = MainStatus.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                heroes = network.getHeroes(token)
                _mainStatus.update { MainStatus.Success }
            }catch (e: Exception) {
                _mainStatus.value = MainStatus.Error("Something went wrong. $e")
            }

        }

    }

    sealed class MainStatus {
        object Loading : MainStatus()
        data class Error(val error: String) : MainStatus()
        object Success : MainStatus()
    }
}

