package org.mountain.backend.review

import org.mountain.backend.member.domain.User
import org.mountain.backend.mountain.domain.Mountain
import javax.persistence.*

@Entity
class Review(
    user: User,
    mountain: Mountain,
    grade: Double,
    comment: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.EAGER)
    @get:JoinColumn(name = "user_id")
    val user = user

    @ManyToOne(fetch = FetchType.EAGER)
    @get:JoinColumn(name = "mountain_id")
    val mountain = mountain

    val grade = grade
    val comment = comment

}