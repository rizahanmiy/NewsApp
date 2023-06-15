package com.rizahanmiy.newsapp.presentation.ui.categories


import android.content.Context
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizahanmiy.newsapp.R
import com.rizahanmiy.newsapp.data.base.BaseFragment
import com.rizahanmiy.newsapp.data.entities.NewsArticlesApi
import com.rizahanmiy.newsapp.data.entities.NewsSourceApi
import com.rizahanmiy.newsapp.domain.common.ResultState
import com.rizahanmiy.newsapp.domain.common.State
import com.rizahanmiy.newsapp.presentation.ui.adapter.CategoryListAdapter
import com.rizahanmiy.newsapp.presentation.ui.adapter.FeedAdapter
import com.rizahanmiy.newsapp.presentation.ui.adapter.NewsSourceListAdapter
import com.rizahanmiy.newsapp.presentation.ui.webview.WebViewActivity
import com.rizahanmiy.newsapp.presentation.viewmodel.NewsViewModel
import com.rizahanmiy.newsapp.presentation.viewmodel.ViewModelFactory
import com.rizahanmiy.newsapp.utils.common.categoryList
import com.rizahanmiy.newsapp.utils.extension.observe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.pullToRefresh
import kotlinx.android.synthetic.main.fragment_feed.rvArticle
import kotlinx.android.synthetic.main.layout_toolbar_categories.*
import javax.inject.Inject

class CategoriesFragment : BaseFragment(), FeedAdapter.OnArticleClickListener{

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var source: MutableList<NewsSourceApi>
    private lateinit var news: MutableList<NewsArticlesApi>
    private lateinit var categoryListAdapter: CategoryListAdapter
    private lateinit var newsSourceListAdapter: NewsSourceListAdapter

    private lateinit var articleAdapter: FeedAdapter

    override val layout: Int = R.layout.fragment_categories


    var previousTotalItemCount = 0
    var loading = true
    var limit = 10
    var page = 1
//    var country = ""
    var cat = "general"
    var sou = ""
    var searchText : String? = ""

    var categoryData : List<String> = categoryList

    override fun onPreparation() {
        AndroidSupportInjection.inject(this)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        source = mutableListOf()
        news = mutableListOf()
    }

    override fun onUi() {

        //News Article RV
        categoryListAdapter = CategoryListAdapter(
            context = this.requireContext(),
            data = categoryData,
            onCategoryClicked = {
                onClickCategory(category = it)
            },
            feed = false
        )

        rvCategory.apply {
            adapter = categoryListAdapter
            layoutManager = LinearLayoutManager(
                this.context,
                RecyclerView.HORIZONTAL,
                false
            )
        }

        //News Source RV
        newsSourceListAdapter = NewsSourceListAdapter(
            context = this.requireContext(),
            data = source,
            onCategoryClicked = {
                onClickCategory(source = it)
            },
        )

        rvSource.apply {
            adapter = newsSourceListAdapter
            layoutManager = LinearLayoutManager(
                this.context,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    override fun onAction() {
        pullToRefresh.setOnRefreshListener {
            observe(newsViewModel.fetchArticleSources(
                page = 1,
                pageSize = limit,
                sources = sou
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
                        country = ""
                    ), ::setItemProductsPaging
                    )
                    loading = true
                }
            }
        }
        rvArticle.addOnScrollListener(scrollListener)


        ivClear.setOnClickListener {
            etSearch.setText("")
        }

        etSearch.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                val imm: InputMethodManager =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    etSearch.windowToken,
                    InputMethodManager.RESULT_UNCHANGED_SHOWN
                )

                searchText = etSearch.text.toString()
                searchResult(etSearch.text.toString())
            }
            return@setOnEditorActionListener false
        }
    }

    private fun searchResult(search: String){
        if (rbSource.isChecked){
            observe(newsViewModel.fetchArticle(
                page = page,
                pageSize = limit,
                sources = search

            )){
                manageStateArticle(it)
            }
        }else if(rbArticle.isChecked){
            observe(newsViewModel.fetchArticle(
                page = page,
                pageSize = limit,
                search = search

            )){
                manageStateArticle(it)
            }
        }else{
            Toast.makeText(context, "Please select source/article", Toast.LENGTH_SHORT).show()
        }

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
            country = "",
            category = cat
        )){
            manageStateArticle(it)
        }
    }

    private fun getSource(){
        observe(newsViewModel.fetchSource(
            page = 1,
            pageSize = limit,
            country = "",
            category = cat
        )){
            manageStateSource(it)
        }
    }

    private fun getArticle(){
        observe(newsViewModel.fetchArticleSources(
            page = 1,
            pageSize = limit,
            sources = sou
        )){ manageStateArticle(it) }
    }

    private fun onClickCategory(category:String? = "", source:String? = ""){
        cat = category.toString()
        sou = source.toString()
        if (cat.isNotBlank() && sou == ""){
            getSource()
        }else if (sou.isNotBlank()){
            getArticle()
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
    private fun manageStateSource(result: ResultState<MutableList<NewsSourceApi>>) {
        when (result) {
            is ResultState.Success -> {
                pullToRefresh.isRefreshing = false
                page = 2
                if (result.data.size < 9) {
                    rvArticle.removeOnScrollListener(scrollListener)
                }
                source.clear()
                source.addAll(result.data)
                getArticle()
                newsSourceListAdapter.notifyDataSetChanged()
            }
            else -> {
                Log.d("TAG", "Error")
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

    override fun onPause() {
        super.onPause()
        Log.d("CategoryFragment", "onPause() Article")
    }
    override fun onResume() {
        super.onResume()
        onObserver()
    }

    override fun onArticleClicked(item: NewsArticlesApi) {
        item.url?.let { WebViewActivity.start(requireContext(), it) }
    }

}