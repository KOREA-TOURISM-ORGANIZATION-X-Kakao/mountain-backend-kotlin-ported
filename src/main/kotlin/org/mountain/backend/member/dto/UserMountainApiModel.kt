package org.mountain.backend.member.dto

import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.dto.MountainResponseModel
import org.mountain.backend.review.dto.ReviewResponseModel

data class UserMountainUpdateModel(
    val email: String,
    val mountainCode: String
)

data class UserMountainResponseModel(
    val mountains: List<Mountain>,
    val totalPage: Int
)

data class UserInfoResponseModel(
    val username: String,
    val email: String,
    val mountain: List<MountainResponseModel>,
    val reviews: List<ReviewResponseModel>
)

data class UserMountainPaginationRequestModel(
    val email: String,
    val currentPage: Int,
    val dataSize: Int
)