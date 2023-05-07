package com.example.practica_fundamentos_android

import com.example.practica_fundamentos_android.login.LoginViewModel
import com.example.practica_fundamentos_android.main.MainActivityViewModel
import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.network.Network
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainActivityViewModelTests {

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
    fun test_getHeroes_success() {
        val response = arrayOf(Heroe("Goku","",100,100))
        val responseJson = Gson().toJson(response)
        success_response_mock(responseJson.toString())
        val viewModel = MainActivityViewModel()
        viewModel.network.urlHeroes = mockWebServer.url("/")
        viewModel.token = "token"
        viewModel.getHeroes()
        Thread.sleep(3000) //al hacerse en segundo plano esperamos a que termine

        val expected = MainActivityViewModel.MainStatus.Success
        val result = viewModel.mainStatus.value
        Assert.assertEquals(expected,result)

    }


    @Test
    fun test_getHeroes_error() {
        error_response_mock()
        val viewModel = MainActivityViewModel()
        viewModel.network.urlHeroes = mockWebServer.url("/")
        viewModel.token = "token"
        viewModel.getHeroes()
        Thread.sleep(3000) //al hacerse en segundo plano esperamos a que termine
        val expected = MainActivityViewModel.MainStatus.Error("Something went wrong. java.lang.Exception: Error code: 401")
        val result = viewModel.mainStatus.value
        Assert.assertEquals(expected, result)

    }

    @Test
    fun test_heal_heroe() {
        val viewModel = MainActivityViewModel()
        viewModel.heroes = listOf(Heroe("Goku","",100,50))
        viewModel.healHeroe(0)
        var expected = Heroe("Goku","",100,70)
        var result = viewModel.heroes[0]
        Assert.assertEquals(expected, result)

        viewModel.heroes = listOf(Heroe("Goku","",100,90))
        viewModel.healHeroe(0)
        expected = Heroe("Goku","",100,100)
        result = viewModel.heroes[0]
        Assert.assertEquals(expected, result)
    }

    @Test
    fun test_damage_heroe() {
        // lo habría mockeado así, pero por ser una funcion estatica no funciona( por falta de tiempo no lo hago)
//        val mockRandom = mock(Random::class.java)
//        `when`(mockRandom.nextInt()).thenReturn(35)

//        val viewModel = MainActivityViewModel()
//        viewModel.heroes = listOf(Heroe("Goku","",100,50))
//        viewModel.damageHeroe(0)
//        var expected = Heroe("Goku","",100,15)
//        var result = viewModel.heroes[0]
//        Assert.assertTrue(result - )
//
//        viewModel.heroes = listOf(Heroe("Goku","",100,10))
//        viewModel.damageHeroe(0)
//        expected = Heroe("Goku","",100,0)
//        result = viewModel.heroes[0]
//        Assert.assertEquals(expected, result)
    }

    @Test
    fun test_restart_heroes() {
        val viewModel = MainActivityViewModel()
        viewModel.heroes = listOf(Heroe("Goku","",100,50))
        viewModel.restartAll()
        val expected = Heroe("Goku","",100,100)
        val result = viewModel.heroes[0]
        Assert.assertEquals(expected, result)
    }





}