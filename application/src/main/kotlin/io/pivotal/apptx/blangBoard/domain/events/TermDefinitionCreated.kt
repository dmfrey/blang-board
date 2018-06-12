package io.pivotal.apptx.blangBoard.domain.events

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.Instant
import java.util.*

@JsonPropertyOrder("eventType", "key", "occurredOn", "termUuid", "teamKey", "termDefinitionUuid", "definition" )
@JsonIgnoreProperties( ignoreUnknown = true )
data class TermDefinitionCreated(
        @JsonProperty( "termDefinitionUuid" ) val termDefinitionUuid: UUID,
        @JsonProperty( "definition" ) val definition: String,
        @JsonProperty( "teamKey" ) val teamKey: String,
        @JsonProperty( "termUuid" ) val termUuid: UUID,
        @JsonProperty( "occurredOn" ) override val occurredOn: Instant,
        @JsonProperty( "key" ) override val key: String
): DomainEvent( key, occurredOn ) {

    @JsonProperty( "eventType" )
    override fun eventType(): String = "TermDefinitionCreated"

}
