package org.mountain.backend.member.dto

import org.mountain.backend.mountain.domain.Mountain

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

data class UserMountainUpdateModel(
    val email: String,
    val mountainCode: String
)

data class UserMountainResponseModel(
    val mountains: List<Mountain>
)