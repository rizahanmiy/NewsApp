package com.rizahanmiy.newsapp.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsSourceEntity(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var url: String = "",
    var category: String = "",
    val language:  String = "",
    var country:  String = ""
): Parcelable