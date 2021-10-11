package org.mountain.backend.mountain.repository

import org.mountain.backend.mountain.domain.Mountain
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MountainRepository : JpaRepository<Mountain, String> {
}