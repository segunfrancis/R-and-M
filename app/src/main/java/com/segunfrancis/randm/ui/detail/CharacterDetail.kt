package com.segunfrancis.randm.ui.detail

data class CharacterDetail(
    val name: String?,
    val image: String?,
    val species: String?,
    val status: String?,
    val gender: String?,
    val created: String?,
    val episodes: List<Episode>?
)

data class Episode(
    val id: String?,
    val name: String?
)
