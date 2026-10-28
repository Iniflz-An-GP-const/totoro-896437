package rubery.totoro.digital.di

import rubery.totoro.digital.data.datastore.OnboardingDataStoreManager
import rubery.totoro.digital.data.datastore.ProfileDataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { OnboardingDataStoreManager(androidContext()) }
    single { ProfileDataStoreManager(androidContext()) }
}