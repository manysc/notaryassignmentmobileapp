package com.savasys.notaryassignmentmobileapp.api

import com.savasys.notaryassignmentmobileapp.api.adapter.DateAdapter
import com.savasys.notaryassignmentmobileapp.data.model.Notarization
import com.savasys.notaryassignmentmobileapp.data.model.NotarizationStatus
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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

interface NotarizationsApiService {

    @GET("notarizations/")
    suspend fun getNotarizations(): List<Notarization>

    @GET("notarizations/")
    suspend fun getNotarizationsByStatus(@Query("status") status: NotarizationStatus): List<Notarization>

    @GET("notarizations/active")
    suspend fun getActiveNotarizations(): List<Notarization>

    @GET("notarizations/inProgress")
    suspend fun getInProgressNotarizations(): List<Notarization>

    @GET("notarizations/inactive")
    suspend fun getInactiveNotarizations(): List<Notarization>
}

object NotarizationsApi {
    val notarizationsApiService: NotarizationsApiService by lazy {
        retrofit.create(NotarizationsApiService::class.java)
    }
}