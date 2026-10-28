package rubery.totoro.digital.di

import rubery.totoro.digital.ui.viewmodel.HistoryViewModel
import rubery.totoro.digital.ui.viewmodel.HomeViewModel
import rubery.totoro.digital.ui.viewmodel.OnboardingViewModel
import rubery.totoro.digital.ui.viewmodel.ProfileViewModel
import rubery.totoro.digital.ui.viewmodel.SplashViewModel
import rubery.totoro.digital.ui.viewmodel.StatisticsViewModel
import rubery.totoro.digital.ui.viewmodel.WorkoutViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        SplashViewModel(
            onboardingRepository = get()
        )
    }

    viewModel {
        OnboardingViewModel(
            onboardingRepository = get()
        )
    }

    viewModel {
        HomeViewModel(
            workoutRepository = get(),
            profileRepository = get(),
            workoutSessionRepository = get(),
            tipRepository = get()
        )
    }

    viewModel { (workoutId: Int) ->
        WorkoutViewModel(
            workoutId = workoutId,
            workoutRepository = get(),
            workoutSessionRepository = get()
        )
    }

    viewModel {
        HistoryViewModel(
            workoutSessionRepository = get()
        )
    }

    viewModel {
        StatisticsViewModel(
            workoutSessionRepository = get()
        )
    }

    viewModel {
        ProfileViewModel(
            profileRepository = get(),
            workoutSessionRepository = get()
        )
    }
}