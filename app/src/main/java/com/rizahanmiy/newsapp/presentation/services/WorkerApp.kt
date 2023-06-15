package com.rizahanmiy.newsapp.presentation.services

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rizahanmiy.newsapp.data.local.LocalPreferences
import com.rizahanmiy.newsapp.domain.usecase.UserUseCase
import com.rizahanmiy.newsapp.utils.constants.AppConstants.NEWS_PREF
import io.reactivex.disposables.CompositeDisposable

class WorkerApp(
    private val userUseCase: UserUseCase,
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    private var resultStatus: Result = Result.success()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var sharedPreferences: LocalPreferences

    override fun doWork(): Result {
        sharedPreferences = LocalPreferences(context.getSharedPreferences(NEWS_PREF, 0))
//        return fetchFirebase()
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        compositeDisposable.clear()
    }

}