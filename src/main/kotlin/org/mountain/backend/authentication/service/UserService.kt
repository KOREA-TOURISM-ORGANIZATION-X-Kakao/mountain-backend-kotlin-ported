package org.mountain.backend.authentication.service

import org.mountain.backend.authentication.domain.User
import org.mountain.backend.authentication.dto.SignupModel
import org.mountain.backend.authentication.dto.toEntity
import org.mountain.backend.authentication.repository.UserRepository
import org.springframework.stereotype.Service

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
        return userRepository.findById(email).orElseGet(null)
    }

    fun getUserByEmailAndUsername(email: String, username: String): User? {
        return userRepository.findByEmailAndUsername(email, username)
    }

}