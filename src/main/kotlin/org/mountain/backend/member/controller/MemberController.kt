package org.mountain.backend.member.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.member.dto.UserInfoResponse
import org.mountain.backend.member.dto.UserInfoUpdateModel
import org.mountain.backend.member.dto.UserMountainResponseModel
import org.mountain.backend.member.dto.UserMountainUpdateModel
import org.mountain.backend.member.service.UserApiService
import org.mountain.backend.member.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberController @Autowired constructor(
    private val userService: UserService,
    private val userApiService: UserApiService
) {

    @GetMapping("/{member_email}")
    fun getUserInfo(@PathVariable("member_email") memberEmail: String): ResponseEntity<UserInfoResponse> {
        return ResponseEntityWrapper.ok(userApiService.getUserInfo(memberEmail))
    }

    @PutMapping
    fun updateUserInfo(
        userInfoUpdateModel: UserInfoUpdateModel,
        @AuthenticationPrincipal user: org.springframework.security.core.userdetails.User
    ): ResponseEntity<Any> {
        userApiService.updateUser(userInfoUpdateModel, user)
        return ResponseEntityWrapper.ok()
    }

}