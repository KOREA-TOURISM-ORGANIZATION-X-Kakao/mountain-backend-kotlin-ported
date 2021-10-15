package org.mountain.backend.mountain.service

import org.mountain.backend.mountain.repository.LocationRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MountainLocationService(
    val locationRepository: LocationRepository
) {

    fun searchLocation(keyword: String): List<String> {
        return locationRepository.searchLocation(Pageable.ofSize(10), keyword).content
    }

}