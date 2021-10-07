package org.mountain.backend.test.user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.authentication.domain.User
import org.mountain.backend.authentication.repository.UserRepository
import org.mountain.backend.authentication.service.UserService
import org.mountain.backend.authentication.type.RegistrationType

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @InjectMocks
    lateinit var userService: UserService

    @Mock
    lateinit var userRepository: UserRepository

    @DisplayName("username 존재여부 확인")
    @Test
    fun userService_existsUsernameTest() {
        // given
        val username: String = "남대영"

        // when
        Mockito.`when`(userRepository.existsByUsername(username)).thenReturn(true)

        // then
        val isExistsUsername = userService.isExistsUserName(username)

        Assertions.assertEquals(isExistsUsername, true)
    }

    @DisplayName("email 유저 존재 여부 확인")
    @Test
    fun userService_existsUserEmailTest() {
        // given
        val email: String = "wsnam0507@gmail.com"

        // when
        Mockito.`when`(userRepository.existsById(email)).thenReturn(true)

        // then
        val isExistsId = userService.isExistsEmail(email)

        Assertions.assertEquals(isExistsId, true)
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