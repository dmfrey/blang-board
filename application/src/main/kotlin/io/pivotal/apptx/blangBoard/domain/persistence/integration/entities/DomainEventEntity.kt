package io.pivotal.apptx.blangBoard.domain.persistence.integration.entities

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table( name = "domain_event" )
data class DomainEventEntity(
        @Id val id: String = "",
        val occurredOn: Timestamp = Timestamp.from( Instant.now() ),
        @Lob val data: String = "",
        val projectKey: String = ""
)