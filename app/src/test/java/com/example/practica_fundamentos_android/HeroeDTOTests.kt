package com.example.practica_fundamentos_android

import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.model.HeroeDTO
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HeroeDTOTests {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_heroe() {
        val heroeDTO = HeroeDTO("description","Goku", "1234", true,"url_photo")
        Assert.assertEquals("Goku",heroeDTO.name)
        Assert.assertEquals("description",heroeDTO.description)
        Assert.assertEquals("1234",heroeDTO.id)
        Assert.assertEquals(true,heroeDTO.favorite)
        Assert.assertEquals("url_photo",heroeDTO.photo)
    }
}