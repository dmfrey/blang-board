package io.pivotal.apptx.blangBoard.domain.persistence.integration.config

import io.pivotal.apptx.blangBoard.domain.persistence.integration.entities.DomainEventEntity
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters

@Configuration
@EntityScan(
        basePackageClasses = arrayOf( DomainEventEntity::class, Jsr310JpaConverters::class )
)
class DataConfig {

}
