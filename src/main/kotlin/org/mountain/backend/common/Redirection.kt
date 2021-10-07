package org.mountain.backend.common

import com.fasterxml.jackson.databind.ObjectMapper
import javax.servlet.http.HttpServletResponse

class Redirection(
    val objectMapper: ObjectMapper
) {

    companion object {
        private val AUTHENTICATION_REDIRECT_URL: String = "http://localhost:3001/auth/redirect";

        fun redirectAfterAuthenticationSuccessfully(
            response: HttpServletResponse,
            status: Int,
            token: String
        ) {
            response.status = status
            response.sendRedirect("$AUTHENTICATION_REDIRECT_URL?token=$token")
        }

        fun redirectAfterAuthenticationFailure(
            response: HttpServletResponse,
            status: Int,
            errorMessage: String
        ) {
            response.status = status
            response.contentType = "application/json;charset=UTF-8"
            response.sendRedirect("$AUTHENTICATION_REDIRECT_URL?error=$errorMessage")
        }
    }

}