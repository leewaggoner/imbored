package com.example.imbored.di

import com.example.imbored.ui.choose.ChooseActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ChooseActivityViewModel() }
}