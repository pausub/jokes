package com.pausub.jokes.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChuckJokesListResponse(@JsonProperty("result") val result: Set<ChuckJokeResponse>)