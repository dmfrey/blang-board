package io.pivotal.apptx.blangBoard.domain.common

import org.springframework.stereotype.Component
import java.util.*

@Component
class UuidGenerator {

    fun generate() = UUID.randomUUID()

    fun generateString() = generate().toString()

}