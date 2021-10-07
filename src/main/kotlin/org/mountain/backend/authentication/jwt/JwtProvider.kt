package org.mountain.backend.authentication.jwt

import io.jsonwebtoken.*
import org.mountain.backend.configuration.AppProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*
import javax.annotation.PostConstruct
import kotlin.properties.Delegates

val log: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

@Service
class JwtProvider (
    val appProperties: AppProperties,
) {

    lateinit var tokenSecret: String
    var tokenExpirationSeconds: Long by Delegates.notNull()

    @PostConstruct
    fun init() {
        tokenSecret = Base64.getEncoder().encodeToString(appProperties.auth.tokenSecret.toByteArray())
        tokenExpirationSeconds = appProperties.auth.tokenExpirationSeconds * 1000L
    }

    fun generateToken(subject: String, authorities: String): String {
        val now: Long = Date().time
        val expiresIn = Date(now + tokenExpirationSeconds)

        return Jwts.builder()
            .setSubject(subject)
            .claim("auth", authorities)
            .setExpiration(expiresIn)
            .signWith(SignatureAlgorithm.HS256, tokenSecret)
            .compact()
    }

    fun parseClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(tokenSecret)
            .parseClaimsJws(token)
            .body
    }

    fun validation(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token)
            return true
        } catch (e: MalformedJwtException) {
            log.error("잘못된 JWT 서명입니다", e)
        } catch (e: ExpiredJwtException) {
            log.error("만료된 JWT 토큰입니다.", e)
        } catch (e: UnsupportedJwtException) {
            log.error("지원되지 않는 JWT 토큰입니다.", e)
        } catch (e: IllegalArgumentException) {
            log.error("JWT 토큰이 잘못되었습니다.", e)
        }

        return false
    }

}