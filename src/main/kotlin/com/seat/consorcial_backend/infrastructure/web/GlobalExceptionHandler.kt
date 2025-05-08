package com.seat.consorcial_backend.infrastructure.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val parameterName = ex.parameter.parameterName
        val requiredType = ex.requiredType?.simpleName ?: "unknown"
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Bad Request",
                message = "Parameter '$parameterName' should be of type $requiredType"
            ))
    }

    data class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String
    )
} 