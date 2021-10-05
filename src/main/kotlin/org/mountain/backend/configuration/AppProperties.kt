package org.mountain.backend.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import kotlin.properties.Delegates

@ConfigurationProperties(prefix = "app")
class AppProperties {

    val auth = Auth()

    inner class Auth {
        lateinit var tokenSecret: String
        var tokenExpirationSeconds: Long by Delegates.notNull<Long>()
    }

}