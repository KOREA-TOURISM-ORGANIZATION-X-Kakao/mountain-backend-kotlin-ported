package org.mountain.backend.mountain.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Mountain(
    mountainCode: String,
    mountainName: String,
    location: String
) {

    @Id
    @Column(name = "id")
    var mountainCode: String = mountainCode

    var mountainName: String = mountainName
    var location: String = location

    @JsonIgnore
    @OneToMany(mappedBy = "mountain", cascade = [CascadeType.ALL], orphanRemoval = true)
    var mountainAttributes: MutableSet<MountainAttribute> = mutableSetOf()
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
class HikingTrail(
    mountainAttribute: MountainAttribute,
    latitude: Double,
    longitude: Double
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.EAGER)
    var mountainAttribute = mountainAttribute

    var latitude = latitude
    var longitude = longitude
}

@Entity
class MountainAttribute(
    mountain: Mountain,
    loadLength: Double, //PMNTN_LT
    difficulty: String, // PMNTN_DFFL
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.EAGER)
    var mountain = mountain

    var loadLength = loadLength
    var difficulty = difficulty

    @OneToMany(mappedBy = "mountainAttribute", cascade = [CascadeType.ALL], orphanRemoval = true)
    var hikingTrails: MutableSet<HikingTrail> = mutableSetOf()
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