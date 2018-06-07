package io.pivotal.apptx.blangBoard.domain.common

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class TimestampGenerator {

    fun generate() = Instant.now()

}