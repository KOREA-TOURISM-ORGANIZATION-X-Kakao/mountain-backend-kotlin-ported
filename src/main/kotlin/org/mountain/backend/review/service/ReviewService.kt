package org.mountain.backend.review.service

import org.mountain.backend.member.domain.User
import org.mountain.backend.member.service.UserService
import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.service.MountainService
import org.mountain.backend.review.domain.Review
import org.mountain.backend.review.dto.ReviewPostModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService @Autowired constructor(
    val userService: UserService,
    @Qualifier("mountainService") val mountainService: MountainService,
) {

    @Transactional
    fun saveReview(reviewPostModel: ReviewPostModel) {
        val user: User = userService.getUserByEmail(reviewPostModel.email)
        val mountain: Mountain = mountainService.getMountainById(reviewPostModel.mountainCode)
        val review = Review(user, mountain, reviewPostModel.grade, reviewPostModel.comment)

        user.reviews.add(review)
        mountain.reviews.add(review)

        userService.saveUser(user)
        mountainService.saveMountain(mountain)
    }

}