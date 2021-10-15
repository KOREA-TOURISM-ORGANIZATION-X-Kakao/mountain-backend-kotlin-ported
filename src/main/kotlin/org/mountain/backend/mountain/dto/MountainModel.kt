package org.mountain.backend.mountain.dto

import org.mountain.backend.mountain.domain.HikingTrail
import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.domain.MountainAttribute
import org.mountain.backend.review.dto.ReviewResponseModel

data class MountainExcelFileModel(
    val no: String,
    val mountainName: String,
    val location: String,
    val mountainCode: String
)

data class MountainGeometry(
    val latitude: Double,
    val longitude: Double
)

data class MountainAttributeModel(
    val no: String,
    val loadLength: Double,
    val difficulty: String,
    val paths: List<MountainGeometry>
)

data class MountainInformationModel(
    val attributes: List<MountainAttributeModel>
)

data class MountainInformationResponseModel(
    val mountainCode: String,
    val mountainName: String,
    val mountainAttributes: List<MountainAttributeResponseModel>,
    val reviews: List<ReviewResponseModel>
)

data class MountainResponseModel(
    val mountainCode: String,
    val mountainName: String,
    val mountainAttributes: List<MountainAttributeResponseModel>
) {
    companion object {
        fun of(mountains: Collection<Mountain>): List<MountainResponseModel> {
            return mountains.map {
                MountainResponseModel(it.mountainCode, it.mountainName, MountainAttributeResponseModel.of(it.mountainAttributes))
            }.toList()
        }
    }
}

data class MountainAttributeResponseModel(
    val loadLength: Double,
    val difficulty: String,
    val trails: List<HikingTrailResponseModel>
) {
    companion object {
        fun of(attributes: Set<MountainAttribute>): List<MountainAttributeResponseModel> {
            return attributes.map {
                MountainAttributeResponseModel(it.loadLength, it.difficulty, HikingTrailResponseModel.of(it.hikingTrails))
            }.toList()
        }
    }
}

data class HikingTrailResponseModel(
    val latitude: Double,
    val longitude: Double
) {

    companion object {
        fun of(trails: Set<HikingTrail>): List<HikingTrailResponseModel> {
            return trails.map { HikingTrailResponseModel(it.latitude, it.longitude) }.toList()
        }
    }

}
