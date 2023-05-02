package com.example.practica_fundamentos_android.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.model.HeroeDTO
import com.example.practica_fundamentos_android.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val context: Context, val caca:String): ViewModel() {

    private val network: Network = Network()
    private lateinit var token: String
    var heroes: List<Heroe> = listOf()

    init {
        getToken()
        getHeroes()
    }


    private fun getToken() {
        context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            .getString(TOKEN_ID, "")
    }

    private fun getHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            heroes = network.getHeroes(token)
//            heroes.forEach {
//                Log.i("TAG", it.name)
//            }

        }

    }
}