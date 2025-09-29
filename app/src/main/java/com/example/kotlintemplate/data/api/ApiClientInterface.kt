package com.example.kotlintemplate.data.api

interface ApiClientInterface<T> {
    fun getClient(): T
}