package com.example.practica_fundamentos_android

import org.junit.Test
import com.example.practica_fundamentos_android.network.Network
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After

import org.junit.Assert.*
import org.junit.Before

const val OK_RESPONSE = "Correct user and password"
const val ERROR_RESPONSE = "Incorrect user and password"

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

    @Test
    fun test_login_success() {
        success_response()
        val network = Network()
        val result = network.login("user","password")
        assertEquals(OK_RESPONSE, result)

        val url = mockWebServer.url("/")
        val request = Request.Builder()
            .url(url)
            .build()

    }


    private fun success_response() {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(OK_RESPONSE)
        mockWebServer.enqueue(response)
    }

    private fun error_response() {
        val response = MockResponse()
            .setResponseCode(401)
            .setBody(ERROR_RESPONSE)
        mockWebServer.enqueue(response)
    }


}