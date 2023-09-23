package com.pausub.jokes.api

import com.pausub.jokes.api.model.JokeResponse
import org.springframework.stereotype.Service

@Service
class JokesService(private val apiClient: ChuckJokesApiClient) {

    fun getRandomJoke(category: String?): JokeResponse {
        return apiClient.getRandomJoke(category).toJoke()
    }

    fun searchJokes(query: String): Set<JokeResponse> {
        return apiClient.searchJokes(query).map { it.toJoke() }.toSet()
    }

    fun getAllCategories(): Set<String> {
        return apiClient.getCategories()
    }
}