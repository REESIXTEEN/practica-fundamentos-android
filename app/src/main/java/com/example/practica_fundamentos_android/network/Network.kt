package com.example.practica_fundamentos_android.network

import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.model.HeroeDTO
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Credentials

class Network {


    fun login(email: String, password: String): String {
        val client = OkHttpClient()
        val url = "https://dragonball.keepcoding.education//api/auth/login"
        val requestBody = FormBody.Builder().build()
        val auth = Credentials.basic(email, password)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", auth)
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        return response.body?.string() ?: ""

    }

    fun getHeroes(token: String): List<Heroe> {
        val client = OkHttpClient()
        val url = "https://dragonball.keepcoding.education/api/heros/all"
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
        val gson = Gson()
        val dtoHeroes =  gson.fromJson(response.body?.string() ?: "", Array<HeroeDTO>::class.java)
        return dtoHeroes.toList().map { Heroe(it.name, it.photo, 100,100) }

//        response.body?.let { responseBody -> val gson = Gson()
//            try {
//                val a =  gson.fromJson(responseBody.string(), Array<HeroeDTO>::class.java)
//                return a
//            } catch (ex: Exception) {
//                return arrayOf()
//            }
//        } ?: run { return arrayOf() }

    }


}