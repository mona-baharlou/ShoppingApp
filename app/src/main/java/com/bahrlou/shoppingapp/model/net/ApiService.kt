package com.bahrlou.shoppingapp.model.net

import com.bahrlou.shoppingapp.model.data.AddNewCommentResponse
import com.bahrlou.shoppingapp.model.data.AdsResponse
import com.bahrlou.shoppingapp.model.data.CartResponse
import com.bahrlou.shoppingapp.model.data.CheckOut
import com.bahrlou.shoppingapp.model.data.CommentResponse
import com.bahrlou.shoppingapp.model.data.LoginResponse
import com.bahrlou.shoppingapp.model.data.ProductResponse
import com.bahrlou.shoppingapp.model.data.SubmitOrder
import com.bahrlou.shoppingapp.model.data.UserCartInfo
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

    @POST("getComments")
    suspend fun getComments(@Body jsonObject: JsonObject): CommentResponse

    @POST("addNewComment")
    suspend fun addComment(@Body jsonObject: JsonObject): AddNewCommentResponse

    @POST("addToCart")
    suspend fun addProductToCart(@Body jsonObject: JsonObject): CartResponse

    @GET("getUserCart")
    suspend fun getUserCart(): UserCartInfo

    @POST("removeFromCart")
    suspend fun removeProductFromCart(@Body jsonObject: JsonObject): CartResponse

    @POST("submitOrder")
    suspend fun submitOrder(@Body jsonObject: JsonObject): SubmitOrder

    @POST("checkout")
    suspend fun checkout(@Body jsonObject: JsonObject): CheckOut


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