package org.mountain.backend.authentication.dto

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.authentication.domain.User

data class SignupModel(
    val username: String,
    val email: String,
    val password: String
) {

    fun toEntity(): User {
        return User(username, email, password, Authority.ROLE_USER)
    }

}

data class SigninModel(
    val email: String,
    val password: String
)