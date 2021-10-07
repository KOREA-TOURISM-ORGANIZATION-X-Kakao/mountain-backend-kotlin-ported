package org.mountain.backend.test.jwt

import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mountain.backend.authentication.jwt.JwtProvider
import org.mountain.backend.configuration.AppProperties

@ExtendWith(MockitoExtension::class)
open class JwtTest {

    @Mock
    lateinit var jwtProvider: JwtProvider

    @InjectMocks
    lateinit var appProperties: AppProperties

    @BeforeEach
    fun init() {
        appProperties = AppProperties()
        appProperties.auth.tokenSecret = "test-secret"
        appProperties.auth.tokenExpirationSeconds = 360000L

        jwtProvider = JwtProvider(appProperties)
        jwtProvider.init()
    }

    @DisplayName("jwt token 생성 테스트")
    @Test
    fun jwt_jwtGenerationTest() {
        // given
        val subject = "wsnam0507@gmail.com"
        val authorities = "ROLE_USER"

        // then
        val token = jwtProvider.generateToken(subject, authorities)
        println("generated token is => $token")

        Assertions.assertEquals(token.isEmpty(), false)
    }

    @DisplayName("jwt 검증 테스트")
    @Test
    fun jwt_jwtValidation() {
        // given
        val subject = "wsnam0507@gmail.com"
        val authorities = "ROLE_USER"
        val token = jwtProvider.generateToken(subject, authorities)

        // then
        val isValidToken = jwtProvider.validation(token)
        Assertions.assertEquals(isValidToken, true)
    }

    @DisplayName("jwt claims 파싱 테스트")
    @Test
    fun jwt_parseClaimsTest() {
        val subject = "wsnam0507@gmail.com"
        val authorities = "ROLE_USER"
        val token = jwtProvider.generateToken(subject, authorities)

        val claims = jwtProvider.parseClaims(token)
        Assertions.assertEquals(claims.get("auth"), authorities)
    }

}