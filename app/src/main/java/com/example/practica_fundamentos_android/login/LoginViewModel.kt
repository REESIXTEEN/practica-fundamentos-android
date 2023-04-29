package com.example.practica_fundamentos_android.login

import androidx.lifecycle.ViewModel
import com.example.practica_fundamentos_android.network.Network

class LoginViewModel: ViewModel() {

    private val network: Network = Network()

    lateinit  var  email: String
    lateinit  var  password: String
    fun login(){
       if (checkCredentials()) {

       }
       else {

       }
    }

    fun checkCredentials() : Boolean{

    }

}