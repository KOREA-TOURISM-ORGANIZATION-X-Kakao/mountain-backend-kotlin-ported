package org.mountain.backend.member.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.authentication.type.RegistrationType
import org.mountain.backend.common.BaseTimeEntity
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

    constructor(email: String) : this("", email, "", "", Authority.ROLE_USER, RegistrationType.EMAIL)

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
}

@Entity
class UserMountain(
    user: User,
    mountainCode: String
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @get:JoinColumn(name = "user_id")
    var user = user

    var mountainCode = mountainCode

    // 현재 연관관계에서 데이터를 꺼내올 때 StackOverFlow가 발생하는 이슈가 있는데 equals(), hashcode()를 재정의 해주면 해결된다.
    /*
    *   1. Parent 객체를 생성한다.
        2. Parent 객체에 종속된 Child 객체를 생성해서 Parent.childs(MutableSet) 필드에 추가한다.
        3. Child 객체를 하나 더 생성해서 마찬가지로 Parent.childs 필드에 추가한다.
        4. 이 때 childs 필드에 이미 객체가 존재하므로 추가하려는 객체와 동일한 객체인지 확인한다. - 이 때 에러 발생
        5. 동일한 객체가 아닐 경우 Parent.childs 필드에 객체를 추가한다.

        set collection에서 중복 확인을 할 때 equals(), hashcode()를 이용해서 하기때문에 Parent.childs -> child.parent -> parent.childs
        와 같은 순환참조가 발생하여 stack over flow발생
    * */
    override fun equals(other: Any?): Boolean {
        return this.id == (other as UserMountain).id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }

}
