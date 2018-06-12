package io.pivotal.apptx.blangBoard.domain.usecases

import io.pivotal.apptx.blangBoard.domain.Project

interface LookupProjectGroup {

    fun execute( projectKey: String ): Project

}
