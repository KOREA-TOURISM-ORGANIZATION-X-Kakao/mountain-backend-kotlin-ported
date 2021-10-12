package org.mountain.backend.exception

import org.springframework.http.HttpStatus

enum class ErrorType(
    val errorCode: String,
    val httpStatus: HttpStatus
) {

    RUNTIME_EXCEPTION("R0001", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED("S0001", HttpStatus.UNAUTHORIZED)

}