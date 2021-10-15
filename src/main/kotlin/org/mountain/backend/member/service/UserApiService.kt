package org.mountain.backend.member.service

import org.mountain.backend.authentication.type.RegistrationType
import org.mountain.backend.exception.ApiException
import org.mountain.backend.exception.ErrorType
import org.mountain.backend.member.domain.User
import org.mountain.backend.member.domain.UserMountain
import org.mountain.backend.member.dto.*
import org.mountain.backend.mountain.dto.MountainResponseModel
import org.mountain.backend.mountain.repository.MountainRepository
import org.mountain.backend.review.dto.ReviewResponseModel
import org.mountain.backend.review.dto.ReviewUpdateByUserModel
import org.mountain.backend.review.dto.UserReviewPaginationResponseModel
import org.mountain.backend.review.dto.UserReviewRequestModel
import org.mountain.backend.review.service.UserReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserApiService @Autowired constructor(
    private val userService: UserService,
    private val mountainRepository: MountainRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userReviewService: UserReviewService
) {

    fun isProperUserRequest(
        email: String,
        principle: org.springframework.security.core.userdetails.User
    ): Boolean {
        val user: User = userService.getUserByEmail(email)
        // TODO: 보안 강화해야함

        return when(user.registrationType) {
            RegistrationType.EMAIL -> principle.username == user.email
            RegistrationType.KAKAO -> principle.username == user.email
            RegistrationType.NAVER -> principle.username == user.email
            else -> false
        }
    }

    private fun updateUser(userInfoUpdateModel: UserInfoUpdateModel) {
        val user: User = userService.getUserByEmail(userInfoUpdateModel.email)

        user.password = passwordEncoder.encode(userInfoUpdateModel.password)
        user.username = userInfoUpdateModel.username

        userService.saveUser(user)
    }

    @Transactional
    fun updateUser(userInfoUpdateModel: UserInfoUpdateModel, user: org.springframework.security.core.userdetails.User) {
        if(isProperUserRequest(userInfoUpdateModel.email, user)) {
            updateUser(userInfoUpdateModel)
        }
        else {
            throw ApiException(ErrorType.ACCESS_DENIED, "잘못된 접근입니다.")
        }
    }

    @Transactional
    fun getUserSavedMountain(email: String): UserMountainResponseModel {
        val user: User = userService.getUserByEmail(email)

        return UserMountainResponseModel(mountainRepository.findByUser(user))
    }

    @Transactional
    fun addMountain(mountainUpdateModel: UserMountainUpdateModel) {
        val user: User = userService.getUserByEmail(mountainUpdateModel.email)
        user.userSavedMountains.add(UserMountain(user, mountainUpdateModel.mountainCode))
        userService.saveUser(user)
    }

    @Transactional
    fun deleteMountain(mountainUpdateModel: UserMountainUpdateModel) {
        val user: User = userService.getUserByEmail(mountainUpdateModel.email)
        user.userSavedMountains.removeIf { it.mountainCode == mountainUpdateModel.mountainCode }
        userService.saveUser(user)
    }

    fun getUserInfo(email: String): UserInfoResponse {
        val user: User = userService.getUserByEmail(email)

        return UserInfoResponse(
            user.email,
            user.username,
            user.modifiedDate.format(simpleDateFormat)
        )
    }

    fun getAllUserInfo(email: String): UserInfoResponseModel {
        val user: User = userService.getUserByEmail(email)
        val mountains = mountainRepository.findByUser(user)

        return UserInfoResponseModel(
            user.email,
            user.username,
            MountainResponseModel.of(mountains),
            ReviewResponseModel.of(user.reviews)
        )
    }

    @Transactional
    private fun updateReview(reviewUpdateByUserModel: ReviewUpdateByUserModel) {
        val user = userService.getUserByEmail(reviewUpdateByUserModel.email)
        val review = user.reviews.find { it.id == reviewUpdateByUserModel.reviewId }
            ?: throw ApiException(ErrorType.RUNTIME_EXCEPTION, "리뷰가 없습니다.")
        user.reviews.removeIf { it.id == reviewUpdateByUserModel.reviewId }

        review.comment = reviewUpdateByUserModel.comment
        review.grade = reviewUpdateByUserModel.grade

        user.reviews.add(review)

        userService.saveUser(user)
    }

    fun updateReview(reviewUpdateByUserModel: ReviewUpdateByUserModel, user: org.springframework.security.core.userdetails.User) {
        if(isProperUserRequest(reviewUpdateByUserModel.email, user)) {
            updateReview(reviewUpdateByUserModel)
        }
        else {
            throw ApiException(ErrorType.ACCESS_DENIED, "잘못된 접근입니다.")
        }
    }

    fun getUserReview(userReviewRequestModel: UserReviewRequestModel): UserReviewPaginationResponseModel {
        return userReviewService.getUserReviewList(
            userReviewRequestModel.email,
            PageRequest.of(userReviewRequestModel.currentPage, userReviewRequestModel.dataSize)
        )
    }

}