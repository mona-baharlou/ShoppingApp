package com.bahrlou.shoppingapp.model.repository.comment

import com.bahrlou.shoppingapp.model.data.Comment
import com.bahrlou.shoppingapp.model.net.ApiService
import com.google.gson.JsonObject

class CommentRepositoryImpl(
    private val apiService: ApiService,

    ) : CommentRepository {
    override suspend fun getComments(productId: String): List<Comment> {
        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
        }

        val result = apiService.getComments(jsonObject)
        if (result.success) {
            return result.comments
        }

        return listOf()
    }
}