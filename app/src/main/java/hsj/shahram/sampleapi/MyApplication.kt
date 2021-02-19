package hsj.shahram.sampleapi

import android.app.Application
import androidx.multidex.MultiDexApplication
import hsj.shahram.sampleapi.di.component.AppComponent
import hsj.shahram.sampleapi.di.component.DaggerAppComponent

class MyApplication : MultiDexApplication() {

    val appComponent : AppComponent by lazy {


        DaggerAppComponent.factory().create(this)


    }

    override fun onCreate() {
        super.onCreate()
    }
}