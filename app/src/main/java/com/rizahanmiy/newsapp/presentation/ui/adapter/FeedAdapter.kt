package com.rizahanmiy.newsapp.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizahanmiy.newsapp.R
import com.rizahanmiy.newsapp.data.entities.NewsArticlesApi
import com.rizahanmiy.newsapp.domain.common.State
import com.rizahanmiy.newsapp.presentation.ui.adapter.viewholder.FeedViewHolder


class FeedAdapter(
    val context: Context,
    val articles: MutableList<NewsArticlesApi>,
    val listener: OnArticleClickListener? = null,
    var limitShimmer: Int = 9
) :
    RecyclerView.Adapter<FeedViewHolder>() {

    companion object {
        var currentSelectIndex = -1
        const val REQUEST_PRODUCTQTY = 1
        const val LOADING_VIEW = 0
        const val CONTENT_VIEW = 1
    }
    var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
/*        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_history_order, parent, false)
        return FeedOrderViewHolder(itemView)*/

        return when (viewType) {
            LOADING_VIEW -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_shimmer_one_line_option, parent, false)
                return FeedViewHolder(itemView)
            }
            CONTENT_VIEW -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_article, parent, false)
                FeedViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_shimmer_one_line_option, parent, false)
                return FeedViewHolder(itemView)
            }
        }
    }

    override fun getItemCount(): Int {
        if (state == State.LOADING) return articles.size + limitShimmer
        return articles.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (state == State.LOADING && position > articles.size - 1) {
            LOADING_VIEW
        } else {
            CONTENT_VIEW
        }
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = articles[position]
        holder.bindTo(item)

        holder.itemView.setOnClickListener {
            listener?.onArticleClicked(item)
        }
    }

    fun setUpdateState(state: State) {
        this.state = state
        notifyDataSetChanged()
    }

    interface OnArticleClickListener {
        fun onArticleClicked(item:NewsArticlesApi)
    }
}