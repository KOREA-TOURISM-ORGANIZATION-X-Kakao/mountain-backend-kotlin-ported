package org.mountain.backend.authentication.dto

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.member.domain.User
import org.mountain.backend.authentication.type.RegistrationType

fun SignupModel.toEntity(): User {
    return User(
        username,
        email,
        password,
        "",
        Authority.ROLE_USER,
        RegistrationType.EMAIL
    )
}

data class SignupModel(
    val username: String,
    val email: String,
    val password: String
)

data class SigninModel(
    val email: String,
    val password: String
)
