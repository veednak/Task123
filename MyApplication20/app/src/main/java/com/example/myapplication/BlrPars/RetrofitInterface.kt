package com.example.myapplication.BlrPars

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {
    @get:GET("api/exrates/rates?periodicity=0")
    val posts: Call<List<CurrencyBelarus?>?>?

    companion object {
        const val BASE_URL = "https://www.nbrb.by"
    }
}