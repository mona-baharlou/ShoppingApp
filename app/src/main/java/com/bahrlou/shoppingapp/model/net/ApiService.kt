package com.bahrlou.shoppingapp.model.net

import com.bahrlou.shoppingapp.model.data.AdsResponse
import com.bahrlou.shoppingapp.model.data.LoginResponse
import com.bahrlou.shoppingapp.model.data.ProductResponse
import com.bahrlou.shoppingapp.model.repository.TokenInMemory
import com.bahrlou.shoppingapp.util.BASE_URL
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("signUp")
    suspend fun signUp(@Body jsonObject: JsonObject): LoginResponse

    @POST("signIn")
    suspend fun signIn(@Body jsonObject: JsonObject): LoginResponse

    @GET("refreshToken")
    fun refreshToken(): Call<LoginResponse>

    @GET("getSliderPics")
    suspend fun getAds(): AdsResponse

    @GET("getProducts")
    suspend fun getProducts(): ProductResponse
}

fun createApiService(): ApiService {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {

            val oldRequest = it.request()
            val newRequest = oldRequest.newBuilder()

            if (TokenInMemory.token != null) {
                newRequest.addHeader("Authorization", TokenInMemory.token!!)
            }
            newRequest.addHeader("Accept", "application/json")
            newRequest.method(oldRequest.method, oldRequest.body)

            return@addInterceptor it.proceed(newRequest.build())

        }.build()


    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)
}