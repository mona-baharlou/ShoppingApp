package com.bahrlou.shoppingapp.di

import com.bahrlou.shoppingapp.ui.features.signIn.SignInViewModel
import com.bahrlou.shoppingapp.ui.features.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shoppingModules = module {

    viewModel { SignUpViewModel() }
    viewModel { SignInViewModel() }

}