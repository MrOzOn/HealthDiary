package com.mrozon.core_api.provides

import android.content.Context

interface AppProvider {

    fun provideContext(): Context
}