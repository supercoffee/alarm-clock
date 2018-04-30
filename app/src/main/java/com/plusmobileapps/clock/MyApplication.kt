package com.plusmobileapps.clock

import android.app.Application
import com.plusmobileapps.clock.di.AppComponent
import com.plusmobileapps.clock.di.AppModule
import com.plusmobileapps.clock.di.DaggerAppComponent
import com.plusmobileapps.clock.di.RoomModule

class MyApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule(this))
                .build()

    }
}