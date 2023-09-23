package com.pausub.jokes.bdd.definitions

import com.pausub.jokes.api.model.JokeResponse
import io.cucumber.java.en.And
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JokesStepDefinitions {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private var categoryResponse: ResponseEntity<Set<*>>? = null
    private var jokeResponse: ResponseEntity<JokeResponse>? = null
    private var jokesResponse: ResponseEntity<Set<JokeResponse>>? = null

    @When("user fetch a random joke")
    fun userFetchARandomJoke() {
        jokeResponse = restTemplate.getForEntity("/random", JokeResponse::class.java)
    }

    @When("user fetch a random joke by given category")
    fun userFetchARandomJokeByGivenCategory() {
        jokeResponse = restTemplate.getForEntity("/random?category=dev", JokeResponse::class.java)
    }

    @And("user should receive a joke with given category")
    fun userShouldReceiveAJokeWithGivenCategory() {
        assertThat(jokeResponse?.body?.categories).contains("dev")
    }

    @When("user fetch all joke categories")
    fun userFetchAllJokeCategories() {
        categoryResponse = restTemplate.getForEntity("/categories", Set::class.java)
    }

    @When("user search for jokes with the query {string}")
    fun userSearchForJokesWithTheQuery(query: String) {
        val responseType = object : ParameterizedTypeReference<Set<JokeResponse>>() {}
        jokesResponse = restTemplate.exchange("/search?query=$query", HttpMethod.GET, null, responseType)
    }

    @And("all jokes should contain {string} substring")
    fun allJokesShouldContainSubstring(query: String) {
        assertThat(jokesResponse?.body?.all { it.value.contains(query) }).isTrue()
    }

    @And("user should receive a joke")
    fun userShouldReceiveAJoke() {
        assertThat(jokeResponse?.body).isNotNull
    }

    @And("user should receive at least one joke")
    fun userShouldReceiveAtLeastOneJoke() {
        assertThat(jokesResponse?.body).isNotEmpty
    }

    @And("user should receive at least one category")
    fun userShouldReceiveAtLeastOneCategory() {
        assertThat((categoryResponse?.body)?.size).isGreaterThan(0)
    }
}
