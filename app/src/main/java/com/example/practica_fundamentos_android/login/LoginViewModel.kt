package com.example.practica_fundamentos_android.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val network: Network = Network()

    lateinit  var  email: String
    lateinit  var  password: String

    private val _loginStatus = MutableStateFlow<LoginStatus>(LoginStatus.Idle)
    val loginStatus: StateFlow<LoginStatus> = _loginStatus

    fun login(){
        if(checkCredentials()){
            _loginStatus.value = LoginStatus.Loading
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val token = network.login(email,password)
                    Log.i("TAG", "Token obtained from api")
                    _loginStatus.value = LoginStatus.TokenReceived(token)
                }catch (e: Exception) {
                    _loginStatus.value = LoginStatus.Error("Error during login. $e")
                }
            }
        }else {
            _loginStatus.value = LoginStatus.CredentialsError
        }
    }

    private fun checkCredentials(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    fun saveToken(token: String, context: Context) {
        context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            .edit()
            .putString("token", token)
            .apply()
    }

    fun isLogged(context: Context): Boolean {
        return !context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getString("token", "").isNullOrEmpty()
    }


    sealed class LoginStatus{
        object Idle : LoginStatus()
        data class Error(val error: String) : LoginStatus()
        data class TokenReceived(val token: String) : LoginStatus()
        object CredentialsError : LoginStatus()
        object Loading : LoginStatus()
    }

}

