package org.mountain.backend.authentication.dto

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.member.domain.User
import org.mountain.backend.authentication.type.RegistrationType
import java.lang.RuntimeException

fun <K, V> Map<K, V>.toMutableMap(): MutableMap<K, V> {
    return HashMap(this)
}

fun OAuth2Attributes.toEntity(): User {
    return User(name, email, "", picture, Authority.ROLE_USER, RegistrationType.valueOf(registrationId.uppercase()))
}

data class OAuth2Attributes(
    val attributes: Map<String, Any>,
    val nameAttributeKey: String,
    val email: String,
    val picture: String,
    val name: String,
    val registrationId: String
) {
    companion object {
        fun of(
            registrationId: String,
            nameAttributeKey: String,
            attributes: Map<String, Any>
        ): OAuth2Attributes {
            println(attributes)
            when(registrationId) {
                "kakao" -> return ofKakao(nameAttributeKey, attributes)
                "naver" -> return ofNaver(nameAttributeKey, attributes)
                else -> throw RuntimeException("지원하지 않는 인증 서비스입니다. [ $registrationId ]")
            }
        }

        fun getAttribute(nameAttributeKey: String, attributes: Map<String, Any>): MutableMap<String, Any> {
            var map: Map<String, Any>
            var value: Any? = attributes[nameAttributeKey]

            map = when(value) {
                is Map<*, *> -> value as Map<String, Any>
                else -> HashMap()
            }

            return map.toMutableMap()
        }

        fun ofNaver(nameAttributeKey: String, attributes: Map<String, Any>): OAuth2Attributes {
            val map = getAttribute(nameAttributeKey, attributes)

            return OAuth2Attributes(
                registrationId = "naver",
                nameAttributeKey = nameAttributeKey,
                name = map["nickname"] as String,
                email = map["email"] as String,
                attributes = map,
                picture = map["profile_image"] as String
            )
        }

        fun ofKakao(nameAttributeKey: String, attributes: Map<String, Any>): OAuth2Attributes {
            var properties: MutableMap<String, Any> = getAttribute("properties", attributes)
            val account = getAttribute("kakao_account", attributes)
            properties["email"] = account["email"] as String

            return OAuth2Attributes(
                registrationId = "kakao",
                nameAttributeKey = nameAttributeKey,
                name = properties["nickname"] as String,
                picture = properties["profile_image"] as String,
                email = account["email"] as String,
                attributes = properties
            )
        }

        fun getNameAttributeKey(registrationId: String): String {
            return when(registrationId) {
                "kakao" -> "nickname"
                "naver" -> "nickname"
                "facebook" -> "username"
                else -> throw RuntimeException("지원하지 않는 서비스 이름 입니다.")
            }
        }
    }
}
