package org.mountain.backend.authentication.domain

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.common.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User (
    username: String,
    email: String,
    password: String,
    authority: Authority
) : BaseTimeEntity() {

    @Id
    @Column(name = "id")
    var email: String = email

    var username: String = username
    var password: String = password
    var authority: Authority = authority

}