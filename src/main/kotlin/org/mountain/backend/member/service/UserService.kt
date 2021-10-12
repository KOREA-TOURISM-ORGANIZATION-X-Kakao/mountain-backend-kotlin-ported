package org.mountain.backend.member.service

import org.mountain.backend.member.domain.User
import org.mountain.backend.authentication.dto.SignupModel
import org.mountain.backend.authentication.dto.toEntity
import org.mountain.backend.exception.ApiException
import org.mountain.backend.exception.ErrorType
import org.mountain.backend.member.repository.UserRepository
import org.mountain.backend.member.repository.findByEmail
import org.springframework.stereotype.Service
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
        return userRepository.findByEmail(email) ?: throw ApiException(ErrorType.RUNTIME_EXCEPTION, "유저를 찾을 수 없습니다.")
    }

    fun getUserByEmailAndUsername(email: String, username: String): User? {
        return userRepository.findByEmailAndUsername(email, username)
    }

}
