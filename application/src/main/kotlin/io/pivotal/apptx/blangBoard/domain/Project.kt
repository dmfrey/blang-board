package io.pivotal.apptx.blangBoard.domain

import io.pivotal.apptx.blangBoard.domain.events.DomainEvent
import io.pivotal.apptx.blangBoard.domain.events.TermCreated
import io.pivotal.apptx.blangBoard.domain.events.TermDefinitionCreated
import java.time.Instant
import java.util.*

class Project( val key: String ) {

    private val _terms: MutableMap<UUID, Term> = mutableMapOf()
    val terms: Map<UUID, Term> = _terms

    private val _definition: MutableMap<UUID, Definition> = mutableMapOf()

    private val _changes: MutableMap<UUID, DomainEvent> = mutableMapOf()
    val changes: Map<UUID, DomainEvent> = _changes

    fun addTerm( termUuid: UUID, name: String, occurredOn: Instant ) {

        termCreated( TermCreated( termUuid, name, occurredOn, key ) )

    }

    private fun termCreated( termCreated: TermCreated ) : Project {

        _terms[ termCreated.termUuid ] = Term( termCreated.termUuid, termCreated.name )
        _changes[ termCreated.termUuid ] =  termCreated

        return this
    }

    fun addTermDefinition( definitionUuid: UUID, definition: String, teamKey: String, termUuid: UUID, occurredOn: Instant ) {

        termDefinitionCreated( TermDefinitionCreated( definitionUuid, definition, teamKey, termUuid, occurredOn, key ) )

    }

    private fun termDefinitionCreated( termDefinitionCreated: TermDefinitionCreated) : Project {

        _definition[ termDefinitionCreated.termDefinitionUuid ] = Definition(
                termDefinitionCreated.termDefinitionUuid,
                termDefinitionCreated.definition,
                termDefinitionCreated.teamKey,
                termDefinitionCreated.termUuid
        )
        _changes[ termDefinitionCreated.termDefinitionUuid ] =  termDefinitionCreated

        return this
    }

    fun flushChanges() = _changes.clear()

    private fun handleEvent( domainEvent: DomainEvent ) {

        when( domainEvent ) {
            is TermCreated -> termCreated( domainEvent )
            is TermDefinitionCreated -> termDefinitionCreated( domainEvent )
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