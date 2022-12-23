package ie.setu.hitlist.main

/*
   A single instance of this class will be created when the application is launched.
   A reference to this application can be acquired in other activities as needed.
*/

import android.app.Application
// import ie.setu.hitlist.models.HitManager
import ie.setu.hitlist.models.HitStore
import timber.log.Timber


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Hit App started")
    }
}