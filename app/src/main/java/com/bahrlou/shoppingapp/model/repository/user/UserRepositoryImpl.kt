package com.bahrlou.shoppingapp.model.repository.user

class UserRepositoryImpl : UserRepository {
    override suspend fun signUp(name: String, username: String, password: String) {

    }

    override suspend fun signIn(username: String, password: String) {

    }


    //offline
    override fun signOut() {
    }

    override fun loadToken() {
    }

    override fun saveToken(newToken: String) {
    }

    override fun getToken(): String {
        return ""

    }

    override fun saveUsername(username: String) {
    }

    override fun getUsername(): String {
        return ""
    }
}