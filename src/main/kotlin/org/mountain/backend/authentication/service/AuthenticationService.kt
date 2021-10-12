package org.mountain.backend.authentication.service

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.member.domain.User
import org.mountain.backend.authentication.dto.JwtResponseModel
import org.mountain.backend.authentication.dto.SigninModel
import org.mountain.backend.authentication.dto.SignupModel
import org.mountain.backend.authentication.jwt.JwtProvider
import org.mountain.backend.member.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService @Autowired constructor(
    val userService: UserService,
    val jwtProvider: JwtProvider,
    val passwordEncoder: PasswordEncoder
) {

    fun signup(signupModel: SignupModel): Boolean {
        if(userService.isExistsEmail(signupModel.email)) {
            return false
        }

        userService.saveUser(signupModel.copy(password = passwordEncoder.encode(signupModel.password)))
        return true
    }

    fun signin(signinModel: SigninModel): JwtResponseModel? {
        val user: User = userService.getUserByEmail(signinModel.email)

        if(user != null && passwordEncoder.matches(signinModel.password, user.password)) {
            return JwtResponseModel(
                jwtProvider.generateToken(signinModel.email, Authority.ROLE_USER.name),
                ""
            )
        }

        return null
    }

}