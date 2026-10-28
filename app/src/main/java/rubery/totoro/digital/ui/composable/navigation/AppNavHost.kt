package rubery.totoro.digital.ui.composable.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import rubery.totoro.digital.ui.composable.screen.history.RROILHistoryScreen
import rubery.totoro.digital.ui.composable.screen.home.RROILHomeScreen
import rubery.totoro.digital.ui.composable.screen.onboarding.RROILOnboardingScreen
import rubery.totoro.digital.ui.composable.screen.profile.RROILProfileScreen
import rubery.totoro.digital.ui.composable.screen.splash.RROILSplashScreen
import rubery.totoro.digital.ui.composable.screen.statistics.RROILStatisticsScreen
import rubery.totoro.digital.ui.composable.screen.workout.RROILWorkoutScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash,
        modifier = modifier,
    ) {
        composable<NavRoute.Splash> {
            RROILSplashScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(route = NavRoute.Home) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate(route = NavRoute.Onboarding) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.Onboarding> {
            RROILOnboardingScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(route = NavRoute.Home) {
                        popUpTo(NavRoute.Onboarding) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.Home> {
            RROILHomeScreen(
                onWorkoutClick = { workoutId ->
                    navController.navigate(route = NavRoute.Workout(workoutId = workoutId))
                },
            )
        }

        composable<NavRoute.Workout> { backStackEntry ->
            val workout: NavRoute.Workout = backStackEntry.toRoute()
            RROILWorkoutScreen(
                workoutId = workout.workoutId,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable<NavRoute.History> {
            RROILHistoryScreen()
        }

        composable<NavRoute.Statistics> {
            RROILStatisticsScreen()
        }

        composable<NavRoute.Profile> {
            RROILProfileScreen()
        }
    }
}