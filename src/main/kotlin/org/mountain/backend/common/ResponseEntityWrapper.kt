package org.mountain.backend.common

import org.springframework.http.ResponseEntity

class ResponseEntityWrapper<T> {

    companion object {

        @JvmStatic
        fun <T> ok(value: T): ResponseEntity<T> {
            return ResponseEntity.ok(value)
        }

        @JvmStatic
        fun <T> ok(): ResponseEntity<T> {
            return ResponseEntity.ok().build()
        }

        @JvmStatic
        fun <T> badRequest(): ResponseEntity<T> {
            return ResponseEntity.badRequest().build();
        }

        @JvmStatic
        fun <T> notFound(): ResponseEntity<T> {
            return ResponseEntity.notFound().build();
        }

        @JvmStatic
        fun <T> unauthorized(): ResponseEntity<T> {
            return ResponseEntity.status(401).build();
        }

    }

}
