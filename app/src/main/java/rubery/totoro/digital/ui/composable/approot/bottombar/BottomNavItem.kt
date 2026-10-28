package rubery.totoro.digital.ui.composable.approot.bottombar

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import rubery.totoro.digital.ui.composable.navigation.NavRoute

data class BottomNavItem(
    @field:StringRes val titleRes: Int,
    val icon: ImageVector,
    val route: NavRoute,
)

internal fun isSelectedDestination(destination: NavDestination?, route: NavRoute): Boolean {
    return destination?.hierarchy?.any { navDestination -> navDestination.hasRoute(route::class) }
        ?: false
}
