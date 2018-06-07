package io.pivotal.apptx.blangBoard.domain.usecases.requests

data class CreateTermRequest(
        var projectName: String,
        var term: String
)