package org.mountain.backend.review.dto

import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.review.domain.Review
import org.springframework.security.core.userdetails.User

data class ReviewResponseModel(
    val reviewer: String,
    val grade: Double,
    val comment: String
) {
    companion object {
        fun of(reviews: Set<Review>): List<ReviewResponseModel> {
            return reviews.map { ReviewResponseModel(it.user.username, it.grade, it.comment) }.toList()
        }
    }
}

// 리뷰 저장/수정 모델
data class ReviewPostModel(
    val email: String,
    val mountainCode: String,
    val grade: Double,
    val comment: String
)

data class ReviewDeleteModel(
    val id: Long
)