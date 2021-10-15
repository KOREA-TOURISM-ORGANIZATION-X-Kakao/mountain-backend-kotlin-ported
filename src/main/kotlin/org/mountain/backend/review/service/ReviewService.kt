package org.mountain.backend.review.service

import org.mountain.backend.member.domain.User
import org.mountain.backend.member.service.UserService
import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.service.MountainService
import org.mountain.backend.review.domain.Review
import org.mountain.backend.review.dto.ReviewPostModel
import org.mountain.backend.review.dto.ReviewResponseModel
import org.mountain.backend.review.dto.UserReviewPaginationResponseModel
import org.mountain.backend.review.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService @Autowired constructor(
    val userService: UserService,
    @Qualifier("mountainService") val mountainService: MountainService,
    val reviewRepository: ReviewRepository
) {

    @Transactional
    fun saveReview(reviewPostModel: ReviewPostModel) {
        val user: User = userService.getUserByEmail(reviewPostModel.email)
        val mountain: Mountain = mountainService.getMountainById(reviewPostModel.mountainCode)
        val review = Review(user, mountain, reviewPostModel.grade, reviewPostModel.comment)

        user.reviews.add(review)
        mountain.reviews.add(review)

        review.user = user
        review.mountain = mountain

        reviewRepository.save(review)
    }

}

@Service
class UserReviewService(
    val reviewRepository: ReviewRepository
) {

    fun getUserReviewList(email: String, pageable: Pageable): UserReviewPaginationResponseModel {
        val page = reviewRepository.findByUser(pageable, email)
        val reviews = ReviewResponseModel.of(page.content)

        return UserReviewPaginationResponseModel(
            reviews,
            page.totalPages
        )
    }

}
