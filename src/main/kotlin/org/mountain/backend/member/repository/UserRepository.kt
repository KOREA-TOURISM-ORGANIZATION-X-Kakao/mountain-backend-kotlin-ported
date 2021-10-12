package org.mountain.backend.member.repository

import org.mountain.backend.member.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Query("SELECT DISTINCT u FROM User u " +
        "LEFT JOIN FETCH u.userSavedMountains " +
        "WHERE u.id = :id")
fun UserRepository.findByEmail(id: String): User? = findById(id).orElse(null)

@Repository
interface UserRepository : JpaRepository<User, String> {

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.userSavedMountains " +
            "WHERE u.username = :username")
    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.userSavedMountains " +
            "WHERE u.id = :id AND u.username = :username")
    fun findByEmailAndUsername(id: String, username: String): User?

}