package org.mountain.backend.authentication.repository

import org.mountain.backend.authentication.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {

    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean

    fun findByEmail(email: String): User?

}