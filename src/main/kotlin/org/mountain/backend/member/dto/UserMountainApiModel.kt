package org.mountain.backend.member.dto

import org.mountain.backend.member.domain.UserMountain
import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.dto.MountainResponseModel
import org.mountain.backend.review.dto.ReviewResponseModel

data class UserMountainUpdateModel(
    val email: String,
    val mountainCode: String
)

data class UserMountainResponseModel(
    val mountains: List<Mountain>
)

data class UserInfoResponseModel(
    val username: String,
    val email: String,
    val mountain: List<MountainResponseModel>,
    val reviews: List<ReviewResponseModel>
) {

}