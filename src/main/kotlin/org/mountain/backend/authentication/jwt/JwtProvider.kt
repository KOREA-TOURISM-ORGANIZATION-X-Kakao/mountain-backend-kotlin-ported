package org.mountain.backend.authentication.jwt

import io.jsonwebtoken.*
import org.mountain.backend.configuration.AppProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
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
    val claimsKey = "authority"
    val headerTokenKey = "X-AUTH-TOKEN"

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
            .claim(claimsKey, authorities)
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

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val authority: String = claims.get(claimsKey).toString() ?: throw RuntimeException("??????????????? ?????? ???????????????.")
        val authorities: Collection<out GrantedAuthority> = listOf(SimpleGrantedAuthority(authority))
        val userDetails: UserDetails = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(userDetails, "", authorities)
    }

    fun validation(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token)
            return true
        } catch (e: MalformedJwtException) {
            log.error("????????? JWT ???????????????", e)
        } catch (e: ExpiredJwtException) {
            log.error("????????? JWT ???????????????.", e)
        } catch (e: UnsupportedJwtException) {
            log.error("???????????? ?????? JWT ???????????????.", e)
        } catch (e: IllegalArgumentException) {
            log.error("JWT ????????? ?????????????????????.", e)
        }

        return false
    }

}