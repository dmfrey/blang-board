package io.pivotal.apptx.blangBoard.domain.events

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.Instant
import java.util.*

@JsonPropertyOrder("eventType", "key", "occurredOn", "name", "termUuid" )
@JsonIgnoreProperties( ignoreUnknown = true )
data class TermCreated(
        @JsonProperty( "termUuid" ) val termUuid: UUID,
        @JsonProperty( "name" ) val name: String,
        @JsonProperty( "occurredOn" ) override val occurredOn: Instant,
        @JsonProperty( "key" ) override val key: String
): DomainEvent( key, occurredOn ) {

    @JsonProperty( "eventType" )
    override fun eventType(): String = "TermCreated"

}