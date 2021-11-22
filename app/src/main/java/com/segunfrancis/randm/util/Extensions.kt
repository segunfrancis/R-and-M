package com.segunfrancis.randm.util

import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.segunfrancis.CharacterDetailQuery
import com.segunfrancis.CharacterListQuery
import com.segunfrancis.randm.ui.detail.CharacterDetail
import com.segunfrancis.randm.ui.detail.Episode
import com.segunfrancis.randm.ui.home.Character

fun CharacterListQuery.Result.mapToCharacter(): Character {
    return Character(
        id = id,
        name = name,
        image = image
    )
}

fun CharacterDetailQuery.Data.mapToCharacterDetail(): CharacterDetail {
    return CharacterDetail(
        name = character?.name,
        image = character?.image,
        gender = character?.gender,
        status = character?.status,
        species = character?.species,
        created = character?.created,
        episodes = character?.episode?.map { it.mapToEpisode() }
    )
}

private fun CharacterDetailQuery.Episode?.mapToEpisode(): Episode {
    return Episode(
        id = this?.id,
        name = this?.name
    )
}

fun ImageView.loadImage(imageUrl: String?, imageLoader: ImageLoader) {
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .target(this)
        .crossfade(true)
        .build()
    imageLoader.enqueue(request)
}
