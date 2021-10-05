package org.mountain.backend

import org.mountain.backend.configuration.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties::class)
open class MountainApplication

fun main(args: Array<String>) {
    runApplication<MountainApplication>(*args)
}
