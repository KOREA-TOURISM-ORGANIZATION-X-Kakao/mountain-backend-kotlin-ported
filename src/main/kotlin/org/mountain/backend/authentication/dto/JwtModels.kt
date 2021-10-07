package org.mountain.backend.authentication.dto

data class JwtResponseModel(
    val accessToken: String,
    val refreshToken: String
)
