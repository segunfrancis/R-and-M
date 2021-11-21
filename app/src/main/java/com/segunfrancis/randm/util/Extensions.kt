package com.segunfrancis.randm.util

import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.segunfrancis.CharacterListQuery
import com.segunfrancis.randm.ui.home.Character

fun CharacterListQuery.Result.mapToCharacter(): Character {
    return Character(
        id = id,
        name = name,
        image = image
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
