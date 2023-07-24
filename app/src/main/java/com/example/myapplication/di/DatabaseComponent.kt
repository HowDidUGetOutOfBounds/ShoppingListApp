package com.example.myapplication.di

import com.example.myapplication.ui.AddItemFragment
import com.example.myapplication.ui.MainScreenFragment
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun inject(fragment: MainScreenFragment)

    fun inject(fragment: AddItemFragment)
}