package com.example.cherubook.utility

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    init {
        context=this
    }
    companion object {
        private lateinit var context: Context
        fun applicationContext(): Context {
            return context
        }
    }
}