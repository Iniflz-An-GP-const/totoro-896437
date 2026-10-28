package rubery.totoro.digital.ui.composable.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable
    object Splash : NavRoute()

    @Serializable
    object Onboarding : NavRoute()

    @Serializable
    object Home : NavRoute()

    @Serializable
    data class Workout(val workoutId: Int) : NavRoute()

    @Serializable
    object History : NavRoute()

    @Serializable
    object Statistics : NavRoute()

    @Serializable
    object Profile : NavRoute()
}