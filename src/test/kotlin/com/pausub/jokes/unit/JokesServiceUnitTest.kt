package com.pausub.jokes.unit

import com.pausub.jokes.api.ChuckJokesApiClient
import com.pausub.jokes.api.JokesService
import com.pausub.jokes.api.model.ChuckJoke
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class JokesServiceUnitTest {

    @MockBean
    private lateinit var apiClient: ChuckJokesApiClient

    @Autowired
    private lateinit var jokesService: JokesService

    @Test
    fun `should fetch random joke`() {
        val mockJoke = ChuckJoke(listOf("category"), "id", "value")
        `when`(apiClient.getRandomJoke(anyString())).thenReturn(mockJoke)
        val result = jokesService.getRandomJoke("category")

        assertEquals("id", result.id)
        assertEquals("value", result.value)
        assertTrue(result.categories.contains("category"))
    }

    @Test
    fun `should search jokes`() {
        val mockJokes = setOf(
                ChuckJoke(listOf("category1"), "id1", "value1"),
                ChuckJoke(listOf("category2"), "id2", "value2")
        )
        `when`(apiClient.searchJokes("test")).thenReturn(mockJokes)

        val result = jokesService.searchJokes("test")

        assertEquals(2, result.size)
        assertTrue(result.any { it.id == "id1" && it.value == "value1" && it.categories.contains("category1") })
        assertTrue(result.any { it.id == "id2" && it.value == "value2" && it.categories.contains("category2") })
    }

    @Test
    fun `should fetch all categories`() {
        val mockCategories = setOf("category1", "category2", "category3")
        `when`(apiClient.getCategories()).thenReturn(mockCategories)

        val result = jokesService.getAllCategories()

        assertEquals(3, result.size)
        assertTrue(result.containsAll(listOf("category1", "category2", "category3")))
    }
}
