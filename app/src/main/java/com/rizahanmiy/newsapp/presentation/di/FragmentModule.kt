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
//
//    @ContributesAndroidInjector
//    abstract fun cartFragment(): CartDialogFragment
//
//    @ContributesAndroidInjector
//    abstract fun historyFragment(): OrderFragment
//
//    @ContributesAndroidInjector
//    abstract fun helpFragment(): HelpFragment
//
//
//    @ContributesAndroidInjector
//    abstract fun chatFragment(): ChatFragment
//

}