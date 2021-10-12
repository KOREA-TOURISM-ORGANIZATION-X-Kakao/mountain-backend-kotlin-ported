package org.mountain.backend.member.service

import org.mountain.backend.member.domain.User
import org.mountain.backend.authentication.dto.SignupModel
import org.mountain.backend.authentication.dto.toEntity
import org.mountain.backend.authentication.type.RegistrationType
import org.mountain.backend.exception.ApiException
import org.mountain.backend.exception.ErrorType
import org.mountain.backend.member.domain.UserMountain
import org.mountain.backend.member.dto.UserInfoResponse
import org.mountain.backend.member.dto.UserInfoUpdateModel
import org.mountain.backend.member.dto.UserMountainResponseModel
import org.mountain.backend.member.dto.UserMountainUpdateModel
import org.mountain.backend.member.repository.UserMountainRepository
import org.mountain.backend.member.repository.UserRepository
import org.mountain.backend.mountain.repository.MountainRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

val simpleDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")

@Service
class UserService (
    val userRepository: UserRepository,
) {

    fun isExistsEmail(email: String): Boolean {
        return userRepository.existsById(email)
    }

    fun isExistsUserName(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun saveUser(signupModel: SignupModel) {
        val user: User = signupModel.toEntity()

        saveUser(user)
    }

    fun saveUser(user: User) {
        userRepository.save(user)
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmails(email) ?: throw ApiException(ErrorType.RUNTIME_EXCEPTION, "유저를 찾을 수 없습니다.")
    }

    fun getUserByEmailAndUsername(email: String, username: String): User? {
        return userRepository.findByEmailAndUsername(email, username)
    }

}

@Service
class UserApiService(
    val userService: UserService,
    val userMountainRepository: UserMountainRepository,
    val mountainRepository: MountainRepository,
    val passwordEncoder: PasswordEncoder
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

}