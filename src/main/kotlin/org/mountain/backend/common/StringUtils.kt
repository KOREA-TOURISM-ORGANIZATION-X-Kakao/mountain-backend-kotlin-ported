package org.mountain.backend.common

import kotlin.text.StringBuilder

class StringUtils {

    companion object {
        private val stringBuilder: StringBuilder = StringBuilder()

        fun trimAtStringLast(value: String): String {
            stringBuilder.append(value)
            val length = value.length

            for(i in length - 1 downTo 0 step(1)) {
                if(stringBuilder.get(i).isWhitespace()) {
                    stringBuilder.deleteCharAt(i)
                }
                else {
                    break
                }
            }
            val result  = stringBuilder.toString()
            stringBuilder.clear()

            return result
        }
    }

}