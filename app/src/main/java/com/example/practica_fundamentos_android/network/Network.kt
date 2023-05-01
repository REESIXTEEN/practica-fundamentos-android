package com.example.practica_fundamentos_android.network

import android.util.Log
import com.example.practica_fundamentos_android.login.LoginViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import android.util.Base64
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.Credentials

class Network {


    fun login(email: String, password: String): String {
        val client = OkHttpClient()
        val url = "https://dragonball.keepcoding.education//api/auth/login"
        val requestBody = FormBody.Builder().build()

//        val credentials = "$email:$password"
//        val auth = "Basic ${Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)}"
        val auth = Credentials.basic(email, password)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", auth)
            .build()

        return try {
            val call = client.newCall(request)
            val response = call.execute()
            response.body?.string() ?: ""
        } catch (e: Exception) {
            throw Exception("Error login: ${e.printStackTrace()}")
        }

//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//            override fun onResponse(call: Call, response: Response) {
//                Log.i("TAG", response.body?.string()!!)
//            }
//        })

    }


}