package org.mountain.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MountainApplication

fun main(args: Array<String>) {
    runApplication<MountainApplication>(*args)
}