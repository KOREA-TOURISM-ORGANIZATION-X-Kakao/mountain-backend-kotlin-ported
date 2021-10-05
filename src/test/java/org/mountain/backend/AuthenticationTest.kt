package org.mountain.backend

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mountain.backend.authentication.model.SignupModel
import org.mountain.backend.authentication.service.AuthenticationService
import org.mountain.backend.authentication.service.UserService

@ExtendWith(MockitoExtension::class)
class AuthenticationTest {

    @Mock
    lateinit var userService: UserService

    @InjectMocks
    lateinit var authenticationService: AuthenticationService

    @DisplayName("signup 테스트")
    @Test
    fun authentication_registerUserTest() {
        //given
        val signupModel = SignupModel("남대영", "wsnam0507@gmail.com", "12345")
        //when
        Mockito.`when`(userService.isExistsUser("wsnam0507@gmail.com")).thenReturn(false)
        //then
        val isSuccessfully = authenticationService.signup(signupModel)

        Assertions.assertEquals(isSuccessfully, true)
    }

}