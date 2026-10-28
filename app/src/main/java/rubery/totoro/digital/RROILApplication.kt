package rubery.totoro.digital

import android.app.Application
import rubery.totoro.digital.di.dataModule
import rubery.totoro.digital.di.dispatcherModule
import rubery.totoro.digital.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RROILApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@RROILApplication)
            modules(appModules)
        }
    }
}
