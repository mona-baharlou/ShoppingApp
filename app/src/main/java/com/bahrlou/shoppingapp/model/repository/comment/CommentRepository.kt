package com.bahrlou.shoppingapp.model.repository.comment

import com.bahrlou.shoppingapp.model.data.Comment

interface CommentRepository {

    suspend fun getComments(productId: String): List<Comment>

    suspend fun addComment(productId: String, comment: String, IsSucceed:(String)-> Unit)
}