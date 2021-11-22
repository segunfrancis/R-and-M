package com.segunfrancis.randm.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.segunfrancis.CharacterDetailQuery
import com.segunfrancis.CharacterListQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteClient(private val apolloClient: ApolloClient, private val dispatcher: CoroutineDispatcher) : IRemoteClient {

    override suspend fun getCharacterList(): Response<CharacterListQuery.Data> =
        withContext(dispatcher) {
            apolloClient.query(CharacterListQuery()).await()
        }

    override suspend fun getCharacterDetail(id: String): Response<CharacterDetailQuery.Data> {
        return withContext(dispatcher) {
            apolloClient.query(CharacterDetailQuery(id = id)).await()
        }
    }
}
