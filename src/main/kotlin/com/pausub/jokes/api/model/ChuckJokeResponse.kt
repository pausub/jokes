package com.pausub.jokes.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChuckJokeResponse(
    @JsonProperty("categories") val categories: List<String>,
    @JsonProperty("id") val id: String,
    @JsonProperty("value") val value: String
)
