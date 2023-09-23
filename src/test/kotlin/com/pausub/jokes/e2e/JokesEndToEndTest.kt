package com.pausub.jokes.e2e

import com.pausub.jokes.api.model.JokeResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JokesEndToEndTest {

	@Autowired
	private lateinit var restTemplate: TestRestTemplate

	@Test
	fun `should fetch random joke`() {
		val response = restTemplate.getForEntity("/random", JokeResponse::class.java)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).isNotNull
	}

	@Test
	fun `should fetch random joke by category`() {
		val response = restTemplate.getForEntity("/random?category=dev", JokeResponse::class.java)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body?.categories?.contains("dev")).isTrue()
	}

	@Test
	fun `should fetch all categories`() {
		val response = restTemplate.getForEntity("/categories", Set::class.java)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).hasSizeGreaterThan(0)
	}

	@Test
	fun `should search jokes`() {
		val responseType = object : ParameterizedTypeReference<Set<JokeResponse>>() {}
		val response = restTemplate.exchange("/search?query=mugger", HttpMethod.GET, null, responseType)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).hasSizeGreaterThan(0)
		assertThat(response.body?.all { it.value.contains("mugger") }).isTrue()
	}

}
