package com.example.callfether.di

import com.example.callfether.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel()
    }
}