package org.mountain.backend.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.RuntimeException
import javax.servlet.http.HttpServletRequest

class ApiException(
    val errorType: ErrorType,
    private val errorMessage: String
) : RuntimeException(errorMessage)

@RestControllerAdvice
class ApiExceptionAdvice {

    @ExceptionHandler(ApiException::class)
    fun exceptionHandler(request: HttpServletRequest, apiException: ApiException): ResponseEntity<ErrorEntity> {
        apiException.printStackTrace()
        return ResponseEntity.status(apiException.errorType.httpStatus)
            .body(ErrorEntity(apiException.errorType.httpStatus, apiException.message!!, apiException.errorType.errorCode))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun exceptionHandler(
        request: HttpServletRequest, accessDeniedException: AccessDeniedException
    ): ResponseEntity<ErrorEntity> {
        accessDeniedException.printStackTrace()
        return ResponseEntity.status(ErrorType.ACCESS_DENIED.httpStatus)
            .body(ErrorEntity(ErrorType.ACCESS_DENIED.httpStatus, "권한이 없습니다.", ErrorType.ACCESS_DENIED.errorCode))
    }

}