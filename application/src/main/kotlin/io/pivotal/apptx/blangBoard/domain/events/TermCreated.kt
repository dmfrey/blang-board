package io.pivotal.apptx.blangBoard.domain.events

import java.time.Instant
import java.util.*

data class TermCreated(
        val termUuid: UUID,
        val name: String,
        override val occurredOn: Instant,
        override val key: String
): DomainEvent( key, occurredOn ) {

    override fun eventType(): String = "TermCreated"

}