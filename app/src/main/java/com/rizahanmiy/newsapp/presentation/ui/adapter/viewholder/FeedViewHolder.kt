package com.rizahanmiy.newsapp.presentation.ui.adapter.viewholder


import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.rizahanmiy.newsapp.data.entities.NewsArticlesApi
import com.rizahanmiy.newsapp.utils.common.loadImage
import kotlinx.android.synthetic.main.list_item_article.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(newsArticleEntity: NewsArticlesApi) {
        with(itemView) {

            newsArticleEntity.urlToImage?.let { ivArticle.loadImage(it) }
            tvTitle.text = newsArticleEntity.title
            tvAuthor.text = newsArticleEntity.author
            tvDesc.text = newsArticleEntity.description
            tvContent.text = newsArticleEntity.content
            tvTime.text = newsArticleEntity.publishedAt?.let { convertTimeFormat(it) }
        }
    }

    private fun convertTimeFormat(inputTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val date = inputFormat.parse(inputTime)
        return outputFormat.format(date!!)
    }
}