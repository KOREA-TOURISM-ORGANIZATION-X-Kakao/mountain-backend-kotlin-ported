package org.mountain.backend.mountain.repository

import org.mountain.backend.mountain.domain.Mountain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<Mountain, String> {

    @Query("SELECT DISTINCT m.location FROM Mountain m " +
            "WHERE m.location LIKE %:keyword%")
    fun searchLocation(pageable: Pageable, keyword: String): Page<String>

}