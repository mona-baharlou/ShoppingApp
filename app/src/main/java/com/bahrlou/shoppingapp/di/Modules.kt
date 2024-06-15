package com.bahrlou.shoppingapp.di

import android.content.Context
import com.bahrlou.shoppingapp.model.net.createApiService
import com.bahrlou.shoppingapp.model.repository.user.UserRepository
import com.bahrlou.shoppingapp.model.repository.user.UserRepositoryImpl
import com.bahrlou.shoppingapp.ui.features.signIn.SignInViewModel
import com.bahrlou.shoppingapp.ui.features.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shoppingModules = module {

    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }

    single { createApiService() }

    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }

}