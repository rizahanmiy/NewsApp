package com.rizahanmiy.newsapp.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rizahanmiy.newsapp.R
import com.rizahanmiy.newsapp.data.base.BaseActivity
import com.rizahanmiy.newsapp.data.local.LocalPreferences
import com.rizahanmiy.newsapp.presentation.ui.feed.FeedFragment
import com.rizahanmiy.newsapp.presentation.ui.categories.CategoriesFragment
import com.rizahanmiy.newsapp.presentation.viewmodel.NewsViewModel
import com.rizahanmiy.newsapp.presentation.viewmodel.ViewModelFactory
import com.rizahanmiy.newsapp.utils.common.IOSelectTab
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyIntents.KEY_MODEL
import com.rizahanmiy.newsapp.utils.constants.AppConstants.NEWS_PREF
import com.rizahanmiy.newsapp.utils.extension.replaceFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), IOSelectTab,
    BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModelUser: NewsViewModel

    private lateinit var sharedPreferences: LocalPreferences


    private var isDoubleBackToExitPressedOnce = false

    companion object {
        const val FEED_PAGE = 1
        const val CATEGORY_PAGE = 2

        var currentTab = CATEGORY_PAGE

        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            context.startActivity(starter)
        }
    }

    private val feedFragment: FeedFragment by lazy { FeedFragment() }
    private val categoriesFragment: CategoriesFragment by lazy { CategoriesFragment() }
    private var activeFragment: Fragment = FeedFragment()

    override val layout: Int = R.layout.activity_main

    override fun onPreparation() {
        AndroidInjection.inject(this)
        viewModelUser = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
        sharedPreferences = LocalPreferences(this.getSharedPreferences(NEWS_PREF, 0))
    }

    override fun onIntent() {
    }

    override fun onUi() {
        setupBottomMainMenu()
    }

    private fun setupBottomMainMenu() {
        replaceFragment(R.id.frame, feedFragment)
        activeFragment = feedFragment
        bnvMain.selectedItemId = R.id.navigation_satu
        bnvMain.itemIconTintList = null
    }

    override fun onAction() {
        bnvMain.setOnNavigationItemSelectedListener(this)
    }

    override fun onObserver() {
    }

    override fun onResume() {
        super.onResume()
        updateBottomNavigationHighlight()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isDoubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        isDoubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            getString(R.string.message_please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()
        Handler().postDelayed({ isDoubleBackToExitPressedOnce = false }, 2000)
    }

    override fun selectedTab(position: Int) {
        when (position) {
            FEED_PAGE -> {
                activeFragment = feedFragment
                bnvMain.selectedItemId = R.id.navigation_satu
            }
            CATEGORY_PAGE -> {
                activeFragment = categoriesFragment
                bnvMain.selectedItemId = R.id.navigation_dua
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_satu -> {
                replaceFragment(R.id.frame, feedFragment)
                activeFragment = feedFragment
            }
            R.id.navigation_dua -> {
                replaceFragment(R.id.frame, categoriesFragment)
                activeFragment = categoriesFragment
            }
        }
        return true
    }

    private fun updateBottomNavigationHighlight() {
        when (activeFragment) {
            is FeedFragment -> {
                bnvMain.selectedItemId = R.id.navigation_satu
                currentTab = 1
            }
            is CategoriesFragment -> {
                bnvMain.selectedItemId = R.id.navigation_dua
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause()")
    }

}
