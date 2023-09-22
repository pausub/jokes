package com.pausub.jokes.api

import com.pausub.jokes.api.model.Joke
import org.springframework.stereotype.Service

@Service
class JokesService(private val apiClient: ChuckJokesApiClient) {

    fun getRandomJoke(category: String?): Joke {
        return apiClient.getRandomJoke(category).toJoke()
    }

    fun searchJokes(query: String): Set<Joke> {
        return apiClient.searchJokes(query).map { it.toJoke() }.toSet()
    }

    fun getAllCategories(): Set<String> {
        return apiClient.getCategories()
    }
}