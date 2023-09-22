package com.pausub.jokes.e2e

import com.pausub.jokes.api.model.Joke
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JokesEndToEndTest {

	@Autowired
	private lateinit var restTemplate: TestRestTemplate

	@Test
	fun `should fetch random joke`() {
		val response = restTemplate.getForEntity("/jokes/random", Joke::class.java)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body)
	}

	@Test
	fun `should fetch all categories`() {
		val response = restTemplate.getForEntity("/jokes/categories", Set::class.java)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).hasSizeGreaterThan(0)
	}

	@Test
	fun `should search jokes`() {
		val response = restTemplate.getForEntity("/jokes/search?query=joke", Set::class.java)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).hasSizeGreaterThan(0)
	}

}
