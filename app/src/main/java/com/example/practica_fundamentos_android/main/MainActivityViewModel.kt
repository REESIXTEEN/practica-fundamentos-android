package com.example.practica_fundamentos_android.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

const val HEAL = 20
const val MIN_DAMAGE = 10
const val MAX_DAMAGE = 60

class MainActivityViewModel(): ViewModel() {

    private val network: Network = Network()
    private lateinit var token: String
    var heroes: List<Heroe> = listOf()

    private val _mainStatus = MutableStateFlow<MainStatus>(MainStatus.Loading)
    val mainStatus: StateFlow<MainStatus> = _mainStatus


    fun getToken(context: Context) {
        token = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getString("token", "").toString()
    }

    fun deleteToken(context: Context) {
        context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            .edit()
            .remove("token")
            .apply()
    }

    fun getHeroes() {
        _mainStatus.value = MainStatus.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                heroes = network.getHeroes(token)
                Log.i("TAG", "Heroes obtained from api")
                _mainStatus.update { MainStatus.Success }
            }catch (e: Exception) {
                _mainStatus.value = MainStatus.Error("Something went wrong. $e")
            }
        }

    }

    fun healHeroe(pos: Int) {
        heroes[pos].vidaRestante = heroes[pos].vidaRestante + HEAL
        if(heroes[pos].vidaRestante > heroes[pos].vidaTotal){
            heroes[pos].vidaRestante = heroes[pos].vidaTotal
        }
    }

    fun damageHeroe(pos: Int) {
        val damage = Random.nextInt(MIN_DAMAGE, MAX_DAMAGE)
        heroes[pos].vidaRestante = heroes[pos].vidaRestante - damage
        if(heroes[pos].vidaRestante <= 0) {
            heroes[pos].vidaRestante = 0
        }
    }


    sealed class MainStatus {
        object Loading : MainStatus()
        data class Error(val error: String) : MainStatus()
        object Success : MainStatus()
    }
}

