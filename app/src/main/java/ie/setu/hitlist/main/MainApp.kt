package ie.setu.hitlist.main

/*
   A single instance of this class will be created when the application is launched.
   A reference to this application can be acquired in other activities as needed.
*/

import android.app.Application
import ie.setu.hitlist.models.HitMemStore
import ie.setu.hitlist.models.HitModel
import timber.log.Timber
import timber.log.Timber.i


class MainApp : Application() {

//  val targets = ArrayList<HitModel>()
val targets = HitMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Hit App started")
        targets.create(HitModel(0, "TODO one...", "About One"))
        targets.create(HitModel(1, "TODO two...", "About Three"))
        targets.create(HitModel(2, "TODO three...", "About Three"))
    }
}
