package com.pausub.jokes.api

import com.pausub.jokes.api.model.ChuckJoke
import com.pausub.jokes.api.model.ChuckJokesList
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod.GET
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class ChuckJokesApiClient(private val restTemplate: RestTemplate) {

    private val log = LoggerFactory.getLogger(ChuckJokesApiClient::class.java)

    private val baseUrl = "https://api.chucknorris.io/jokes"

    fun getRandomJoke(category: String? = null): ChuckJoke {
        val uri = URI(if (category != null) "$baseUrl/random?category=$category" else "$baseUrl/random")
        val response = executeRequest<ChuckJoke>(uri)
        return response.body ?: throw RuntimeException("Failed to fetch a joke")
    }

    fun getCategories(): Set<String> {
        val response = executeRequest<Set<String>>(URI("$baseUrl/categories"))
        return response.body ?: throw RuntimeException("Failed to fetch categories")
    }

    fun searchJokes(query: String): Set<ChuckJoke> {
        val response = executeRequest<ChuckJokesList>(URI("$baseUrl/search?query=$query"))
        return response.body?.result ?: throw RuntimeException("Failed to fetch jokes")
    }

    private inline fun <reified T> executeRequest(uri: URI): ResponseEntity<T> {
        val requestEntity = RequestEntity<Void>(null, GET, uri)
        log.info("Sending a request: $requestEntity")
        val response = restTemplate.exchange(requestEntity, T::class.java)
        log.info("Received response: ${response.body}")
        return response
    }

}