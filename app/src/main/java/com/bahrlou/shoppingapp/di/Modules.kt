package com.bahrlou.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.bahrlou.shoppingapp.model.db.AppDatabase
import com.bahrlou.shoppingapp.model.net.createApiService
import com.bahrlou.shoppingapp.model.repository.cart.CartRepository
import com.bahrlou.shoppingapp.model.repository.cart.CartRepositoryImpl
import com.bahrlou.shoppingapp.model.repository.comment.CommentRepository
import com.bahrlou.shoppingapp.model.repository.comment.CommentRepositoryImpl
import com.bahrlou.shoppingapp.model.repository.product.ProductRepository
import com.bahrlou.shoppingapp.model.repository.product.ProductRepositoryImpl
import com.bahrlou.shoppingapp.model.repository.user.UserRepository
import com.bahrlou.shoppingapp.model.repository.user.UserRepositoryImpl
import com.bahrlou.shoppingapp.ui.features.category.CategoryViewModel
import com.bahrlou.shoppingapp.ui.features.main.MainViewModel
import com.bahrlou.shoppingapp.ui.features.product.ProductViewModel
import com.bahrlou.shoppingapp.ui.features.signIn.SignInViewModel
import com.bahrlou.shoppingapp.ui.features.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shoppingModules = module {

    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }

    single { createApiService() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "shopping_database.db")
            .build()
    }


    //Repositories
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }

    single<ProductRepository> {
        ProductRepositoryImpl(
            get(),
            get<AppDatabase>().productDao() //we need productDao from appDatabase
        )
    }


    //ViewModels
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { (isInternetConnected: Boolean) -> MainViewModel(get(), isInternetConnected) }
    viewModel { CategoryViewModel(get()) }
    viewModel { ProductViewModel(get(), get(),get()) }
}