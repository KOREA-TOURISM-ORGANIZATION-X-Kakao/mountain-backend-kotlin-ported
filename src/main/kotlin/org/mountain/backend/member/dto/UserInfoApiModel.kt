package org.mountain.backend.member.dto

data class UserInfoUpdateModel(
    val email: String,
    val username: String,
    val password: String
)

data class UserInfoResponse(
    val email: String,
    val username: String,
    val modifiedAt: String
)