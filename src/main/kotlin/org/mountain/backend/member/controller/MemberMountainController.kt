package org.mountain.backend.member.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.member.dto.UserMountainResponseModel
import org.mountain.backend.member.dto.UserMountainUpdateModel
import org.mountain.backend.member.service.UserApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberMountainController(
    private val userApiService: UserApiService
)  {

    @GetMapping("/{member_email}/mountain")
    fun getUserMountains(@PathVariable("member_email") memberEmail: String): ResponseEntity<UserMountainResponseModel> {
        return ResponseEntity.ok(userApiService.getUserSavedMountain(memberEmail))
    }

    @PostMapping("/mountain")
    fun saveUserMountain(userMountainUpdateModel: UserMountainUpdateModel): ResponseEntity<Any> {
        userApiService.addMountain(userMountainUpdateModel)
        return ResponseEntityWrapper.ok()
    }

    @DeleteMapping("/mountain")
    fun deleteUserMountain(userMountainUpdateModel: UserMountainUpdateModel): ResponseEntity<Any> {
        userApiService.deleteMountain(userMountainUpdateModel)
        return ResponseEntityWrapper.ok()
    }

}