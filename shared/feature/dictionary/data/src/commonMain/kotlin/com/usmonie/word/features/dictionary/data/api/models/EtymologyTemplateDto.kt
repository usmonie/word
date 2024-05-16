package com.usmonie.word.features.dictionary.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class EtymologyTemplateDto(
    @SerialName("args")
    val args: Map<String, String> = mapOf(),
    @SerialName("expansion")
    val expansion: String? = null,
    @SerialName("name")
    val name: String? = null
)
