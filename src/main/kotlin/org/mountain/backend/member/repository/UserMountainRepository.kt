package org.mountain.backend.member.repository

import org.mountain.backend.member.domain.UserMountain
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserMountainRepository : JpaRepository<UserMountain, Long> {
}