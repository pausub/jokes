package com.pausub.jokes.api

import com.pausub.jokes.api.model.ChuckJoke
import com.pausub.jokes.api.model.Joke

fun ChuckJoke.toJoke(): Joke {
    return Joke(
        category = categories.toSet(),
        id = id,
        value = value
    )
}