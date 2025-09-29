package com.example.kotlintemplate.ui.route

import kotlinx.serialization.Serializable

class Route {
    @Serializable
    object Home

    @Serializable
    data class Detail(
        val id: Int
    )

    @Serializable
    object Rakuten
}