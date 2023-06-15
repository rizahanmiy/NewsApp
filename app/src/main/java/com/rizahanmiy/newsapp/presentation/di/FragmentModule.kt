package com.rizahanmiy.newsapp.presentation.di

import com.rizahanmiy.newsapp.presentation.ui.categories.CategoriesFragment
import com.rizahanmiy.newsapp.presentation.ui.feed.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun feedFragment(): FeedFragment

    @ContributesAndroidInjector
    abstract fun categoriesFragment(): CategoriesFragment

}