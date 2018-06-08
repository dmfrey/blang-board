package io.pivotal.apptx.blangBoard.domain.usecases.requests

data class CreateTermRequest(
        var projectKey: String,
        var term: String
)