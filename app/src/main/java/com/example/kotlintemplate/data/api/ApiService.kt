package com.example.kotlintemplate.data.api

import com.example.kotlintemplate.data.entity.RakutenEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/services/api/IchibaItem/Search/20220601")
    suspend fun rakutenSearch(
        @Query("keyword")
        keyword: String,
        @Query("page")
        page: Int = 1,
        @Query("hits")
        hits: Int = 30,
        @Query("formatVersion")
        formatVersion: Int = 2,
        @Query("applicationId")
        applicationId: String = "1032211485929725116"
    ): RakutenEntity
}