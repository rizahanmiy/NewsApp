package com.rizahanmiy.newsapp.presentation.ui.adapter.viewholder


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rizahanmiy.newsapp.data.entities.NewsArticlesApi
import com.rizahanmiy.newsapp.data.entities.NewsSourceApi
import com.rizahanmiy.newsapp.utils.common.loadImage
import kotlinx.android.synthetic.main.list_item_article.view.*


class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(newsArticleEntity: NewsArticlesApi) {
        with(itemView) {

            newsArticleEntity.urlToImage?.let { ivArticle.loadImage(it) }
            tvTitle.text = newsArticleEntity.title
            tvAuthor.text = newsArticleEntity.author
            tvDesc.text = newsArticleEntity.description
            tvContent.text = newsArticleEntity.content
        }
    }
}