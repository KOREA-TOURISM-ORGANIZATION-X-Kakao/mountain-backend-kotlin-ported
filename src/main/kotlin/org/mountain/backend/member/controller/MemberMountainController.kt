package org.mountain.backend.member.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.member.dto.UserMountainPaginationRequestModel
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
    fun getUserMountains(
        @PathVariable("member_email") memberEmail: String,
        @RequestParam(value = "currentPage", defaultValue = "0") currentPage: String,
        @RequestParam(value = "dataSize", defaultValue = "5") dataSize: String
    ): ResponseEntity<UserMountainResponseModel> {
        val userMountainRequestModel = UserMountainPaginationRequestModel(
            memberEmail,
            currentPage.toInt(),
            dataSize.toInt()
        )
        return ResponseEntity.ok(userApiService.getUserSavedMountain(userMountainRequestModel))
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