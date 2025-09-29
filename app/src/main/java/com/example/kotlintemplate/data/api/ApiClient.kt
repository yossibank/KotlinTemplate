package com.example.kotlintemplate.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient : ApiClientInterface<Retrofit> {
    private companion object {
        private const val BASE_URL = "https://app.rakuten.co.jp"
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor {
            val origin = it.request()
            val request = origin.newBuilder()
                .method(origin.method, origin.body)
                .header("Content-Type", "application/json")
                .build()
            it.proceed(request)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(client)
        .build()

    override fun getClient(): Retrofit {
        return retrofit
    }
}