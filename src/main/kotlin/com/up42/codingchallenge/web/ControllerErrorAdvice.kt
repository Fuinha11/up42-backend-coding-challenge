package com.up42.codingchallenge.web

import com.up42.codingchallenge.error.FeatureNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerErrorAdvice {
    @ExceptionHandler(value = [(FeatureNotFoundException::class)])
    fun handleResponseStatusException(ex: FeatureNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(Exception::class)])
    fun handleGenericException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message!!), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}