package com.segunfrancis.randm.data.remote

import com.apollographql.apollo.api.Response
import com.segunfrancis.CharacterDetailQuery
import com.segunfrancis.CharacterListQuery

interface IRemoteClient {
    suspend fun getCharacterList(): Response<CharacterListQuery.Data>

    suspend fun getCharacterDetail(id: String): Response<CharacterDetailQuery.Data>
}
