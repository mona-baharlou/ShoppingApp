package com.bahrlou.shoppingapp.di

import com.bahrlou.shoppingapp.ui.signIn.SignInViewModel
import com.bahrlou.shoppingapp.ui.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shoppingModules = module {

    viewModel { SignUpViewModel() }
    viewModel { SignInViewModel() }

}