package org.mountain.backend.exception

import org.springframework.http.HttpStatus

data class ErrorEntity(
    val httpStatus: HttpStatus,
    val errorMessage: String,
    val errorCode: String
)