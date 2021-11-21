package com.segunfrancis.randm.di

import coil.ImageLoader
import com.apollographql.apollo.ApolloClient
import com.segunfrancis.randm.data.remote.RemoteClient
import com.segunfrancis.randm.util.AppConstants
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val mainModule = module {
    single { ApolloClient.builder().serverUrl(AppConstants.SERVER_URL).build() }
    single { Dispatchers.IO }
    single { RemoteClient(apolloClient = get(), dispatcher = get()) }
    single { ImageLoader(get()).newBuilder().build() }
}
