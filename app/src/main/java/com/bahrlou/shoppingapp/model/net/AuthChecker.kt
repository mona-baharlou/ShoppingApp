package com.bahrlou.shoppingapp.model.net

import com.bahrlou.shoppingapp.model.data.LoginResponse
import com.bahrlou.shoppingapp.model.repository.TokenInMemory
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthChecker : Authenticator, KoinComponent {
    //when error 401 occurred
    //get token from server & check token's expiration

    private val apiService: ApiService by inject()
    override fun authenticate(route: Route?, response: Response): Request? {

        if (TokenInMemory.token != null
            && !response.request.url
                .pathSegments.last().equals(
                    "refreshToken",
                    false
                )//if for example we are in cart section and token is expired we should refresh token
        ) {

            val result = refreshToken()

            if (result) {
                //refreshing token was a problem and now it is solved
                return response.request
                //previous request will run again & will work with previous token
                // because in my app when token is refreshed, new token will not send
                // instead previous token's expiration will be updated
            }

        }
        return null

    }


    //Asynchronously makes a request to server in another thread
    private fun refreshToken(): Boolean {
        val request: retrofit2.Response<LoginResponse> =
            apiService.refreshToken().execute()

        if (request.body() != null) {
            if (request.body()!!.success) {
                return true
            }
        }

        return false
    }
}