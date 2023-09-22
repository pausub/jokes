package com.pausub.jokes.api

import com.pausub.jokes.api.model.Joke
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

        val joke = Joke(setOf("dev"), "123", "Sample joke")
        `when`(jokeService.getRandomJoke(null)).thenReturn(joke)

        val expectedResponse = """
            {
                "category": ["dev"],
                "id": "123",
                "value": "Sample joke"
            }
        """.trimIndent()

        mockMvc.perform(get("/jokes/random"))
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

        mockMvc.perform(get("/jokes/categories"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().json(expectedResponse))
    }

    @Test
    fun `should search jokes`() {
        val jokes = setOf(Joke(setOf("dev"), "123", "Sample joke"), Joke(setOf("dev"), "1234", "Sample joke 2"))
        `when`(jokeService.searchJokes("joke")).thenReturn(jokes)

        val expectedResponse = """
            [
                {
                    "category": ["dev"],
                    "id": "123",
                    "value": "Sample joke"
                },
                {
                    "category": ["dev"],
                    "id": "1234",
                    "value": "Sample joke 2"
                }
            ]
        """.trimIndent()

        mockMvc.perform(get("/jokes/search").param("query", "joke"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().json(expectedResponse))
    }

}