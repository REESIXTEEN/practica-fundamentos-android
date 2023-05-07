package com.example.practica_fundamentos_android

import com.example.practica_fundamentos_android.model.Heroe
import org.junit.Test
import com.example.practica_fundamentos_android.network.Network
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import java.lang.Exception


class NetworkTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var httpClient: OkHttpClient

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun success_response_mock(result: String) {
        //Mockear que la respuesta del servidor es correcta
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(result)
        mockWebServer.enqueue(response)
    }

    private fun error_response_mock() {
        //Mockear que la respuesta del servidor es un error
        val response = MockResponse()
            .setResponseCode(401)
            .setBody("")
        mockWebServer.enqueue(response)
    }

    @Test
    fun test_login_success() {
        val expected = "Correct credentials"
        success_response_mock(expected)
        val network = Network()
        network.urlLogin = mockWebServer.url("/")
        val result = network.login("user","password")
        assertEquals(expected, result)

    }

    @Test
    fun test_login_error() {
        error_response_mock()
        val network = Network()
        network.urlLogin = mockWebServer.url("/")
        try {
            network.login("user","password")
            assertEquals(1,2)
        }catch (e: Exception){
            assertEquals("Login Error code 401", e.message)
        }
    }

    @Test
    fun test_heroes_success() {
        val response = arrayOf(Heroe("Goku","",100,100))
        val responseJson = Gson().toJson(response)
        success_response_mock(responseJson.toString())
        val network = Network()
        network.urlHeroes = mockWebServer.url("/")
        val result = network.getHeroes("token")
        val expected = listOf(Heroe("Goku","",100,100))
        assertEquals(expected, result)
    }

    @Test
    fun test_heroes_error() {
        error_response_mock()
        val network = Network()
        network.urlHeroes = mockWebServer.url("/")
        try {
            network.getHeroes("token")
            assertEquals(1,2)
        }catch (e: Exception){
            assertEquals("Error code: 401", e.message)
        }
    }





}