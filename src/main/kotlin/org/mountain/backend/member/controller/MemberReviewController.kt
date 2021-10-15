package org.mountain.backend.member.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.member.service.UserApiService
import org.mountain.backend.review.dto.ReviewUpdateByUserModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

}