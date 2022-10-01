package com.example.apicalling.retrofit

import com.example.apicalling.controller.CartsApiModelItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    fun getProduct() : Call<List<ProductApiModelItem>>

    @GET("carts")
    fun getCarts() : Call<List<CartsApiModelItem>>
}