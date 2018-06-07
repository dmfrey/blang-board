package io.pivotal.apptx.blangBoard.domain.events

import java.time.Instant

data class DomainEventIgnored(
        override val occurredOn: Instant,
        override val key: String
): DomainEvent( key, occurredOn ) {

    override fun eventType(): String = "DomainEventIgnored"

}