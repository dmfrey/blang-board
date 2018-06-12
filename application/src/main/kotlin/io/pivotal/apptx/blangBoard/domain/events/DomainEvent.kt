package io.pivotal.apptx.blangBoard.domain.events

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.Instant

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType",
        defaultImpl = DomainEventIgnored::class
)
@JsonSubTypes(
        JsonSubTypes.Type( value = TermCreated::class, name = "TermCreated" ),
        JsonSubTypes.Type( value = TermDefinitionCreated::class, name = "TermDefinitionCreated" )
)
abstract class DomainEvent(
        open val key: String,
        open val occurredOn: Instant )
{

    @JsonIgnore
    abstract fun eventType(): String

}
