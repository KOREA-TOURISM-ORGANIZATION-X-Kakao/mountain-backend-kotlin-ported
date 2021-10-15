package org.mountain.backend.mountain.dto

import org.mountain.backend.mountain.domain.Mountain

data class MountainSearchModelFilter(
    val mountainName: String,
    val location: String?,
    val difficulty: String?,
    val currentPage: Int,
    val dataSize: Int
)

data class MountainSearchResponse(
    val mountains: List<Mountain>,
    val totalPage: Int
)