package org.mountain.backend

import org.mountain.backend.configuration.AppProperties
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties::class)
class MountainApplication

fun main(args: Array<String>) {
    runApplication<MountainApplication>(*args)
}
