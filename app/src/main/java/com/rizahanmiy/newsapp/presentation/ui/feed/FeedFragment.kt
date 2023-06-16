package com.rizahanmiy.newsapp.presentation.ui.feed


import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizahanmiy.newsapp.R
import com.rizahanmiy.newsapp.data.base.BaseFragment
import com.rizahanmiy.newsapp.data.entities.NewsArticlesApi
import com.rizahanmiy.newsapp.domain.common.ResultState
import com.rizahanmiy.newsapp.domain.common.State
import com.rizahanmiy.newsapp.presentation.ui.adapter.CategoryListAdapter
import com.rizahanmiy.newsapp.presentation.ui.adapter.FeedAdapter
import com.rizahanmiy.newsapp.presentation.ui.webview.WebViewActivity
import com.rizahanmiy.newsapp.presentation.viewmodel.NewsViewModel
import com.rizahanmiy.newsapp.presentation.viewmodel.ViewModelFactory
import com.rizahanmiy.newsapp.utils.common.categoryList
import com.rizahanmiy.newsapp.utils.extension.observe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_categories.pullToRefresh
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.layout_toolbar_categories.*
import javax.inject.Inject


class FeedFragment : BaseFragment(), FeedAdapter.OnArticleClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var news: MutableList<NewsArticlesApi>
    private lateinit var categoryListAdapter: CategoryListAdapter

    private lateinit var articleAdapter: FeedAdapter

    var previousTotalItemCount = 0
    var loading = true
    var limit = 10
    var page = 1
    var country = ""
    var category = "business"

    var categoryData : List<String> = categoryList

    override val layout: Int = R.layout.fragment_feed

    override fun onPreparation() {
        AndroidSupportInjection.inject(this)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        news = mutableListOf()
    }

    private fun onClickCategory(data:String?){
        if (data != null) {
            category = data
        }

        observe(newsViewModel.fetchArticle(
            page = page,
            pageSize = limit,
            country = country,
            category = category
        )){ manageStateArticle(it) }
    }


    override fun onUi() {
        categoryListAdapter = CategoryListAdapter(
            context = this.requireContext(),
            data = categoryData,
            onCategoryClicked = {
                onClickCategory(it)
            },
            feed = true
        )

        rvCategory.apply {
            adapter = categoryListAdapter
            layoutManager = LinearLayoutManager(
                this.context,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    override fun onAction() {
        pullToRefresh.setOnRefreshListener {
            observe(newsViewModel.fetchArticle(
                page = 1,
                pageSize = 10,
                country = country,
                category = category
            )){
                manageStateArticle(it)
            }
        }

        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (articleAdapter.state == State.LOADING) return
                var visibleItemCount = 6
                visibleItemCount *= (recyclerView.layoutManager as LinearLayoutManager).itemCount
                val totalItemCount =
                    (recyclerView.layoutManager as LinearLayoutManager).itemCount
                val pastVisibleItems =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val pastVisibleItem = getLastVisibleItem(pastVisibleItems)
                if (loading && (totalItemCount > previousTotalItemCount)) {
                    loading = false
                    previousTotalItemCount = totalItemCount
                }
                if (!loading && (pastVisibleItem + visibleItemCount) > totalItemCount) {
                    observe(newsViewModel.fetchArticle(
                        page = page,
                        pageSize = limit,
                        country = country
                    ), ::setItemProductsPaging
                    )
                    loading = true
                }
            }
        }
        rvArticle.addOnScrollListener(scrollListener)
    }

    private fun setItemProductsPaging(productResult: ResultState<MutableList<NewsArticlesApi>>) {
        when (productResult) {
            is ResultState.Success -> {
                articleAdapter.setUpdateState(State.SUCCESS)
                page += 1
                rvArticle.addOnScrollListener(scrollListener)
                news.addAll(productResult.data)
                articleAdapter.setUpdateState(State.SUCCESS)
                articleAdapter.notifyDataSetChanged()
            }
            is ResultState.Error -> {
                articleAdapter.setUpdateState(State.FAILED)
                rvArticle.removeOnScrollListener(scrollListener)
            }
            is ResultState.Failed -> {
                articleAdapter.setUpdateState(State.FAILED)
                rvArticle.removeOnScrollListener(scrollListener)
            }
            is ResultState.Loading -> {
                articleAdapter.setUpdateState(State.LOADING)
                rvArticle.removeOnScrollListener(scrollListener)
            }
            is ResultState.NoAuthorization -> {
                articleAdapter.setUpdateState(State.FAILED)
                rvArticle.removeOnScrollListener(scrollListener)
            }
        }
    }

    fun getLastVisibleItem(lastVisibleItemPositions: Int): Int {
        var maxSize = 0
        if (lastVisibleItemPositions > maxSize) {
            maxSize = lastVisibleItemPositions
        }
        return maxSize
    }
    override fun onObserver() {
        observe(newsViewModel.fetchArticle(
            page = page,
            pageSize = limit,
            country = country,
            category = category
        )){
            manageStateArticle(it)
        }
    }

    private fun showArticle(){
        context?.let {
            articleAdapter = FeedAdapter(it, news , this)
            context.let {
                rvArticle.apply {
                    adapter = articleAdapter
                    layoutManager = LinearLayoutManager(
                        it,
                        RecyclerView.VERTICAL,
                        false
                    )
                }
            }
        }
    }

    private fun manageStateArticle(result: ResultState<MutableList<NewsArticlesApi>>) {
        when (result) {
            is ResultState.Loading -> {
                pullToRefresh.isRefreshing = false
            }
            is ResultState.Success -> {
                pullToRefresh.isRefreshing = false
                page = 2
                if (result.data.size < 9) {
                    rvArticle.removeOnScrollListener(scrollListener)
                }
                news.clear()
                news.addAll(result.data)
                showArticle()
                articleAdapter.setUpdateState(State.SUCCESS)
                articleAdapter.notifyDataSetChanged()
            }
            is ResultState.Error -> {
                pullToRefresh.isRefreshing = false
                rvArticle.removeOnScrollListener(scrollListener)
            }
            is ResultState.Failed -> {
                pullToRefresh.isRefreshing = false
                rvArticle.removeOnScrollListener(scrollListener)
            }
            is ResultState.NoAuthorization -> {
                pullToRefresh.isRefreshing = false
                rvArticle.removeOnScrollListener(scrollListener)
            }
        }
    }

    override fun onArticleClicked(item: NewsArticlesApi) {
        item.url?.let { WebViewActivity.start(requireContext(), it) }
    }

    //Back to saved Filter
    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause() Article")
    }

    override fun onResume() {
        super.onResume()
        onObserver()
    }

}
