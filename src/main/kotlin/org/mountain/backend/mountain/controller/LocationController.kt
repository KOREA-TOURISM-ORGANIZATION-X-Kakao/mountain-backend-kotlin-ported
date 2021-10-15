package org.mountain.backend.mountain.controller

import org.mountain.backend.common.ResponseEntityWrapper
import org.mountain.backend.mountain.service.MountainLocationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/location")
class LocationController(
    val locationService: MountainLocationService
) {

    @GetMapping
    fun getLocations(@RequestParam("keyword") keyword: String): ResponseEntity<List<String>> {
        return ResponseEntityWrapper.ok(locationService.searchLocation(keyword))
    }

}