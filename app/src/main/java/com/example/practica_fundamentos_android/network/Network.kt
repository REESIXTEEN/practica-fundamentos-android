package com.example.practica_fundamentos_android.network

import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.model.HeroeDTO
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Credentials
import okhttp3.HttpUrl

const val LIFE = 100

class Network {

    private val  urlLogin = "https://dragonball.keepcoding.education//api/auth/login"
    private val urlHeroes = "https://dragonball.keepcoding.education/api/heros/all"

    fun login(email: String, password: String): String {
        val client = OkHttpClient()
        val url = urlLogin
        val requestBody = FormBody.Builder().build()
        val auth = Credentials.basic(email, password)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", auth)
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        return if(response.code == 200)
            response.body?.string() ?: throw Exception("Login Error ${response.body.toString()}")
        else
            throw Exception("Login Error code ${response.code}")

    }

    fun getHeroes(token: String): List<Heroe> {
        val client = OkHttpClient()
        val url = urlHeroes
        val requestBody = FormBody.Builder()
            .add("name", "")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Bearer $token")
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        if(response.code == 200) {
            val gson = Gson()
            val dtoHeroes = gson.fromJson(response.body?.string() ?: "", Array<HeroeDTO>::class.java)
            return dtoHeroes.toList().map { Heroe(it.name, it.photo, LIFE, LIFE) }
        }
        else throw Exception("Error code: ${response.code}")

    }


}