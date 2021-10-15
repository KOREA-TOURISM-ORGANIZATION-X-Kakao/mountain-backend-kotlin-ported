package org.mountain.backend.review.repository

import org.mountain.backend.review.domain.Review
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE r.user.id = :email")
    fun findByUser(pageable: Pageable, email: String): Page<Review>

}