package io.pivotal.apptx.blangBoard.endpoint.model

data class NewTermDefinitionRequestModel(
    val teamKey: String,
    val definition: String
)
