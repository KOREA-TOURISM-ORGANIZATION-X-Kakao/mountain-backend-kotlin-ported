package org.mountain.backend.mountain.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.mountain.dto.MountainInformationResponseModel
import org.mountain.backend.mountain.dto.MountainResponseModel
import org.mountain.backend.mountain.service.MountainService
import org.mountain.backend.mountain.service.MountainServiceImpl
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/mountain")
class MountainController(
    @Qualifier("mountainService") val mountainService: MountainService
){

    @GetMapping("/{mountain_code}")
    fun getMountainInfo(@PathVariable("mountain_code") mountainCode: String): ResponseEntity<MountainInformationResponseModel> {
        return ResponseEntityWrapper.ok(mountainService.getMountainByIdAsDto(mountainCode))
    }

}