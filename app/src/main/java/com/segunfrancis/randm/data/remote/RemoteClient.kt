package com.segunfrancis.randm.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.segunfrancis.CharacterListQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteClient(private val apolloClient: ApolloClient, private val dispatcher: CoroutineDispatcher) {

    suspend fun getCharacterList(): Response<CharacterListQuery.Data> =
        withContext(dispatcher) {
            apolloClient.query(CharacterListQuery()).await()
        }
}
