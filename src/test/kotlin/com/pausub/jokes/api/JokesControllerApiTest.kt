package com.pausub.jokes.api

import com.pausub.jokes.api.model.JokeResponse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class JokesControllerApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var jokeService: JokesService

    @Test
    fun `should fetch random joke`() {

        val jokeResponse = JokeResponse(setOf("dev"), "123", "Sample joke")
        `when`(jokeService.getRandomJoke(null)).thenReturn(jokeResponse)

        val expectedResponse = """
            {
                "categories": ["dev"],
                "id": "123",
                "value": "Sample joke"
            }
        """.trimIndent()

        mockMvc.perform(get("/random"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().json(expectedResponse))
    }

    @Test
    fun `should fetch random joke by category`() {

        val jokeResponse = JokeResponse(setOf("dev"), "123", "Sample joke")
        `when`(jokeService.getRandomJoke("dev")).thenReturn(jokeResponse)

        val expectedResponse = """
            {
                "categories": ["dev"],
                "id": "123",
                "value": "Sample joke"
            }
        """.trimIndent()

        mockMvc.perform(get("/random?category=dev"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().json(expectedResponse))
    }

    @Test
    fun `should fetch all categories`() {

        val categories = setOf("dev", "movie")
        `when`(jokeService.getAllCategories()).thenReturn(categories)

        val expectedResponse = """
            ["dev", "movie"]
        """.trimIndent()

        mockMvc.perform(get("/categories"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().json(expectedResponse))
    }

    @Test
    fun `should search jokes`() {
        val jokeResponses = setOf(JokeResponse(setOf("dev"), "123", "Sample joke"), JokeResponse(setOf("dev"), "1234", "Sample joke 2"))
        `when`(jokeService.searchJokes("joke")).thenReturn(jokeResponses)

        val expectedResponse = """
            [
                {
                    "categories": ["dev"],
                    "id": "123",
                    "value": "Sample joke"
                },
                {
                    "categories": ["dev"],
                    "id": "1234",
                    "value": "Sample joke 2"
                }
            ]
        """.trimIndent()

        mockMvc.perform(get("/search").param("query", "joke"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().json(expectedResponse))
    }

}