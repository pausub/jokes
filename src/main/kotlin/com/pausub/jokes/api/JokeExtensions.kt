package com.pausub.jokes.api

import com.pausub.jokes.api.model.ChuckJokeResponse
import com.pausub.jokes.api.model.JokeResponse

fun ChuckJokeResponse.toJoke(): JokeResponse {
    return JokeResponse(
        categories = categories.toSet(),
        id = id,
        value = value
    )
}