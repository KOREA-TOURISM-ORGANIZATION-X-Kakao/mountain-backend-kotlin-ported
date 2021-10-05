package org.mountain.backend.authentication.service

import org.mountain.backend.authentication.model.SignupModel
import org.springframework.stereotype.Service

@Service
class AuthenticationService (
    val userService: UserService
) {

    fun signup(signupModel: SignupModel): Boolean {
        if(userService.isExistsUser(signupModel.email)) {
            return false
        }

        userService.saveUser(signupModel)
        return true
    }

}