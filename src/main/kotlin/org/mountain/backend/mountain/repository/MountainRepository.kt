package org.mountain.backend.mountain.repository

import org.mountain.backend.member.domain.User
import org.mountain.backend.mountain.domain.Mountain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

fun MountainRepository.findByMountainCode(id: String): Mountain? = findById(id).orElse(null)

@Repository
interface MountainRepository : JpaRepository<Mountain, String> {

    @Query("SELECT m FROM Mountain m" +
            " WHERE m.id = (SELECT u.mountainCode FROM UserMountain u WHERE u.user = :user)")
    fun findByUser(pageable: Pageable, user: User): Page<Mountain>

}