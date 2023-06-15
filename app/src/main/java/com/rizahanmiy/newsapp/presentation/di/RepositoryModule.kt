package com.rizahanmiy.newsapp.presentation.di

import android.app.Application
import android.content.Context
import com.rizahanmiy.newsapp.data.api.RemoteApi
import com.rizahanmiy.newsapp.data.local.LocalPreferences
import com.rizahanmiy.newsapp.data.repository.NewsRepositoryImpl
import com.rizahanmiy.newsapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    internal fun provideSharedPreference(aplication: Application) : LocalPreferences{
        val sharedPreferences =
            aplication.getSharedPreferences("KECIPIR_PREF", Context.MODE_PRIVATE)
        return  LocalPreferences(sharedPreferences)
    }

    @Singleton
    @Provides
    internal fun provideCartRepository(remoteApi: RemoteApi, localPreferences: LocalPreferences,aplication: Application): UserRepository {
        return NewsRepositoryImpl(remoteApi,localPreferences,aplication)

    }

}