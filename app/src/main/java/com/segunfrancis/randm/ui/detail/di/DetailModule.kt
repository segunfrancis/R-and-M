package com.segunfrancis.randm.ui.detail.di

import com.segunfrancis.randm.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { id -> DetailViewModel(id = id.get(), client = get()) }
}
