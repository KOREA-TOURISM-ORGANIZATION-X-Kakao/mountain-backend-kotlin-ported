package org.mountain.backend.authentication.service

import org.mountain.backend.authentication.model.SignupModel
import org.springframework.stereotype.Service

@Service
class UserService {

    fun isExistsUser(email: String): Boolean {
        return false
    }

    fun saveUser(signupModel: SignupModel) {

    }

}