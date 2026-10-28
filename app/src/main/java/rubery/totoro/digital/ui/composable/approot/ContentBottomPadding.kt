package rubery.totoro.digital.ui.composable.approot

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

/**
 * The height occupied by the bottom bar on the screen, including the inset for
 * system navigation.
 *
 * Content is drawn underneath the bar, not above it: this is the only way to
 * make cards visibly extend underneath tiled bar variants with side insets.
 * The trade-off is that the last list item would end up underneath the bar, so
 * every scrollable screen must add this value to its `contentPadding`, while a
 * screen with a fixed bottom block must add it to that block's bottom padding.
 *
 * Zero on screens without a bottom bar: `Scaffold` reserves no space there.
 */
val LocalContentBottomPadding = compositionLocalOf { 0.dp }
