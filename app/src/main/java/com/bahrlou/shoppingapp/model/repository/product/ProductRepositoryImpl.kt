package com.bahrlou.shoppingapp.model.repository.product

import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.db.ProductDao
import com.bahrlou.shoppingapp.model.net.ApiService

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun getAllProducts(isInternetConnected: Boolean): List<Product> {

        if (isInternetConnected) {
            //get data from net
            val dataFromServer = apiService.getProducts()
            if (dataFromServer.success) {
                productDao.insertOrUpdate(dataFromServer.products)
                return dataFromServer.products
            }
        } else {
            //get data from local
            return productDao.getAll()
        }

        return listOf()
    }

    override suspend fun getAllAds(isInternetConnected: Boolean): List<Ads> {

        if (isInternetConnected) {
            val dataFromServer = apiService.getAds()
            if (dataFromServer.success) {
                return dataFromServer.ads
            }
        }

        return listOf()
    }

    override suspend fun getProductsByCategory(categoryName: String): List<Product> {
        return productDao.getByCategory(categoryName)
    }

    override suspend fun getProductById(productId: String): Product {
        return productDao.getById(productId)
    }
}