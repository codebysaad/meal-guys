package com.saadfauzi.mealguys.helpers

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {

    val diskIO: Executor = Executors.newSingleThreadExecutor()

    private class MainThreadExecutor: Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(runnable: Runnable) {
            mainThreadHandler.post(runnable)
        }
    }
}