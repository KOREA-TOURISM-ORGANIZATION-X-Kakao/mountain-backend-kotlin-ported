package org.mountain.backend

import org.mountain.backend.configuration.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties::class)
@Controller
class MountainApplication {

    @GetMapping("/")
    fun index(): String {
        return "index.html"
    }

}

fun main(args: Array<String>) {
    runApplication<MountainApplication>(*args)
}
