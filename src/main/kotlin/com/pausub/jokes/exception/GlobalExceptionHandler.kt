package com.pausub.jokes.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestClientException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(RestClientException::class)
    fun handleRestClientException(exception: RestClientException): ResponseEntity<String> {
        logger.error("Received rest client exception: ${exception.message}", exception)
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Server error: ${exception.message}")
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleGenericException(exception: RuntimeException): ResponseEntity<String> {
        logger.error("Internal server error: ${exception.message}", exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: ${exception.message}")
    }

}