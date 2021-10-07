package org.mountain.backend.authentication.configuration;

import org.mountain.backend.authentication.jwt.JwtAccessDeniedHandler
import org.mountain.backend.authentication.jwt.JwtAuthenticationEntryPoint
import org.mountain.backend.authentication.jwt.JwtAuthenticationFilter
import org.mountain.backend.authentication.oauth2.CustomOAuth2UserService
import org.mountain.backend.authentication.oauth2.OAuth2AuthenticationFailureHandler
import org.mountain.backend.authentication.oauth2.OAuth2AuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.config.web.servlet.invoke

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration @Autowired constructor(
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    val jwtAuthenticationFilter: JwtAuthenticationFilter,
    val customOAuth2UserService: CustomOAuth2UserService,
    val oAuth2SuccessfullyHandler: OAuth2AuthenticationSuccessHandler,
    val oAuth2FailureHandler: OAuth2AuthenticationFailureHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    /**
     * {@link org.mountain.backend.authentication.service.CustomUserDetailsService.kt} 참조
     * */
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity?) {
        http {
            csrf {
                disable()
            }
            exceptionHandling {
                jwtAuthenticationEntryPoint
            }
            sessionManagement {
                SessionCreationPolicy.STATELESS
            }
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            oauth2Login {
                authorizationEndpoint {
                    "/oauth2/authorization"
                }
                userInfoEndpoint {
                    customOAuth2UserService
                }
                authenticationSuccessHandler = oAuth2SuccessfullyHandler
                authenticationFailureHandler = oAuth2FailureHandler
            }
            addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        }
    }

}
