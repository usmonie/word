package com.usmonie.word.features.models

import com.usmonie.word.features.dashboard.domain.models.HeadTemplate


data class HeadTemplateUi(
    val id: String,
//                              val args: Map<String, String>,
    val expansion: String?,
    val name: String?,
)