package com.example.kotlintemplate.data.entity

import com.google.gson.annotations.SerializedName

data class RakutenEntity(
    @SerializedName("Items")
    val items: ArrayList<RakutenItem>,
    @SerializedName("count")
    val count: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("first")
    val first: Int,
    @SerializedName("last")
    val last: Int,
    @SerializedName("hits")
    val hits: Int,
    @SerializedName("carrier")
    val carrier: Int,
    @SerializedName("pageCount")
    val pageCount: Int
)

data class RakutenItem(
    @SerializedName("itemName")
    val itemName: String,
    @SerializedName("itemCode")
    val itemCode: String,
    @SerializedName("itemPrice")
    val itemPrice: Int,
    @SerializedName("itemCaption")
    val itemCaption: String,
    @SerializedName("catchCopy")
    val catchCopy: String,
    @SerializedName("itemUrl")
    val itemUrl: String,
    @SerializedName("smallImageUrls")
    val smallImageUrls: ArrayList<String>,
    @SerializedName("mediumImageUrls")
    val mediumImageUrls: ArrayList<String>
)