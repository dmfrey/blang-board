package io.pivotal.apptx.blangBoard.domain

import io.pivotal.apptx.blangBoard.domain.events.DomainEvent
import io.pivotal.apptx.blangBoard.domain.events.TermCreated
import java.time.Instant
import java.util.*

class Project( val key: String ) {

    private val _terms: MutableMap<UUID, String> = mutableMapOf()
    val terms: Map<UUID, String> = _terms

    private val _changes: MutableMap<UUID, DomainEvent> = mutableMapOf()
    val changes: Map<UUID, DomainEvent> = _changes

    fun addTerm( term: Term, occurredOn: Instant ) {

        termCreated( TermCreated( term.uuid, term.name, occurredOn, key ) )

    }

    private fun termCreated( termCreated: TermCreated ) {

        _terms[ termCreated.termUuid ] = termCreated.name
        _changes[ termCreated.termUuid ] =  termCreated

    }

    fun flushChanges() = _changes.clear()

    private fun handleEvent( domainEvent: DomainEvent ) {

        when( domainEvent ) {
            is TermCreated -> termCreated( domainEvent )
        }
    }

    companion object {

        fun createFrom( key: String, domainEvents: Collection<DomainEvent> ): Project {

            val project = Project( key )

            for( domainEvent in domainEvents ) project.handleEvent( domainEvent )

            return project
        }

    }

}