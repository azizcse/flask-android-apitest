package com.techfort.planetapi

import android.app.Application
import android.content.Context

class App : Application(){
    companion object{
        private var context : Context? = null
        fun getContext():Context{
            return context!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}