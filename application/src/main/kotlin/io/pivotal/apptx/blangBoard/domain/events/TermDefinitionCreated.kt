package io.pivotal.apptx.blangBoard.domain.events

import java.time.Instant
import java.util.*

data class TermDefinitionCreated(
        val termDefinitionUuid: UUID,
        val definition: String,
        val teamKey: String,
        val termUuid: UUID,
        override val occurredOn: Instant,
        override val key: String
): DomainEvent( key, occurredOn ) {

    override fun eventType(): String = "TermDefinitionCreated"

}
