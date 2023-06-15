package com.rizahanmiy.newsapp.data.mapper

import com.rizahanmiy.newsapp.data.entities.*
import com.rizahanmiy.newsapp.domain.entities.*

fun NewsSourceApi.map() = NewsSourceEntity(
    id = id ?: "",
    name = name ?: "",
    description = description ?: "",
    url = url ?: "",
    category = category ?: "",
    language = language ?: "",
    country = country ?: ""
)

fun sourceMapListToEntity(source:List<NewsSourceApi>) : List<NewsSourceEntity> =
    source.map { mapItemToEntity(it) }

private fun mapItemToEntity(item:NewsSourceApi): NewsSourceEntity =
    NewsSourceEntity(
        id = item.id ?: "",
        name = item.name ?: "",
        description = item.description ?: "",
        url = item.url ?: "",
        category = item.category ?: "",
        language = item.language ?: "",
        country = item.country ?: ""
    )