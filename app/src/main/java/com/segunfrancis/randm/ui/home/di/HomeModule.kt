package com.segunfrancis.randm.ui.home.di

import com.segunfrancis.randm.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(client = get()) }
}
