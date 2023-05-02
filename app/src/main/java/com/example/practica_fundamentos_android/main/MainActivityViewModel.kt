package com.example.practica_fundamentos_android.main

import androidx.lifecycle.ViewModel
import com.example.practica_fundamentos_android.model.Heroe

class MainActivityViewModel: ViewModel() {

    private lateinit var token: String
    val heroes = listOf(
        Heroe("Goku", "", 100, 80),
        Heroe("Vegeta", "", 100, 50),
        Heroe("Bulma", "", 100, 30),
    )

    init {
        getToken()
        getHeroes()
    }


    private fun getToken() {
        token = ""
    }

    private fun getHeroes() {

    }
}