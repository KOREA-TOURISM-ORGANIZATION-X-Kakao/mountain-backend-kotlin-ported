package org.mountain.backend.authentication.service

import org.mountain.backend.authentication.dto.JwtResponseModel
import org.mountain.backend.authentication.dto.SigninModel
import org.mountain.backend.authentication.dto.SignupModel
import org.springframework.stereotype.Service

@Service
class AuthenticationService (
    val userService: UserService
) {

    fun signup(signupModel: SignupModel): Boolean {
        if(userService.isExistsEmail(signupModel.email)) {
            return false
        }

        userService.saveUser(signupModel)
        return true
    }

    fun signin(signinModel: SigninModel): JwtResponseModel? {
        if(userService.validation(signinModel)) {
            return userService.generateJwt(signinModel)
        }

        return null
    }

}