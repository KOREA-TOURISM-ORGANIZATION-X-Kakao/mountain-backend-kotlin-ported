package org.mountain.backend.mountain.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.mountain.dto.MountainSearchModelFilter
import org.mountain.backend.mountain.dto.MountainSearchResponse
import org.mountain.backend.mountain.service.search.MountainSearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/mountain")
class MountainSearchController(
    val mountainSearchService: MountainSearchService
) {

    @GetMapping("/{mountain_name}/search")
    fun search(
        @PathVariable("mountain_name") mountainName: String,
        @RequestParam("currentPage") currentPage: String,
        @RequestParam("dataSize") dataSize: String,
        @RequestParam("location") locationFilter: String?,
        @RequestParam("difficulty") difficultyFilter: String?
    ): ResponseEntity<MountainSearchResponse> {
        val mountainSearchModelNameFilter = MountainSearchModelFilter(
            mountainName,
            locationFilter,
            difficultyFilter,
            currentPage.toInt(),
            dataSize.toInt()
        )

        return ResponseEntityWrapper.ok(mountainSearchService.search(mountainSearchModelNameFilter))
    }


}