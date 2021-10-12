package org.mountain.backend.member.dto

import org.mountain.backend.mountain.domain.Mountain

data class UserMountainUpdateModel(
    val email: String,
    val mountainCode: String
)

data class UserMountainResponseModel(
    val mountains: List<Mountain>
)