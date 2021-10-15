package org.mountain.backend.mountain.service.search

import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.dto.MountainSearchModelFilter
import org.mountain.backend.mountain.dto.MountainSearchResponse
import org.mountain.backend.mountain.repository.MountainSearchRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class MountainSearchService(
    val mountainRepository: MountainSearchRepository
) {

    fun searchByName(mountainNameFilter: MountainSearchModelFilter): MountainSearchResponse {
        val pageable = PageRequest.of(mountainNameFilter.currentPage, mountainNameFilter.dataSize)
        val page = mountainRepository.searchByMountainName(pageable, mountainNameFilter.mountainName)

        return MountainSearchResponse(
            page.content,
            page.totalPages
        )
    }

    fun search(mountainSearchModelFilter: MountainSearchModelFilter): MountainSearchResponse {
        val pageable = PageRequest.of(mountainSearchModelFilter.currentPage, mountainSearchModelFilter.dataSize)
        var page: Page<Mountain>

        if(mountainSearchModelFilter.location != null && mountainSearchModelFilter.difficulty != null) {
            page = mountainRepository.searchAllFilter(
                pageable,
                mountainSearchModelFilter.mountainName,
                mountainSearchModelFilter.location,
                mountainSearchModelFilter.difficulty
            )
        }
        else if(mountainSearchModelFilter.location != null) {
            page = mountainRepository.searchByMountainNameAndLocation(
                pageable,
                mountainSearchModelFilter.mountainName,
                mountainSearchModelFilter.location
            )
        }
        else if(mountainSearchModelFilter.difficulty != null) {
            page = mountainRepository.searchByMountainNameAndDifficulty(
                pageable,
                mountainSearchModelFilter.mountainName,
                mountainSearchModelFilter.difficulty
            )
        }
        else {
            page = mountainRepository.searchByMountainName(pageable, mountainSearchModelFilter.mountainName)
        }

        return MountainSearchResponse(
            page.content,
            page.totalPages
        )
    }

}