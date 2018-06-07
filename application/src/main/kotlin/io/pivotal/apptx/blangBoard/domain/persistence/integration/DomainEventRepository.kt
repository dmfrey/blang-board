package io.pivotal.apptx.blangBoard.domain.persistence.integration

import io.pivotal.apptx.blangBoard.domain.persistence.integration.entities.DomainEventEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DomainEventRepository: JpaRepository<DomainEventEntity, String> {

    fun findAllByProjectKey( projectKey: String ): List<DomainEventEntity>

}