package com.pausub.jokes.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChuckJokesList(@JsonProperty("result") val result: Set<ChuckJoke>)