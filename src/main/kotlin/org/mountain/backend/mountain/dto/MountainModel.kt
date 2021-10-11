package org.mountain.backend.mountain.dto

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

data class MountainAttribute(
    val no: String, // MNTN_CODE
    val loadLength: Double, //PMNTN_LT
    val difficulty: String, // PMNTN_DFFL
    val paths: List<MountainGeometry>
)

data class MountainInformationModel(
    val attributes: List<MountainAttribute>
)
