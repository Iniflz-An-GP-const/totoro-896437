package rubery.totoro.digital.di

import rubery.totoro.digital.data.repository.OnboardingRepository
import rubery.totoro.digital.data.repository.ProfileRepository
import rubery.totoro.digital.data.repository.TipRepository
import rubery.totoro.digital.data.repository.WorkoutRepository
import rubery.totoro.digital.data.repository.WorkoutSessionRepository
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        OnboardingRepository(
            onboardingDataStoreManager = get(),
            coroutineDispatcher = get(DispatchersQualifiers.IO)
        )
    }

    single { WorkoutRepository() }

    single { TipRepository() }

    single {
        WorkoutSessionRepository(
            workoutSessionDao = get(),
            coroutineDispatcher = get(DispatchersQualifiers.IO)
        )
    }

    single {
        ProfileRepository(
            profileDataStoreManager = get(),
            coroutineDispatcher = get(DispatchersQualifiers.IO)
        )
    }
}