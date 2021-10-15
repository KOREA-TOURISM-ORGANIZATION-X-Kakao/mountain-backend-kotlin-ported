package org.mountain.backend.review.dto

import org.mountain.backend.review.domain.Review

data class ReviewResponseModel(
    val id: Long,
    val reviewer: String,
    val grade: Double,
    val comment: String
) {
    companion object {
        fun of(reviews: Set<Review>): List<ReviewResponseModel> {
            return reviews.map { ReviewResponseModel(it.id, it.user.username, it.grade, it.comment) }.toList()
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

data class ReviewUpdateByUserModel(
    val email: String,
    val reviewId: Long,
    val grade: Double,
    val comment: String
)

data class ReviewDeleteModel(
    val id: String
)