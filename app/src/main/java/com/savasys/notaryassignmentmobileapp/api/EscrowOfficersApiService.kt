package com.savasys.notaryassignmentmobileapp.api

import com.savasys.notaryassignmentmobileapp.api.adapter.DateAdapter
import com.savasys.notaryassignmentmobileapp.data.model.EscrowOfficer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//private const val BASE_URL = "http://34.74.48.236:8081/api/"
private const val BASE_URL = "http://192.168.0.27:8081/api/"
//private const val BASE_URL = "http://192.168.0.36:8081/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EscrowOfficersApiService {

    @GET("escrowOfficers/{id}")
    suspend fun getEscrowOfficer(@Path("id") id: Long): EscrowOfficer
}

object EscrowOfficersApi {
    val escrowOfficersApiService: EscrowOfficersApiService by lazy {
        retrofit.create(EscrowOfficersApiService::class.java)
    }
}