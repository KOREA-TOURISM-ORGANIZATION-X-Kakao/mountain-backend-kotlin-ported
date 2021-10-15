package org.mountain.backend.review.domain

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
    var user = user

    @ManyToOne(fetch = FetchType.EAGER)
    @get:JoinColumn(name = "mountain_id")
    var mountain = mountain

    var grade = grade
    var comment = comment

}