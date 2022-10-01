package com.example.apicalling.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    var Base_URL = "https://fakestoreapi.com/"

    fun getRetrofit(): Retrofit {
        var retrofit =
            Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit
    }
}