package com.example.kotlintemplate.data.api

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

interface ApiClientInterface<T> {
    fun getClient(): T
}

class ApiClient @Inject constructor(
    private val retrofit: Retrofit
) : ApiClientInterface<Retrofit> {
    override fun getClient(): Retrofit = retrofit
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindApiClient(
        apiClient: ApiClient
    ): ApiClientInterface<Retrofit>

    companion object {
        private const val BASE_URL = "https://app.rakuten.co.jp"

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
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
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }
}