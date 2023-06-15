package com.rizahanmiy.newsapp.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizahanmiy.newsapp.R
import com.rizahanmiy.newsapp.data.entities.NewsSourceApi
import kotlinx.android.synthetic.main.list_item_news_category.view.*
import kotlinx.android.synthetic.main.list_item_source.view.*

class NewsSourceListAdapter(
    val context: Context,
    val data:MutableList<NewsSourceApi>,
    val onCategoryClicked: ((category: String?) -> Unit)? = null,
):RecyclerView.Adapter<NewsSourceListAdapter.NewsSourceViewHolder>() {

    companion object {
        var last_position = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceViewHolder {
        return NewsSourceViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_source, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NewsSourceViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.tvTitle.setTextColor(context.getColor(R.color.night_black))

        holder.itemView.setOnClickListener {
            last_position = position
            onCategoryClicked?.invoke(data[position].id)
            notifyDataSetChanged()
        }

        if (last_position == position) {
            holder.itemView.tvTitle.setTextColor(context.getColor(R.color.orange))
        } else {
            holder.itemView.tvTitle.setTextColor(context.getColor(R.color.night_black))
        }
    }

    //ViewHolder
    inner class NewsSourceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: NewsSourceApi?) {
            with(itemView) {
                tvTitle.text = data?.name
            }
        }
    }
}