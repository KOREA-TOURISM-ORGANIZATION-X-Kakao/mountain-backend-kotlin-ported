package org.mountain.backend.mountain.service

import org.mountain.backend.exception.ApiException
import org.mountain.backend.exception.ErrorType
import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.dto.MountainAttributeResponseModel
import org.mountain.backend.mountain.dto.MountainInformationResponseModel
import org.mountain.backend.mountain.dto.MountainResponseModel
import org.mountain.backend.mountain.repository.MountainRepository
import org.mountain.backend.mountain.repository.findByMountainCode
import org.mountain.backend.review.dto.ReviewResponseModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

interface MountainService {
    fun getMountainById(mountainCode: String): Mountain
    fun getMountainByIdAsDto(mountainCode: String): MountainInformationResponseModel
    fun saveMountain(mountain: Mountain)
}

@Service
@Qualifier("mountainService")
class MountainServiceImpl(
    val mountainRepository: MountainRepository
) : MountainService {

    override fun getMountainById(mountainCode: String): Mountain {
        return mountainRepository.findByMountainCode(mountainCode)
            ?: throw ApiException(ErrorType.RUNTIME_EXCEPTION, "산 정보가 없습니다.")
    }

    override fun getMountainByIdAsDto(mountainCode: String):  MountainInformationResponseModel {
        val mountain = getMountainById(mountainCode)

        return MountainInformationResponseModel(
            mountain.mountainCode,
            mountain.mountainName,
            MountainAttributeResponseModel.of(mountain.mountainAttributes),
            ReviewResponseModel.of(mountain.reviews)
        )
    }

    override fun saveMountain(mountain: Mountain) {
        mountainRepository.save(mountain)
    }

}