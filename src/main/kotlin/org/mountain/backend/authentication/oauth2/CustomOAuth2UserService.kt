package org.mountain.backend.authentication.oauth2

import org.mountain.backend.authentication.authority.Authority
import org.mountain.backend.authentication.dto.OAuth2Attributes
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

class CustomOAuth2User(
    authorities: Collection<out GrantedAuthority>,
    attributes: Map<String, Any>,
    nameAttributeKey: String,
    registrationId: String
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey) {
    var registrationId: String = registrationId
}

@Service
class CustomOAuth2UserService : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val user: OAuth2User = delegate.loadUser(userRequest)

        val registrationId: String = userRequest!!.clientRegistration.registrationId
        val usernameAttributeName: String = userRequest!!.clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName
        val attribute: OAuth2Attributes = OAuth2Attributes.of(registrationId, usernameAttributeName, user.attributes)

        println(usernameAttributeName)
        println(attribute.attributes)

        return CustomOAuth2User(
            listOf(SimpleGrantedAuthority(Authority.ROLE_USER.name)),
            attribute.attributes,
            OAuth2Attributes.getNameAttributeKey(registrationId),
            registrationId
        )
    }

}