package org.mountain.backend.authentication.model

data class SignupModel(
    val username: String,
    val email: String,
    val password: String
)