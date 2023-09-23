package com.pausub.jokes.api

import com.pausub.jokes.api.model.JokeResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class JokesController(private val jokeService: JokesService) {

    @GetMapping("/random")
    fun getRandomJoke(@RequestParam(name = "category", required = false) category: String?): JokeResponse {
        return jokeService.getRandomJoke(category)
    }

    @GetMapping("/categories")
    fun getAllCategories(): Set<String> {
        return jokeService.getAllCategories()
    }

    @GetMapping("/search")
    fun searchJokes(@RequestParam(name = "query") query: String): Set<JokeResponse> {
        return jokeService.searchJokes(query)
    }
}