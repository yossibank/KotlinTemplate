package com.example.kotlintemplate.route

import kotlinx.serialization.Serializable

class Route {
    @Serializable
    object Home

    @Serializable
    data class Detail(
        val id: Int
    )
}