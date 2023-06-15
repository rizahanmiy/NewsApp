package com.rizahanmiy.newsapp.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsArticleEntity(
    var source: SourceEntity = SourceEntity(),
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String = "",
    var urlToImage: String = "",
    val publishedAt:  String = "",
    var content:  String = ""
): Parcelable

@Parcelize
data class SourceEntity(
    var id: String = "",
    var name: String = ""
) : Parcelable