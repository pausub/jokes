package com.pausub.jokes.api

import com.pausub.jokes.api.model.Joke
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class JokesController(private val jokeService: JokesService) {

    @GetMapping("/jokes/random")
    fun getRandomJoke(@RequestParam(name = "category", required = false) category: String?): Joke {
        return jokeService.getRandomJoke(category)
    }

    @GetMapping("/jokes/categories")
    fun getAllCategories(): Set<String> {
        return jokeService.getAllCategories()
    }

    @GetMapping("/jokes/search")
    fun searchJokes(@RequestParam(name = "query") query: String): Set<Joke> {
        return jokeService.searchJokes(query)
    }
}