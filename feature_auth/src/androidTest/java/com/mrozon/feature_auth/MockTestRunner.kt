package com.mrozon.feature_auth

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.mrozon.feature_auth.presentation.TestApp

class MockTestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}