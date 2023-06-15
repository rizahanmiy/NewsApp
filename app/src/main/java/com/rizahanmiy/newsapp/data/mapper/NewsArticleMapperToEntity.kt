package com.rizahanmiy.newsapp.data.mapper

import com.rizahanmiy.newsapp.data.entities.*
import com.rizahanmiy.newsapp.domain.entities.*

fun NewsArticlesApi.map() = NewsArticleEntity(
    source = source?.checkoutKeyValueMapNettoItemToEntity() ?: SourceEntity(),
    author = author ?: "",
    title = title ?: "",
    description = description ?: "",
    url = url ?: "",
    urlToImage = urlToImage ?: "",
    publishedAt = publishedAt ?: "",
    content = content ?: ""
)

fun SourceApi.checkoutKeyValueMapNettoItemToEntity(): SourceEntity {
    return SourceEntity(
        id = id ?: "",
        name = name ?: ""
    )
}

//fun articleMapListToEntity(article:MutableList<NewsArticlesApi>) : MutableList<NewsArticleEntity> =
//    article.map { mapItemToEntity(it) }
//
//private fun mapItemToEntity(item:NewsArticlesApi): NewsArticleEntity =
//    NewsArticleEntity(
//        source = item.source?.checkoutKeyValueMapNettoItemToEntity() ?: SourceEntity(),
//        author = item.author ?: "",
//        title = item.title ?: "",
//        description = item.description ?: "",
//        url = item.url ?: "",
//        urlToImage = item.urlToImage ?: "",
//        publishedAt = item.publishedAt ?: "",
//        content = item.content ?: ""
//    )