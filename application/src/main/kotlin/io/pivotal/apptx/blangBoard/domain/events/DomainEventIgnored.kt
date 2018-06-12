package io.pivotal.apptx.blangBoard.domain.events

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.Instant

@JsonPropertyOrder("eventType", "key", "occurredOn" )
@JsonIgnoreProperties( ignoreUnknown = true )
data class DomainEventIgnored(
        override val occurredOn: Instant,
        override val key: String
): DomainEvent( key, occurredOn ) {

    override fun eventType(): String = "DomainEventIgnored"

}