package org.mountain.backend.member.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.authentication.type.RegistrationType
import org.mountain.backend.common.BaseTimeEntity
import org.mountain.backend.review.domain.Review
import javax.persistence.*

@Entity
class User (
    username: String,
    email: String,
    password: String,
    profileImage: String,
    authority: Authority,
    registrationType: RegistrationType
) : BaseTimeEntity() {

    @Id
    @Column(name = "id")
    var email: String = email

    var username: String = username
    var password: String = password
    var profileImage: String = profileImage
    var authority: Authority = authority
    var registrationType: RegistrationType = registrationType

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var userSavedMountains: MutableSet<UserMountain> = mutableSetOf()
        set(value) {
            if(field == null) {
                field = value
            }
            else {
                field.clear()
                field.addAll(value)
            }
        }

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reviews: MutableSet<Review> = mutableSetOf()
        set(value) {
            if(field == null) {
                field = value
            }
            else {
                field.clear()
                field.addAll(value)
            }
        }

}

@Entity
class UserMountain(
    user: User,
    mountainCode: String
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonIgnore // Jackson이 Json으로 변환하는 과정에서 연관관계 때문에 stackoverflow가 발생하는 이슈가 있었음
    @ManyToOne(fetch = FetchType.EAGER)
    @get:JoinColumn(name = "user_id")
    var user = user

    var mountainCode = mountainCode

    override fun equals(other: Any?): Boolean {
        return this.id == (other as UserMountain).id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }

}
