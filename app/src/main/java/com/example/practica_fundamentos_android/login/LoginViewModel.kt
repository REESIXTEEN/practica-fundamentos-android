package com.example.practica_fundamentos_android.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
//                    val result = withContext(Dispatchers.IO) {
//                        network.login(email, password)
//                    }
                    val result = network.login(email,password)
                    _loginStatus.value = LoginStatus.TokenReceived
                    saveToken(result)
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

    private fun saveToken(token: String) {
        println(token)
    }


    sealed class LoginStatus{
        object Idle : LoginStatus()
        data class Error(val error: String) : LoginStatus()
        object TokenReceived : LoginStatus()
        object CredentialsError : LoginStatus()
    }

}

