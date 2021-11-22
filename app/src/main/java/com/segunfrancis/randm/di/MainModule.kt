package com.segunfrancis.randm.di

import coil.ImageLoader
import com.apollographql.apollo.ApolloClient
import com.segunfrancis.randm.data.remote.IRemoteClient
import com.segunfrancis.randm.data.remote.RemoteClient
import com.segunfrancis.randm.util.AppConstants
import com.segunfrancis.randm.util.AppConstants.SERVER_TIMEOUT
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val mainModule = module {
    single {
        OkHttpClient.Builder()
            .callTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    single {
        ApolloClient.builder()
            .serverUrl(AppConstants.SERVER_URL)
            .okHttpClient(get())
            .build()
    }
    single { Dispatchers.IO }
    single<IRemoteClient> { RemoteClient(apolloClient = get(), dispatcher = get()) }
    single { ImageLoader(context = get()).newBuilder().build() }
}
