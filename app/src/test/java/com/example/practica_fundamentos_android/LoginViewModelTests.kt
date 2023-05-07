package com.example.practica_fundamentos_android

import com.example.practica_fundamentos_android.login.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.junit.After
import org.junit.Assert
import org.junit.Before


class LoginViewModelTests {

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
        success_response_mock("token")
        val viewModel = LoginViewModel()
        viewModel.network.urlLogin = mockWebServer.url("/")
        viewModel.email = "email"
        viewModel.password = "password"
        viewModel.login()
        Thread.sleep(3000) //al hacerse en segundo plano esperamos a que termine
        val expected = LoginViewModel.LoginStatus.TokenReceived("token")
        val result = viewModel.loginStatus.value
        Assert.assertEquals(expected, result)

    }

    @Test
    fun test_login_error() {
        error_response_mock()
        val viewModel = LoginViewModel()
        viewModel.network.urlLogin = mockWebServer.url("/")
        viewModel.email = "email"
        viewModel.password = "password"
        viewModel.login()
        Thread.sleep(3000) //al hacerse en segundo plano esperamos a que termine
        val expected = LoginViewModel.LoginStatus.Error("Error during login. java.lang.Exception: Login Error code 401")
        val result = viewModel.loginStatus.value
        Assert.assertEquals(expected, result)

    }

    @Test
    fun test_credentials() {
        val viewModel = LoginViewModel()
        viewModel.email = ""
        viewModel.password = ""
        viewModel.login()
        Thread.sleep(2000) //al hacerse en segundo plano esperamos a que termine
        val expected = LoginViewModel.LoginStatus.CredentialsError
        val result = viewModel.loginStatus.value
        Assert.assertEquals(expected, result)

        viewModel.email = "email"
        viewModel.login()
        Thread.sleep(2000) //al hacerse en segundo plano esperamos a que termine
        Assert.assertEquals(expected, result)
    }

//    @Test
//    fun test_token_saved() {
//        val main = MainActivity()
//        val viewModel = LoginViewModel()
//        viewModel.saveToken("token1234", main.baseContext)
//    }



}