package org.mountain.backend.authentication.controller

import org.mountain.backend.authentication.model.SignupModel
import org.mountain.backend.authentication.service.AuthenticationService
import org.mountain.backend.common.ResponseEntityWrapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authentication")
class AuthenticationController(
    val authenticationService: AuthenticationService
) {

    @PostMapping("/signup")
    fun signup(signupModel: SignupModel): ResponseEntity<Boolean> {
        val isSuccessfullyRegistered = authenticationService.signup(signupModel)
        return if(isSuccessfullyRegistered) ResponseEntityWrapper.ok(isSuccessfullyRegistered)
               else                         ResponseEntityWrapper.badRequest()
    }

}