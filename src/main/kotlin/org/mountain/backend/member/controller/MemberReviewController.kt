package org.mountain.backend.member.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.member.service.UserApiService
import org.mountain.backend.review.dto.ReviewUpdateByUserModel
import org.mountain.backend.review.dto.UserReviewPaginationResponseModel
import org.mountain.backend.review.dto.UserReviewRequestModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberReviewController(
    val userApiService: UserApiService
) {

    @PutMapping("/review")
    fun updateUserReview(
        reviewUpdateByUserModel: ReviewUpdateByUserModel,
        @AuthenticationPrincipal user: org.springframework.security.core.userdetails.User
    ): ResponseEntity<Any> {
        userApiService.updateReview(reviewUpdateByUserModel, user)
        return ResponseEntityWrapper.ok()
    }

    @GetMapping("/{member_email}/review")
    fun getUserReview(
        @PathVariable("member_email") email: String,
        @RequestParam("currentPage", defaultValue = "0") currentPage: String,
        @RequestParam("dataSize", defaultValue = "5") dataSize: String
    ): ResponseEntity<UserReviewPaginationResponseModel> {
        val userReviewRequestModel = UserReviewRequestModel(
            email = email,
            currentPage = currentPage.toInt(),
            dataSize = dataSize.toInt()
        )

        return ResponseEntityWrapper.ok(
            userApiService.getUserReview(userReviewRequestModel)
        )
    }

}