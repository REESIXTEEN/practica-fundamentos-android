package com.example.practica_fundamentos_android

import com.example.practica_fundamentos_android.model.Heroe
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HeroeTests {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_heroe() {
        val heroe = Heroe("Goku", "url_photo", 100,100)
        Assert.assertEquals("Goku",heroe.name)
        Assert.assertEquals("url_photo",heroe.photo)
        Assert.assertEquals(100,heroe.vidaTotal)
        Assert.assertEquals(100,heroe.vidaRestante)
    }
}