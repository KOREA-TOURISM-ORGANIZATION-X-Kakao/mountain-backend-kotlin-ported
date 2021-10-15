package org.mountain.backend.test.mountain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mountain.backend.exception.ApiException
import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.repository.MountainRepository
import org.mountain.backend.mountain.repository.findByMountainCode
import org.mountain.backend.mountain.service.MountainServiceImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class MountainServiceTest {

    @InjectMocks
    lateinit var mountainService: MountainServiceImpl

    @Mock
    lateinit var mountainRepository: MountainRepository

    @DisplayName("산 코드로 산 도메인을 얻어오는데 null처리가 잘 되는지")
    @Test
    fun mountainService_getMountainByIdAndNullSafeTest() {
        // given
        val mountainCode = "mountain-test-code"

        //when
        Mockito.`when`(mountainRepository.findById(mountainCode)).thenReturn(Optional.empty())

        //then
        Assertions.assertThrows(ApiException::class.java) {
            mountainService.getMountainById(mountainCode)
        }
    }

}