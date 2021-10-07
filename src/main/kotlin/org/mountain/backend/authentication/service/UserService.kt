package org.mountain.backend.authentication.service

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.authentication.domain.User
import org.mountain.backend.authentication.dto.JwtResponseModel
import org.mountain.backend.authentication.dto.SigninModel
import org.mountain.backend.authentication.dto.SignupModel
import org.mountain.backend.authentication.jwt.JwtProvider
import org.mountain.backend.authentication.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtProvider: JwtProvider
) {

    fun isExistsEmail(email: String): Boolean {
        return userRepository.existsById(email)
    }

    fun isExistsUserName(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun saveUser(signupModel: SignupModel) {
        val user: User = signupModel.toEntity()
        user.password = passwordEncoder.encode(signupModel.password)

        userRepository.save(user)
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun validation(signinModel: SigninModel): Boolean {
        val user = getUserByEmail(signinModel.email)

        if(user != null) {
            return passwordEncoder.matches(signinModel.password, user.password)
        }

        return false
    }

    fun generateJwt(signinModel: SigninModel): JwtResponseModel? {
        val user = getUserByEmail(signinModel.email)

        if(user != null) {
            return generateJwt(user)
        }

        return null
    }

    private fun generateJwt(user: User): JwtResponseModel {
        val accessToken = jwtProvider.generateToken(user.email, Authority.ROLE_USER.name)

        return JwtResponseModel(accessToken, "")
    }

}