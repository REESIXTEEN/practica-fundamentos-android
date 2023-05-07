package com.example.practica_fundamentos_android.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.network.LIFE
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

    val network: Network = Network()
    lateinit var token: String
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
        val heroe = heroes[pos]
        heroe.vidaRestante = heroe.vidaRestante + HEAL
        if(heroe.vidaRestante > heroe.vidaTotal){
            heroe.vidaRestante = heroe.vidaTotal
        }
    }

    fun damageHeroe(pos: Int) {
        val damage = Random.nextInt(MIN_DAMAGE, MAX_DAMAGE)
        val heroe = heroes[pos]
        heroe.vidaRestante = heroe.vidaRestante - damage
        if(heroe.vidaRestante <= 0) {
            heroe.vidaRestante = 0
        }
    }

    fun restartAll() {
        for (heroe in heroes){
            heroe.vidaRestante = LIFE
        }
    }


    sealed class MainStatus {
        object Loading : MainStatus()
        data class Error(val error: String) : MainStatus()
        object Success : MainStatus()
    }
}

