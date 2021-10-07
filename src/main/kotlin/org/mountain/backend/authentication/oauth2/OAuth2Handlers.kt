package org.mountain.backend.authentication.oauth2;

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.authentication.domain.User
import org.mountain.backend.authentication.dto.OAuth2Attributes
import org.mountain.backend.authentication.jwt.JwtProvider
import org.mountain.backend.authentication.service.UserService
import org.mountain.backend.authentication.type.RegistrationType
import org.mountain.backend.common.Redirection
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

val logger: Logger = LoggerFactory.getLogger("OAuth2-Handlers")

@Component
class OAuth2AuthenticationFailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        logger.error("OAuth2 인증 실패", exception)
    }

}

@Component
class OAuth2AuthenticationSuccessHandler @Autowired constructor(
    private val jwtProvider: JwtProvider,
    private val userService: UserService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val user: CustomOAuth2User = authentication!!.principal as CustomOAuth2User
        val registrationId: String = user.registrationId
        val username: String? = user.attributes["nickname"] as String
        val email: String? = user.attributes["email"] as String

        // registration Type이랑 다 비교해서 있으면 토큰 만들어서 보내줌.
        userService.getUserByEmailAndUsername(email!!, username!!)?.let {
            val token: String = jwtProvider.generateToken(email, Authority.ROLE_USER.name)
            writeSuccessResponse(response!!, token)
        } ?: run {
            userService.saveUser(User(
                username,
                email,
                "",
                Authority.ROLE_USER,
                RegistrationType.valueOf(registrationId.uppercase())
            ))
            val token: String = jwtProvider.generateToken(email, Authority.ROLE_USER.name)

            writeSuccessResponse(response!!, token)
        }
    }

    private fun writeSuccessResponse(response: HttpServletResponse, token: String) {
        Redirection.redirectAfterAuthenticationSuccessfully(response, 200, token)
    }

}
