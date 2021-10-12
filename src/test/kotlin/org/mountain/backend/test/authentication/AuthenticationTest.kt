package org.mountain.backend.test.authentication

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.member.domain.User
import org.mountain.backend.authentication.dto.JwtResponseModel
import org.mountain.backend.authentication.dto.SigninModel
import org.mountain.backend.authentication.dto.SignupModel
import org.mountain.backend.authentication.service.AuthenticationService
import org.mountain.backend.member.service.UserService
import org.mountain.backend.authentication.type.RegistrationType

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
        Mockito.`when`(userService.isExistsEmail("wsnam0507@gmail.com")).thenReturn(false)
        //then
        val isSuccessfully = authenticationService.signup(signupModel)

        Assertions.assertEquals(isSuccessfully, true)
    }

    @DisplayName("이메일 로그인 테스트")
    @Test
    fun authentication_loginEmailTest() {
        // given
        val signinModel = SigninModel("wsnam0507@gmail.com", "12345")

        // when
        Mockito.`when`(userService.getUserByEmail(signinModel.email)).thenReturn(getResultUserMock())

        // then
        val response: JwtResponseModel? = authenticationService.signin(signinModel)

        Assertions.assertEquals(response != null, true)
        Assertions.assertEquals(response!!.accessToken, "test-token")
    }

    fun getResultUserMock(): User {
        return User(
            "남대영",
            "wsnam0507@gmail.com",
            "12345", Authority.ROLE_USER,
            RegistrationType.EMAIL
        )
    }

}