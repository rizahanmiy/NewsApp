package com.rizahanmiy.newsapp.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsSourceApi(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("country")
    val country: String?,
): Serializable
