package com.pausub.jokes.api

import com.pausub.jokes.api.model.ChuckJokeResponse
import com.pausub.jokes.api.model.ChuckJokesListResponse
import com.pausub.jokes.exception.RateLimitExceededException
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpMethod.GET
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.time.Duration

@Service
class ChuckJokesApiClient(
    private val restTemplate: RestTemplate,
    private val meterRegistry: MeterRegistry
) {

    private val log = LoggerFactory.getLogger(ChuckJokesApiClient::class.java)
    private val baseUrl = "https://api.chucknorris.io/jokes"

    private val requestCounter: Counter = Counter.builder("external.api.chucknorris.calls").register(meterRegistry)
    private val requestDuration: Timer = Timer.builder("external.api.chucknorris.call.duration").register(meterRegistry)
    private val requestFailures: Counter = Counter.builder("external.api.chucknorris.call.failures").register(meterRegistry)

    private val bucket: Bucket = Bucket.builder()
        .addLimit(Bandwidth.simple(5, Duration.ofSeconds(1))) // 5 requests per second
        .build()

    fun getRandomJoke(category: String? = null): ChuckJokeResponse {
        val uri = URI(if (category != null) "$baseUrl/random?category=$category" else "$baseUrl/random")
        val response = executeRequest<ChuckJokeResponse>(uri)
        return response.body ?: throw RuntimeException("Failed to fetch a joke")
    }

    @Cacheable("jokes")
    fun getCategories(): Set<String> {
        val response = executeRequest<Set<String>>(URI("$baseUrl/categories"))
        return response.body ?: throw RuntimeException("Failed to fetch categories")
    }

    @Cacheable("categories")
    fun searchJokes(query: String): Set<ChuckJokeResponse> {
        val response = executeRequest<ChuckJokesListResponse>(URI("$baseUrl/search?query=$query"))
        return response.body?.result ?: throw RuntimeException("Failed to fetch jokes")
    }

    private inline fun <reified T> executeRequest(uri: URI): ResponseEntity<T> {
        val requestEntity = RequestEntity<Void>(null, GET, uri)
        log.info("Sending a request: $requestEntity")

        if (!bucket.tryConsume(1)) {
            throw RateLimitExceededException("Rate limit exceeded for request $$requestEntity")
        }

        requestCounter.increment()

        val response: ResponseEntity<T>? = requestDuration.recordCallable {
            restTemplate.exchange(requestEntity, T::class.java)
        }

        if (response?.statusCode?.isError == true) {
            requestFailures.increment()
        }

        log.info("Received response: ${response?.body}")
        return response ?: throw RuntimeException("Request failed: $requestEntity")
    }

}