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

@Repository
interface MountainSearchRepository : JpaRepository<Mountain, String> {

    @Query("SELECT m FROM Mountain m " +
            "WHERE m.mountainName LIKE %:nameFilter% AND m.location LIKE %:locationFilter%")
    fun searchByMountainNameAndLocation(pageable: Pageable, nameFilter: String, locationFilter: String): Page<Mountain>

    @Query("SELECT DISTINCT m FROM Mountain m " +
            "LEFT JOIN MountainAttribute a ON a.mountain = m AND difficulty = :difficultyFilter " +
            "WHERE m.location LIKE %:locationFilter%")
    fun searchByLocationAndDifficulty(pageable: Pageable, locationFilter: String, difficultyFilter: String): Page<Mountain>

    @Query("SELECT DISTINCT m FROM Mountain m " +
            "LEFT JOIN MountainAttribute a ON a.mountain = m AND difficulty = :difficultyFilter " +
            "WHERE m.mountainName LIKE %:nameFilter%")
    fun searchByMountainNameAndDifficulty(pageable: Pageable, nameFilter: String, difficultyFilter: String): Page<Mountain>

    @Query("SELECT m FROM Mountain m " +
            "WHERE m.location LIKE %:locationFilter%")
    fun searchByLocation(pageable: Pageable, locationFilter: String): Page<Mountain>

    @Query("SELECT m FROM Mountain m " +
            "LEFT JOIN MountainAttribute a ON a.mountain = m AND a.difficulty = :difficultyFilter")
    fun searchByDifficulty(pageable: Pageable, difficultyFilter: String): Page<Mountain>

    @Query("SELECT m FROM Mountain m" +
            " WHERE m.mountainName LIKE %:nameFilter%")
    fun searchByMountainName(pageable: Pageable, nameFilter: String): Page<Mountain>

    @Query("SELECT DISTINCT m FROM Mountain m " +
            "LEFT JOIN MountainAttribute a ON a.mountain = m AND a.difficulty = :difficultyFilter " +
            "WHERE m.mountainName LIKE %:nameFilter% AND m.location LIKE %:locationFilter%")
    fun searchAllFilter(pageable: Pageable, nameFilter: String, locationFilter: String, difficultyFilter: String): Page<Mountain>

}