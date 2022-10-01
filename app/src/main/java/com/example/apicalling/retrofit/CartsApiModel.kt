package com.example.apicalling.controller

import com.google.gson.annotations.SerializedName

data class CartsApiModel(

	@field:SerializedName("CartsApiModel")
	val cartsApiModel: List<CartsApiModelItem?>? = null
)

data class ProductsItem(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("productId")
	val productId: Int? = null
)

data class CartsApiModelItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)
