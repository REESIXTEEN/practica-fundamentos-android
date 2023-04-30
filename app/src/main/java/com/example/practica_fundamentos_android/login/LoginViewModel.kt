package com.example.practica_fundamentos_android.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.network.Network
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
        checkCredentials()
        if (true){
            viewModelScope.launch {
                network.login(email,password)
            }

        }
    }

    private fun checkCredentials() {
        if (email.isEmpty() || password.isEmpty()) _loginStatus.value = LoginStatus.CredentialsError
    }


    sealed class LoginStatus{
        object Idle : LoginStatus()
        data class Error(val error: String) : LoginStatus()
        data class TokenReceived(val token: String) : LoginStatus()
        object CredentialsError : LoginStatus()
    }

}

