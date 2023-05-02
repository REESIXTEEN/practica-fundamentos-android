package com.example.practica_fundamentos_android.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.TOKEN_ID
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
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val token = network.login(email,password)
                    _loginStatus.value = LoginStatus.TokenReceived(token)
                }catch (e: Exception) {
                    _loginStatus.value = LoginStatus.Error("Error during login. " + e.toString())
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
            .putString(TOKEN_ID, token)
            .apply()
    }


    sealed class LoginStatus{
        object Idle : LoginStatus()
        data class Error(val error: String) : LoginStatus()
        data class TokenReceived(val token: String) : LoginStatus()
        object CredentialsError : LoginStatus()
        object loading : LoginStatus()
    }

}

