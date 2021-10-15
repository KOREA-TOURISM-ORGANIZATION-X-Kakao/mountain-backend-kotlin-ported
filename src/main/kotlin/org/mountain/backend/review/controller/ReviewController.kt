package org.mountain.backend.review.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.review.dto.ReviewPostModel
import org.mountain.backend.review.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/review")
class ReviewController(
    val reviewService: ReviewService
){

    @PostMapping
    fun writeReview(reviewPostModel: ReviewPostModel): ResponseEntity<Any> {
        reviewService.saveReview(reviewPostModel)
        return ResponseEntityWrapper.ok()
    }

}