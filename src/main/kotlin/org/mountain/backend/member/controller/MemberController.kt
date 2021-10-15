package org.mountain.backend.member.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.member.dto.UserInfoResponseModel
import org.mountain.backend.member.dto.UserInfoUpdateModel
import org.mountain.backend.member.service.UserApiService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val userApiService: UserApiService
) {

    @GetMapping("/{member_email}")
    fun getUserInfo(@PathVariable("member_email") memberEmail: String): ResponseEntity<UserInfoResponseModel> {
        return ResponseEntityWrapper.ok(userApiService.getAllUserInfo(memberEmail))
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