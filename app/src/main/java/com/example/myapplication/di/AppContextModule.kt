package com.example.myapplication.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppContextModule(val context: Context) {
    @Provides
    fun provideAppContext(): Context {
        return context
    }
}