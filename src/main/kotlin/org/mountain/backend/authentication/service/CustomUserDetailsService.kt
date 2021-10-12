package org.mountain.backend.authentication.service;

import org.mountain.backend.member.domain.User
import org.mountain.backend.member.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        val user: User? = userService.getUserByEmail(username!!) ?: throw UsernameNotFoundException("$username 을 찾을 수 없습니다.")

        return createUserDetails(user!!)
    }

    private fun createUserDetails(user: User): UserDetails? {
        val grantedAuthority: GrantedAuthority = SimpleGrantedAuthority(user.authority.name)

        return org.springframework.security.core.userdetails.User(user.email, user.password, listOf(grantedAuthority))
    }

}
