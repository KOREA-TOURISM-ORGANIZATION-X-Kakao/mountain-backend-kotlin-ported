package org.mountain.backend.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties {

    val auth = Auth()

    inner class Auth {
        lateinit var tokenSecret: String
        lateinit var tokenExpirationSeconds: String
    }

}